import sys
from Projects.Cipher.EncryptDecrypt.ForceDecrypt import decrypt as force
from Projects.Cipher.EncryptDecrypt.Encrypt import encrypt
from Projects.Cipher.EncryptDecrypt.Decrypt import decrypt

print("Encrypt or Decrypt a message?")
print('Type \n"E" To Encrypt\n"D" To Decrypt Or \n"F" To Force the Decryption')
EncryptDecrypt = input("> ").upper()

if EncryptDecrypt == "E" or EncryptDecrypt == "D":
    print("\n")

    print("What's the key?")
    key = 0
    while key not in (1, 25):

        key = int(input("> "))
        if key > 0 and key < 26:
            break
        else:
            print("you have made an invalid choice, try again.")

    # Encrypt
    if EncryptDecrypt == "E":

        encrypt(key)

    # Decrypt
    if EncryptDecrypt == "D":

        decrypt(key)

# Force Decryption
if EncryptDecrypt == "F":
    print("\nThe Program Will Check all 26 Possibilities")
    print("Type Your Encrypted word")
    letter = input("> ").upper()

    for i in range(1, 26):
        force(i, letter)


input("\nPress Enter to Exit")
sys.exit()
