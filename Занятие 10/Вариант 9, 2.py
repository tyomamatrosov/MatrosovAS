def F(matrix):
    max_value = None
    max_i, max_j = None, None

    for i, row in enumerate(matrix):
        for j, element in enumerate(row):
            current_value = abs(element)
            if max_value is None or current_value > max_value:
                max_value = current_value
                max_i, max_j = i, j

    return max_value, max_i, max_j

def R(matrix, row, col):
    return [[matrix[i][j] for j in range(len(matrix[i])) if j != col] for i in range(len(matrix)) if i != row]

def Read(file_name):
    with open(file_name, 'r') as file:
        lines = file.readlines()
        matrix = [list(map(int, line.split())) for line in lines]
    return matrix

def Write(matrix, file_name):
    with open(file_name, 'w') as file:
        for row in matrix:
            file.write(' '.join(map(str, row)) + '\n')

# Организация ввода данных из файла
input_file_name = "Матросов_У-234_vvod.txt"
matrix = R(input_file_name)

# Нахождение наибольшего по модулю элемента
max_value, max_i, max_j = F(matrix)

# Создание новой матрицы без строки и столбца с найденным элементом
new_matrix = R(matrix, max_i, max_j)

# Организация вывода результатов в файл
output_file_name = "Матросов_У-234_vivod.txt"
W(new_matrix, output_file_name)

print(f"Наибольший по модулю элемент: {max_value}")
print(f"Индексы строки и столбца: ({max_i}, {max_j})")
