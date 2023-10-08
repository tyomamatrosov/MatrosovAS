def F(n):
    c = 0
    previous = None
    while True:
        if n == 0:
            break  
        if previous is not None and n == previous:
            c += 1
        previous = n
    print(c)

n = int(input())

F(n)
