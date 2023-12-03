def F(s):

    current_number = int(input())

    if current_number == 0:
        return None

    next_second_largest = F(s)

    if next_second_largest is None or current_number > next_second_largest:
        return current_number
    else:
        return next_second_largest

print("Введите последовательность натуральных чисел (завершите ввод числом 0):")
result = F(None)

print(f"Второй по величине элемент: {result}")
