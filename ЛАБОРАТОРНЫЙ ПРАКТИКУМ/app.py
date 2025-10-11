from flask import Flask, request, jsonify, g
import sqlite3

app = Flask(__name__)
DATABASE = 'inventory.db'

def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
        db.row_factory = sqlite3.Row
    return db

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

def init_db():
    with app.app_context():
        db = get_db()
        with app.open_resource('schema.sql', mode='r') as f:
            db.cursor().executescript(f.read())
        db.commit()

@app.route('/api/materials', methods=['GET'])
def get_materials():
    db = get_db()
    materials = db.execute('SELECT * FROM materials').fetchall()
    return jsonify([dict(material) for material in materials])

@app.route('/api/materials', methods=['POST'])
def add_material():
    data = request.get_json()
    required_fields = ['sku', 'name', 'unit', 'min_stock_level']
    if not all(field in data for field in required_fields):
        return jsonify({'error': 'Missing required fields'}), 400

    try:
        db = get_db()
        cursor = db.cursor()
        cursor.execute('''
            INSERT INTO materials (sku, name, description, unit, min_stock_level, current_stock)
            VALUES (?, ?, ?, ?, ?, ?)
        ''', (data['sku'], data['name'], data.get('description', ''),
              data['unit'], data['min_stock_level'], 0))
        db.commit()
        return jsonify({'id': cursor.lastrowid, 'message': 'Material added successfully'}), 201
    except sqlite3.IntegrityError:
        return jsonify({'error': 'SKU already exists'}), 400

@app.route('/api/transactions', methods=['POST'])
def add_transaction():
    data = request.get_json()
    material_id = data.get('material_id')
    transaction_type = data.get('type')
    quantity = data.get('quantity')
    reference = data.get('reference', '')

    if not all([material_id, transaction_type, quantity]):
        return jsonify({'error': 'Missing material_id, type or quantity'}), 400

    if transaction_type not in ['in', 'out']:
        return jsonify({'error': "Type must be 'in' or 'out'"}), 400

    db = get_db()
    try:
        db.execute('''
            INSERT INTO transactions (material_id, type, quantity, reference)
            VALUES (?, ?, ?, ?)
        ''', (material_id, transaction_type, quantity, reference))

        sign = 1 if transaction_type == 'in' else -1
        db.execute('''
            UPDATE materials 
            SET current_stock = current_stock + ? 
            WHERE id = ?
        ''', (sign * quantity, material_id))

        db.commit()
        check_and_create_order(material_id)

        return jsonify({'message': 'Transaction recorded successfully'}), 201

    except Exception as e:
        return jsonify({'error': str(e)}), 500

def check_and_create_order(material_id):
    db = get_db()
    material = db.execute('''
        SELECT id, name, current_stock, min_stock_level 
        FROM materials WHERE id = ?
    ''', (material_id,)).fetchone()

    if material is None:
        return

    current_stock = material['current_stock']
    min_stock_level = material['min_stock_level']

    if current_stock < min_stock_level:
        existing_order = db.execute('''
            SELECT id FROM purchase_orders 
            WHERE material_id = ? AND status IN ('pending', 'ordered')
        ''', (material_id,)).fetchone()

        if not existing_order:
            order_quantity = min_stock_level * 2
            db.execute('''
                INSERT INTO purchase_orders (material_id, quantity, status)
                VALUES (?, ?, 'pending')
            ''', (material_id, order_quantity))
            db.commit()
            print(f"Создан новый заказ на материал: {material['name']}")

@app.route('/api/purchase_orders', methods=['GET'])
def get_purchase_orders():
    db = get_db()
    orders = db.execute('''
        SELECT po.*, m.name as material_name, m.sku
        FROM purchase_orders po
        JOIN materials m ON po.material_id = m.id
    ''').fetchall()
    return jsonify([dict(order) for order in orders])

if __name__ == '__main__':
    with app.app_context():
        init_db()
    app.run(debug=True)
