def decrypt(key):
    letters = []

    # Creates a Regular A-Z Alphabet
    for i in range(65, 91):
        letters.append(chr(i))
    letters.append(" ")

    # Holds The array of Shifted letters
    shiftedletters = []

    # Creates Shifted Alphabet
    for i in range(26):
        if (i + 65 + key) < 91:
            shiftedletters.append(chr(i + 65 + key))
    for i in range(key):
        shiftedletters.append(chr(65 + i))
    shiftedletters.append(" ")

    # Prints Alphabets Side-by-Side
    print("\n")
    print(letters)
    print(shiftedletters, "\n\n")

    # Creates a Dictionary connecting the two Alphabets
    dictionary = dict(zip(shiftedletters, letters))

    print("Type Your Encrypted Phrase")
    letter = input("> ").upper()

    # Switches Coded Letters for Correct Letters
    enword = list(map(lambda x: dictionary[x], letter))
    enwordstring = ''.join(enword)
    print("\n")
    print(enwordstring)
