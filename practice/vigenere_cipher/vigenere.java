import java.util.Scanner;

public class vigenere {
    static String generateKey (String keyWord, int inputLength) {
        String key = "";
        while (key.length() < inputLength) {
            for (int i = 0; i < keyWord.length(); i++) {
                key += keyWord.charAt(i);
                if (key.length() == inputLength) break;
            }
        }
        return key;
    }
    static String encrypt(String key, String plaintext) {
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            ciphertext += (char)(((plaintext.charAt(i)-'A')+(key.charAt(i)-'A'))%26 + 'A');
        }
        return ciphertext;
    }
    static String decrypt(String key, String ciphertext) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            plaintext += (char)(((ciphertext.charAt(i)-'A')-(key.charAt(i)-'A')+26)%26 + 'A');
        }
        return plaintext;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the keyword:");
        String keyWord = sc.nextLine();
        System.out.println("Enter the plaintext:");
        String plaintext = sc.nextLine();

        int n = plaintext.length();
        String key = generateKey(keyWord, n);
        System.out.println("The key is " + key);

        String ciphertext = encrypt(key, plaintext);
        System.out.println("The encrypted text is " + ciphertext);
        String decryptedPlaintext = decrypt(key, ciphertext);
        System.out.println("The decrypted text is " + decryptedPlaintext);

        sc.close();
    }
}
