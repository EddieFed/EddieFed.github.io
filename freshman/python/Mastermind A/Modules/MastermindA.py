import random


def alertstats(turn, blackpeg, whitepeg, possiblecolors):
    print("\n\nTurn: ", turn, "\nBlack Pegs: ", blackpeg)
    print("White Pegs: ", whitepeg, "\nPossible Colors", possiblecolors)
    print("\n")


def pegs(blackpeg, whitepeg):
    repeat = 4
    z = 0
    while z < repeat:

        if guess[z] == chosencopy[z]:
            guess.remove(guess[z])
            chosencopy.remove(chosencopy[z])
            z -= 1
            repeat -= 1
            blackpeg += 1  # Correct Everything

        else:
            z += 1

    for i in range(len(chosencopy)):
        if guess[i] in chosencopy and guess[i] not in chosencopy[i]:
            whitepeg += 1  # Right Color
            alertstats(turn, blackpeg, whitepeg, possiblecolors)

# simply here to test
possiblecolors = ["Red", "Green", "Yellow", "Blue", "Orange", "Purple"]
colormap = ["R", "G", "Y", "B", "O", "P"]
dictionary = dict(zip(colormap, possiblecolors))
chosencolors = []
chosencopy = []

for i in range(1, 5):
    x = random.choice(possiblecolors)
    chosencolors.append(x)
    chosencopy.append(x)

print("possible Colors: ", possiblecolors, "\n")
print("Answer: ", chosencolors)

for turn in range(10):
    blackpeg = 0
    whitepeg = 0
    guess = ''

    tf = "False"

    while tf == "False":

        guess = ''
        while len(guess) != 10 and len(guess) != 8:

            print("What's your guess? \nType the first letter of the colors, separated by commas")
            guess = input("> ").upper()

        if len(guess) == 10:
            guess = guess.split(", ")
        else:
            guess = guess.split(",")

        for i in range(4):
            if guess[i] not in colormap:
                break
            else:
                tf = "True"

    guess = list(map(lambda d: dictionary[d], guess))

    if guess == chosencopy:
        print("\nYou Won in", turn + 1, "Turns!")
        break

    pegs(blackpeg, whitepeg)
    alertstats(turn, blackpeg, whitepeg, possiblecolors)

else:
    print("\nYou lost!")

