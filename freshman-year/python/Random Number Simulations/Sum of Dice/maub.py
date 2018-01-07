import random
import numpy as np
import matplotlib.pyplot as plt
total = []
die1 = np.random.random_integers(1, 6, 1000)
die2 = np.random.random_integers(1, 6, 1000)
for i in range(len(die1)):
    total.append(die1[i]+die2[i])
print(total)


plt.hist(total,bins=range(min(total),max(total)+2))
plt.title("Rolling Dice")
plt.xlabel("Sum of 2 Dice")
plt.ylabel("Times Sum is Rolled")
plt.show()
