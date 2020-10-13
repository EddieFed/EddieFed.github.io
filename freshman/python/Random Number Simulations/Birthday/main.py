import random
import numpy as np
import matplotlib.pyplot as plt
yesNo = []

for i in range(1000):
    bd = np.random.random_integers(1, 365, 30)
    bd = list(bd)
    bd.sort()

    print(bd)

    for i in range(0, len(bd) - 1):
        if bd[i] == bd[i + 1]:
            yesNo.append(int(1))
            break
    else:
        yesNo.append(int(5))



plt.hist(yesNo, bins=range(min(yesNo),max(yesNo)+2))
plt.title("Birthday Matches")
plt.xlabel("Yes                                                                                                          No")
# plt.xticks(range(1.5,2.5,1), "Yes", "No")
plt.ylabel("Number of Yes/No")
plt.show()




