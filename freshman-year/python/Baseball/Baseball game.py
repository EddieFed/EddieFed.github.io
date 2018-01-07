import sys
import random

newGame = "YES"
team1Wins = 0
team2Wins = 0


class PlayersT1:
    numplayers = 0

    def __init__(self, name, hittingaverage, hits, power):
        PlayersT1.numplayers += 1
        self.name = name
        self.hittingaverage = hittingaverage
        self.hits = hits
        self.power = power


#
#


class PlayersT2:
    numplayers = 0

    def __init__(self, name, hittingaverage, hits, power):
        PlayersT2.numplayers += 1
        self.name = name
        self.hittingaverage = hittingaverage
        self.hits = hits
        self.power = power


#
#
#
#
#
#

# Team one
george = PlayersT1("george", 244, [0, 0, 0, 0], [80, 92, 96])
greg = PlayersT1("greg", 335, [0, 0, 0, 0], [80, 90, 98])
jorge = PlayersT1("jorge", 231, [0, 0, 0, 0], [80, 90, 98])
john = PlayersT1("john", 378, [0, 0, 0, 0], [80, 90, 98])
mike = PlayersT1("mike", 272, [0, 0, 0, 0], [80, 90, 98])
edmond = PlayersT1("edmond", 307, [0, 0, 0, 0], [80, 90, 98])
T1 = [george, greg, jorge, john, mike, edmond]

# Team two
jake = PlayersT2("jake", 275, [0, 0, 0, 0], [80, 90, 98])
nick = PlayersT2("nick", 335, [0, 0, 0, 0], [80, 90, 98])
jason = PlayersT2("jason", 249, [0, 0, 0, 0], [80, 90, 98])
robert = PlayersT2("robert", 352, [0, 0, 0, 0], [80, 90, 98])
matt = PlayersT2("matt", 411, [0, 0, 0, 0], [80, 90, 98])
fred = PlayersT2("fred", 121, [0, 0, 0, 0], [67, 78, 92])
T2 = [jake, nick, jason, robert, matt, fred]

while newGame == "YES":

    #
    #
    #

    # Sets constant variables
    inningCount = 0
    inningScoreT1 = [1]
    inningScoreT2 = []
    innings = 1
    inningString = []

    #
    #
    #

    h = 1
    while innings < 18 + 1:

        if h == 1:
            inningScoreT1.remove(1)
            h -= 1

        if innings % 2 == 0:
            inningCount += 1
        #
        #
        # Determines Batting Team
        if innings % 2 != 0:
            playershift = T1
        elif innings % 2 == 0:
            playershift = T2

        bases = [0, 0, 0, 0, 0, 0, 0, 0]
        # Bases are [Batter, First, Second, Third, run, run, run, run]

        outs = 0
        hit = 0
        strike = 0
        runs = 0

        x = 0
        while outs != 3:
            shift = 0

            # Batter Swings
            while strike < 3:
                bat = random.randint(1, 1000)
                PlayerStat = playershift[x].hittingaverage
                x += 1

                if x == len(playershift):
                    x = 0

                if bat <= PlayerStat:
                    bases[0] = 1

                    strength = random.randint(1, 100)
                    #
                    #
                    #

                    # Single
                    if strength <= playershift[x].power[0]:
                        playershift[x].hits[0] += 1
                        shift = 1

                    # Double
                    # if strength > playershift[x].power[0] and strength <= playershift[x].power[1]:
                    if playershift[x].power[0] < strength <= playershift[x].power[1]:
                        playershift[x].hits[1] += 1
                        shift = 2

                    # Triple
                    # if strength > playershift[x].power[1] and strength <= playershift[x].power[2]:
                    if playershift[x].power[1] < strength <= playershift[x].power[2]:
                        playershift[x].hits[2] += 1
                        shift = 3

                    # Home Run
                    if strength > playershift[x].power[2]:
                        playershift[x].hits[3] += 1
                        shift = 4

                    break

                if bat > PlayerStat:
                    strike += 1

            else:
                outs += 1
                # print("out")
    #
    #
    #
            # Sifts bases according to the hit made
            if bases[0] == 1:
                # print("\nTeam: ", innings)
                # shift = int(input("Single, Double, Triple, or HR (4) ?\n> "))

                for i in range(3, -1, -1):
                    bases[i + shift] = bases[i]

                for i in range(shift):
                    bases[i] = 0
                bases[0] = 0

            for i in range(4, 8):
                if bases[i] == 1:
                    runs += 1
                bases[i] = 0
    #
    #
    #
        if innings % 2 == 0:                    #
            inningScoreT2.append(runs)          #
            # print("Team 2")                   #
    #                                           # Keeps track of
        else:                                   # scores by inning
            inningScoreT1.append(runs)          #
            # print("Team 1")                   #
    #                                           #
        innings += 1                            #

        # New inning if a tie is present
        if innings > 18 and sum(inningScoreT1) == sum(inningScoreT2):
            innings -= 2

    # Format Scores for display
    inningScore1Format = '   '.join(map(str, inningScoreT1))
    inningScore2Format = '   '.join(map(str, inningScoreT2))

    # Print Score results
    print("Team 1:  ", inningScore1Format, " Total: ", sum(inningScoreT1))
    print("Team 2:  ", inningScore2Format, " Total: ", sum(inningScoreT2))

