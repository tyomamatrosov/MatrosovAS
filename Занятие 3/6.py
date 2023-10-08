def F(n1, n2, m1, m2):
    d1 = 0
    d2 = 0
    if n1 or n2 %2==0:
        d1 = 1
    else:
        d1 = 0
    if m1 or m2 %2==0:
        d2 = 1
    else:
        d2 = 0
    if d1 == d2:
        print('Да')
    else:
        print('Нет')

n1 = int(input())
n2 = int(input())
m1 = int(input())
m2 = int(input())

F(n1, n2, m1, m2)
