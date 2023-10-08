def F(x, y)
    d = 1
    while x < y:
        x = x + x * 0.10
        d += 1
    print(d)

x = float(input())
y = float(input())

F(x, y)
