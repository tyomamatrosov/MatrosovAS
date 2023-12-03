def F():

    current_number = int(input())

    if current_number == 0:
        return current_number

    next_max = F()
    return max(current_number, next_max)


print("Последовательность натуральных чисел (завершение ввод числом 0):")

result = F()

print(f"Наибольшее значение в последовательности: {result})
