def F(text, word):
    splittedtext = text.split()
    count = 0
    for w in splittedtext:
        if w.lower() == word.lower():  
            count += 1
    return count

F("the red Fox jumps across the bridge", fox)
