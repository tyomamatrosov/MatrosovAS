def F(n):
    c = 0 #кол-во элементов последовательности
    l = 0 #сумма элементов последовательности
    while True:
        if n == 0:
            break
        l += n
        c += 1
    print(l/c)

n  = int(input())

F(n)
