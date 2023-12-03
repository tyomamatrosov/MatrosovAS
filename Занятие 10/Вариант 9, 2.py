def F(matrix):
    max_value = None
    max_indices = None

    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            current_value = abs(matrix[i][j])
            if max_value is None or current_value > max_value:
                max_value = current_value
                max_indices = (i, j)

    return max_indices

def R(matrix, row, col):
    new_matrix = [row[:col] + row[col + 1:] for i, row in enumerate(matrix) if i != row]
    return [list(x) for x in zip(*new_matrix)]

def Read(file_path):
    matrix = []

    with open(file_path, 'r') as file:
        for line in file:
            row = [float(value) for value in line.split()]
            matrix.append(row)

    return matrix

def W(file_path, matrix):
    with open(file_path, 'w') as file:
        for row in matrix:
            line = ' '.join(map(str, row)) + '\n'
            file.write(line)

file_path_input = "Матросов_У-234_vvod.txt"
matrix = Read(file_path_input)

max_indices = F(matrix)

if max_indices:
  
    new_matrix = R(matrix, max_indices[0], max_indices[1])

    print("Исходная матрица:")
    for row in matrix:
        print(row)

    print("\nМаксимальный по модулю элемент:", matrix[max_indices[0]][max_indices[1]])

    print("\nНовая матрица (без строки и столбца с максимальным элементом):")
    for row in new_matrix:
        print(row)

    file_path_output = "Матросов_У-234_vivod2.txt"
    W(file_path_output, new_matrix)
else:
    print("Матрица пуста.")
