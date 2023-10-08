x = float(input())
y = float(input())
d = 1
while x < y:
    x = x + x * 0.10
    d += 1
print(d)
