import java.util.Scanner;

public class caesar_cipher_encryption {
    static String encryption(String plaintext, int key) {
        String encryptedString = "";
        for (int i = 0; i < plaintext.length(); i++) {
            encryptedString += (char)((plaintext.charAt(i) - 'A' + key)%26 + 'A');
        }
        return encryptedString;
    }
    static String decryption(String ciphertext, int key) {
        String decryptedString = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            decryptedString += (char)(((ciphertext.charAt(i) - 'A' - key)%26 + 26)%26+ 'A');
        }
        return decryptedString;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the plaintext: ");
        String plaintext = sc.nextLine();
        plaintext = plaintext.toUpperCase();
        System.out.print("Enter the key: ");
        int key = sc.nextInt();

        String ciphertext = encryption(plaintext, key);
        System.out.println("The encrypted string is " + ciphertext);
        String decryptedPlaintext = decryption(ciphertext, key);
        System.out.println("The decrypted plaintext is " + decryptedPlaintext);
        
        sc.close();
    }
}
