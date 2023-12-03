def is_prime(n, div=2):
    if n < 2:
        return False
    if div * div > n:
        return True
    if n % div == 0:
        return False
    return is_prime(n, div + 1)

n = int(input("Введите натуральное число больше 1: "))

if is_prime(n):
    print("YES")
else:
    print("NO")
