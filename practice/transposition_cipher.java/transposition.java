import java.util.Scanner;

public class transposition {
    static String encrypt (String plaintext, String key) {
        int col = key.length();
        int row = (int) (Math.ceilDiv(plaintext.length(), col));

        char[][] matrix = new char[row][col];
        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = (k < plaintext.length() ? plaintext.charAt(k++) : '_');
            }
        }

        String ciphertext = "";
        for (char c : key.toCharArray()) {
            int cols = key.indexOf(c);
            for (int i = 0; i < row; i++) {
                ciphertext += matrix[i][cols];
            }
        }
        return ciphertext;
    }
    static String decrypt (String ciphertext, String key) {
        int cols = key.length();
        int rows = ciphertext.length()/cols;

        char[][] matrix = new char[rows][cols];
        int k = 0;
        for (char c: key.toCharArray()) {
            int col = key.indexOf(c);
            for (int i = 0; i < rows; i++) {
                matrix[i][col] = ciphertext.charAt(k++);
            }
        }

        String plaintext = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != '_') plaintext += matrix[i][j];                
            }
        }
        return plaintext;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the key:");
        String key = sc.nextLine();
        System.out.println("Enter the plaintext:");
        String plaintext = sc.nextLine();

        plaintext = plaintext.toUpperCase();
        key = key.toUpperCase();

        String ciphertext = encrypt(plaintext, key);
        System.out.println(ciphertext);

        String decrypted = decrypt(ciphertext, key);
        System.out.println(decrypted);

        sc.close();
    }
}
