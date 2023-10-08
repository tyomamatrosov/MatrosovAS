def F(n):
    r = 1 #степень
    p = 0 #показатель
    while r * 2 <= n:
        r *= 2
        p += 1
    print(r, p)

n = int(input())

F(n)
