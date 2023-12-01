def F(matrix, k):
    count = 0
    max_el = None
    for row in matrix:
        for el in row:
            if el % k == 0:
                count += 1
                if max_el is None or el > max_el:
                    max_el = el
    return count, max_el
matrix = [[2, 5, 10],
          [15, 8, 20],
          [25, 30, 35]]
k = 5

count, max_el = F(matrix, k)

print(count, max_el)
