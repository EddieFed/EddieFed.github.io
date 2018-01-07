import random
import numpy as np
import matplotlib.pyplot as plt
boxLen = []
for x in range(10000):
    boxes = []
    allFound = 0

    OneFound = 0
    TwoFound = 0
    ThreeFound = 0
    FourFound = 0
    FiveFound = 0
    SixFound = 0

    while allFound == 0:
        boxes.append(random.randint(1,6))
        for i in range(len(boxes)):
            if boxes[i] == 1:
                OneFound = 1
            if boxes[i] == 2:
                TwoFound = 1
            if boxes[i] == 3:
                ThreeFound = 1
            if boxes[i] == 4:
                FourFound = 1
            if boxes[i] == 5:
                FiveFound = 1
            if boxes[i] == 6:
                SixFound = 1
            if OneFound == 1 and TwoFound == 1 and ThreeFound == 1 and FourFound == 1 and FiveFound == 1 and SixFound == 1:
                allFound = 1
                boxLen.append(len(boxes))

totalValue = 0
for y in range(len(boxLen)):
    totalValue += boxLen[y]
totalValue /= len(boxLen)

print("It would take on average", totalValue, "Boxes to get all 6 prizes\n")

boxLen = sorted(boxLen)

print("The median number of boxes required for 6 prizes is", boxLen[int(len(boxLen)/2)])
plt.hist(boxLen,bins=range(min(boxLen),max(boxLen)+2), color="purple")
plt.title("Boxes to Trials")
plt.xlabel("Number of Boxes")
plt.ylabel("Number of Trials")
plt.show()























