import java.util.Arrays;

public class RailFenceCipher {
    static String encrypt(String plaintext, int key) {
        char[][] matrix = new char[key][plaintext.length()];
        for (int i = 0; i < key; i++)
            Arrays.fill(matrix[i], '\n');
        boolean dirDown = false;
        int row = 0, col;
        for (int i = 0; i < plaintext.length(); i++) {
            if (row == 0 || row == key - 1)
                dirDown = !dirDown;
            col = i;
            matrix[row][col] = plaintext.charAt(i);
            if (dirDown)
                row++;
            else
                row--;
        }
        String cipher = "";
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < plaintext.length(); j++) {
                if (matrix[i][j] != '\n')
                    cipher += matrix[i][j];
            }
        }
        return cipher;
    }

    static String decrypt(String ciphertext, int key) {
        char[][] matrix = new char[key][ciphertext.length()];
        for (int i = 0; i < key; i++)
            Arrays.fill(matrix[i], '\n');
        boolean dirDown = true;
        int row = 0, col = 0;
        for (int j = 0; j < ciphertext.length(); j++) {
            if (row == 0)
                dirDown = true;
            else if (row == key - 1)
                dirDown = false;
            col = j;
            matrix[row][col] = '*';
            if (dirDown)
                row++;
            else
                row--;
        }
        String plain = "";
        int k = 0;
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < ciphertext.length(); j++) {
                if (matrix[i][j] == '*')
                    matrix[i][j] = ciphertext.charAt(k++);
            }
        }
        row = 0;
        col = 0;
        dirDown = true;
        for (int j = 0; j < ciphertext.length(); j++) {
            if (row == 0)
                dirDown = true;
            else if (row == key - 1)
                dirDown = false;
            col = j;
            if (matrix[row][col] != '*')
                plain += matrix[row][col];
            if (dirDown)
                row++;
            else
                row--;
        }
        return plain;
    }

    public static void main(String[] args) {
        String message = "INFORMATIONSECURITY";
        int key = 3;
        String cipher = encrypt(message, key);
        System.out.println(cipher);
        System.out.println(decrypt(cipher, key));
    }
}
