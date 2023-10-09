def F(text, word):
    words_list = text.split()
    count = 0
    for w in words_list:
        if w.lower() == word.lower():  
            count += 1
    return count

F(the red fox jumps across the bridge, fox)
