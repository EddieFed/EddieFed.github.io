import random
import numpy as np
import matplotlib.pyplot as plt
total = []
die1 = np.random.random_integers(1, 6, 1000)
die2 = np.random.random_integers(1, 6, 1000)
for i in range(len(die1)):
    total.append(int(((die1[i]+die2[i])/2) * 10))
print(total)

plt.hist(total, bins=range(min(total),max(total)+2))
plt.title("Rolling Dice")
plt.xlabel("Average Of Dice (Multiplied by 10)")
plt.ylabel("Times Average is Rolled")
# my_xTicks = [1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0]
#
# plt.xticks(total, my_xTicks)

plt.xticks(range(10, 65, 5), ("1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0", "5.5", "6.0"))
plt.show()

# "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0", "5.5", "6.0"