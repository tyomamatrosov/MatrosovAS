def F(matrix):
    max_value = 0
    max_i = 0
    max_j = 0

    # Находим наибольший по модулю элемент
    for i, row in enumerate(matrix):
        for j, element in enumerate(row):
            current = abs(element)
            if max_value is None or current > max_value:
                max_value = current
                max_i, max_j = i, j

    return max_value, max_i, max_j

def R(matrix, row, col):
    # Создаем новую матрицу без строки row и столбца col
    return [[matrix[i][j] for j in range(len(matrix[i])) if j != col] for i in range(len(matrix)) if i != row]


matrix = [[2, -5, 10],
          [15, -8, 20],
          [25, 30, -35]]

max_value, max_i, max_j = F(matrix)

print(f"Наибольший по модулю элемент: {max_value}")
print(f"Индексы строки и столбца: ({max_i}, {max_j})")

# Создаем новую матрицу без строки и столбца с найденным элементом
new_matrix = R(matrix, max_i, max_j)

print("Новая матрица:")
for row in new_matrix:
    print(row)
