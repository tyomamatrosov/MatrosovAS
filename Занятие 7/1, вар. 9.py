a = []
N = int(input())
for i in range (N):
    a.append(float(input()))
minimalabs = min(a, key=abs) #Минимальный по модулю элемент
print(minimalabs)
a.sort(reverse = 1) #Массив в обратном порядке
print(a)
