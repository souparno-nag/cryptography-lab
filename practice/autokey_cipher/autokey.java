import java.util.Scanner;

public class autokey {
    static String generateKey(String keyWord, String plaintext) {
        String key = keyWord;
        int n = plaintext.length(), k = 0;
        while (key.length() < n) {
            key += plaintext.charAt(k++);
        }
        return key;
    }
    static String encrypt(String key, String plaintext) {
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            ciphertext += (char)(((plaintext.charAt(i)-'A')+(key.charAt(i)-'A'))%26+'A');
        }
        return ciphertext;
    }
    static String decrypt(String key, String ciphertext) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            plaintext += (char)(((ciphertext.charAt(i)-'A')-(key.charAt(i)-'A')+26)%26+'A');
        }
        return plaintext;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the keyword:");
        String keyWord = sc.nextLine();
        System.out.println("Enter the plaintext:");
        String plaintext = sc.nextLine();

        String key = generateKey(keyWord, plaintext);
        System.out.println("The key is " + key);

        String encrypted = encrypt(key, plaintext);
        System.out.println("The encrypted text is " + encrypted);
        String decrypted = decrypt(key, encrypted);
        System.out.println("The decryptrd text is " + decrypted);

        sc.close();
    }
}
