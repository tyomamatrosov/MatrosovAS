def F(A, B):

if A > B:
  for i in range(A, B - 1, -1):
    if i % 2 != 0:
      print(i)
else:
  print("A должно быть больше B")

A = int(input()) 
B = int(input()) 

F(A, B)
