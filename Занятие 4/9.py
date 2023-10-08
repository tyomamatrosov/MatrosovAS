 def f(n):
    if n <= 0:
        return 0
    elif n == 1:
        return 1

    s = 0
    p = 0
    c = 1

    for i in range(2, n + 1):
        next = p + c
        s += c
        p = c
        c = next

    return s
n = int(input())
r = f(n)
print(r)
