import java.util.Scanner;

public class railway {
    static String encrypt(String plaintext, int rows) {
        String ciphertext = "";
        int padding = (int)(Math.ceilDiv(plaintext.length(), rows) * rows) - plaintext.length();
        for (int i = 0; i < padding; i++) plaintext += 'Z';
        for (int i = 0; i < rows; i++) {
            for (int j = i; j < plaintext.length(); j += rows) {
                ciphertext += plaintext.charAt(j);
            }
        }
        return ciphertext;
    }
    static String decrypt(String ciphertext, int rows) {
        String plaintext = "";
        int col = (int) (Math.ceilDiv(ciphertext.length(), rows));
        for (int i = 0; i < col; i++) {
            for (int j = i; j < ciphertext.length(); j += col) {
                plaintext += ciphertext.charAt(j);
            }
        }
        return plaintext;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plaintext: ");
        String plaintext = sc.nextLine();
        System.out.println("Enter the number of rows in the cipher: ");
        int rows = sc.nextInt();

        plaintext = plaintext.toUpperCase();

        String ciphertext = encrypt(plaintext, rows);
        System.out.println(ciphertext);
        String decryptedPlaintext = decrypt(ciphertext, rows);
        System.out.println(decryptedPlaintext);
        sc.close();
    }
}
