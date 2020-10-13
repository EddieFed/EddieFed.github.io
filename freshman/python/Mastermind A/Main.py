from Projects.MaserMind_A.Modules.MastermindA import pegs
from Projects.MaserMind_A.Modules.MastermindA import alertstats

import random
import sys


def mastermind():
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

mastermind()

while 0 == 0:
    YN = "x"

    if YN not in ["yes", "no", "y", "n"]:
        print("Would You Like to keep playing? Y/N")
        YN = input("> ").lower()

    if YN in ["yes", 'y']:
        YN = "x"
        mastermind()

    if YN in ["no", "n"]:
        print("\n\nThanks For Playing!")
        break

input("\n\nPress Enter to Exit")
sys.exit()
