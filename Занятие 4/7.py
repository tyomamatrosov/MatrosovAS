def F(n):
    r = 1
    for i in range(2, n + 1):
        r *= i
    return r

def SF(n):
    sr = 0
    for i in range(1, n + 1):
        sr += F(i)
    return sr

n = int(input())
r = SF(n)
print(r) 
