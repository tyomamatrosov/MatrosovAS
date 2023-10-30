def F(n): #Суммв цифр в числе
    return sum(int(digit) for digit in str(n))

def S(n):
    s = 0
    while n > 0:
        n -= F(n)
        s += 1
    return s


n = int(input())
otvet = S(n)
print(otvet)
