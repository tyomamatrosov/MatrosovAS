def F(matrix, k):
    count = 0
    max_value = None

    for row in matrix:
        for element in row:
            if element % k == 0:
                count += 1
                if max_value is None or element > max_value:
                    max_value = element
    return count, max_value

def Read(file_name):
    with open(input_file_name, 'r') as file:
        lines = file.readlines()
        matrix = [list(map(int, line.split())) for line in lines[:-1]]  # Исключаем последнюю строку с k
        k = int(lines[-1])
    return matrix, k

def Write(count, max_value, output_file_name):
    with open(output_file_name, 'w') as file:
        file.write(f"Количество элементов, кратных {k}: {count}\n")
        file.write(f"Наибольший элемент, кратный {k}: {max_value}\n")
        
input_file_name = "Матросов_У-234_vvod.txt"
matrix, k = Read(input_file_name)

count, max_value = F(matrix, k)

output_file_name = "Матросов_У-234_vivod1.txt"
Write(count, max_value, output_file_name)

print(f"Количество элементов, кратных {k}: {count}")
print(f"Наибольший элемент, кратный {k}: {max_value}")
