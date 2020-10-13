letters = []


def decrypt(key, letter):

    for i in range(65, 91):
        letters.append(chr(i))
    letters.append(" ")

    shiftedletters = []

    for i in range(26):
        if (i + 65 + key) < 91:
            shiftedletters.append(chr(i + 65 + key))
    for i in range(key):
        shiftedletters.append(chr(65 + i))
    shiftedletters.append(" ")

    dictionary = dict(zip(shiftedletters, letters))

    enword = list(map(lambda x: dictionary[x], letter))
    enwordstring = ''.join(enword)
    print("\n")
    print(enwordstring)
    print("Key:", key)

