def F(A, B):
  if A <= B:
  for i in range(A, B + 1):
    print(i)
  else:
    print("A должно быть меньше или равно B")

A = int(input()) 
B = int(input()) 

F(A, B)