# ### Determine Winner ######################################
    if sum(inningScoreT1) > sum(inningScoreT2):             #
        print("Team One Won!\n")                            #
        team1Wins += 1                                      #
#                                                           #
    if sum(inningScoreT1) < sum(inningScoreT2):             #
        print("Team Two Won!\n")                            #
        team2Wins += 1                                      #
# ###########################################################

    response = "LOOP"

    while response == "LOOP":

        print("\nWould you like to see a players stats? Y/N")
        seeStat = input("> ").upper()

        if seeStat in ["Y", "YES"]:

            correctName = "FALSE"

            while correctName == "FALSE":

                # Print Player names
                playersT1 = [george.name, greg.name, jorge.name, john.name, mike.name, edmond.name]
                playersT2 = [jake.name, nick.name, jason.name, robert.name, matt.name, fred.name]
                print("\nPlayers: "
                      "\nTeam 1:", ","' '.join(map(str, playersT1)), "\nTeam 2:", ","' '.join(map(str, playersT2)))

                # Print stats
                print("\nWho's stats would you like to see?")
                playerStats = str(input("> ")).lower()

                if playerStats in playersT1 or playerStats in playersT2:

                    playerName = eval(str(playerStats))
                    print("\n\nStats for", playerName.name, "\n")
                    print("Batting Average", str(playerName.hittingaverage * .001)[1:])
                    print("\nTotal Hits: ", sum(playerName.hits))
                    print("Singles:", playerName.hits[0])
                    print("Doubles:", playerName.hits[1])
                    print("Triples:", playerName.hits[2])
                    print("Home Runs:", playerName.hits[3])

                    # Print Percentages
                    print("\nPower:\n"
                          "Singles:", playerName.power[0], "%\n"
                                                           "Doubles:"
                                                           "", playerName.power[1] - playerName.power[0], "%")

                    print("Triples:"
                          "", playerName.power[2] - playerName.power[1], "%\nHome "
                                                                         "Runs", 100 - playerName.power[2], "%")
                    #
                    #
                    correctName = "TRUE"

                else:
                    print("Im sorry", playerStats, "is not a valid response")
                    correctName = "FALSE"

        elif seeStat in ["N", "NO"]:
            response = "STOP"

        else:
            print(seeStat, "is not a valid response, Try again\n")
            response = "LOOP"

#
#
#
#
#

    # Ask to play new game
    response = "LOOP"
    while response == "LOOP":
        print("Would You like to simulate another game? Y/N")
        newGamePrompt = input("> ").upper()

        if newGamePrompt in ["Y", "YES"]:
            response = "STOP"

        elif newGamePrompt in ["N", "NO"]:
            response = "STOP"
            newGame = "STOPGAME"
        else:
            print(newGamePrompt, "is not a valid response, Try again\n")
            response = "LOOP"

# Total Results
print("\nExit Results:")
print("Team 1: ", team1Wins, "wins")
print("Team 2: ", team2Wins, "wins")

# Prompt for Exit
input("\n\nPress Enter to Exit")
sys.exit()



