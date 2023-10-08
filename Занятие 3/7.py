y = int(input())
if y%4==0 and y%100!=0 and y%400!=0:
    print('Да')
else:
    print('Нет')
