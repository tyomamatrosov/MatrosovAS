import requests

BASE_URL = "http://127.0.0.1:5000/api"

def print_menu():
    print("\n--- Система управления запасами ---")
    print("1. Показать все материалы")
    print("2. Добавить материал")
    print("3. Зарегистрировать поступление")
    print("4. Зарегистрировать списание")
    print("5. Показать заказы")
    print("0. Выход")

def get_materials():
    response = requests.get(f"{BASE_URL}/materials")
    if response.status_code == 200:
        materials = response.json()
        print("\n--- Список материалов ---")
        for material in materials:
            print(f"ID: {material['id']}, Артикул: {material['sku']}, Название: {material['name']}, Остаток: {material['current_stock']} {material['unit']}, Мин. уровень: {material['min_stock_level']}")
    else:
        print("Ошибка при получении данных")

def add_material():
    sku = input("Артикул (SKU): ")
    name = input("Наименование: ")
    description = input("Описание: ")
    unit = input("Единица измерения (шт, кг, м): ")
    min_stock_level = float(input("Минимальный уровень запаса: "))

    data = {
        'sku': sku,
        'name': name,
        'description': description,
        'unit': unit,
        'min_stock_level': min_stock_level
    }
    response = requests.post(f"{BASE_URL}/materials", json=data)
    if response.status_code == 201:
        print("Материал успешно добавлен!")
    else:
        print(f"Ошибка: {response.json().get('error', 'Unknown error')}")

def add_transaction(transaction_type):
    material_id = input("ID материала: ")
    quantity = float(input("Количество: "))
    reference = input("Номер документа-основания: ")

    data = {
        'material_id': material_id,
        'type': transaction_type,
        'quantity': quantity,
        'reference': reference
    }
    response = requests.post(f"{BASE_URL}/transactions", json=data)
    if response.status_code == 201:
        print("Операция успешно записана!")
    else:
        print(f"Ошибка: {response.json().get('error', 'Unknown error')}")

def get_orders():
    response = requests.get(f"{BASE_URL}/purchase_orders")
    if response.status_code == 200:
        orders = response.json()
        print("\n--- Список заказов ---")
        for order in orders:
            status_ru = {'pending': 'ожидает', 'ordered': 'заказан', 'delivered': 'доставлен'}.get(order['status'], order['status'])
            print(f"ID: {order['id']}, Материал: {order['material_name']} ({order['sku']}), Кол-во: {order['quantity']}, Статус: {status_ru}")
    else:
        print("Ошибка при получении данных")

def main():
    while True:
        print_menu()
        choice = input("Выберите действие: ")

        if choice == '1':
            get_materials()
        elif choice == '2':
            add_material()
        elif choice == '3':
            add_transaction('in')
        elif choice == '4':
            add_transaction('out')
        elif choice == '5':
            get_orders()
        elif choice == '0':
            break
        else:
            print("Неверный ввод")

if __name__ == '__main__':
    main()