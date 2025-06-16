# DES Encryption & Decryption in Java

This project demonstrates how to perform **symmetric encryption and decryption using the DES (Data Encryption Standard)** algorithm in Java. It includes a simple implementation that encrypts a string using a DES key and then decrypts it back to its original form.

## 🔐 About DES

DES is a symmetric-key block cipher developed in the 1970s. It encrypts data in 64-bit blocks using a 56-bit key. While considered insecure for modern applications, it remains important in learning cryptographic fundamentals.

---

## 📁 File Included

- `Encrypt_Decrypt_DES.java`  
  Contains both encryption and decryption logic using `javax.crypto` libraries.

---

## 🛠️ How It Works

1. A plaintext string is converted to bytes.
2. A DES key is generated (or hardcoded).
3. The plaintext is encrypted using DES.
4. The ciphertext is decrypted back to plaintext.

---

## ▶️ How to Run

```bash
javac Encrypt_Decrypt_DES.java
java Encrypt_Decrypt_DES
- Follow the promts
