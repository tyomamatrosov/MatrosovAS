def F(n):
    if n > 2:
        for i in range(1, n + 1):
            if n % i == 0 and i!=1:
                print(i)
                break

n = int(input())

F(n)
