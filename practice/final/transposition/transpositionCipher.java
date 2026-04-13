import java.util.*;

public class transpositionCipher {

    private static Integer[] getColumnOrder(String key) {
        int len = key.length();
        Character[] chars = new Character[len];
        Integer[] indices = new Integer[len];

        for (int i = 0; i < len; i++) {
            chars[i] = key.charAt(i);
            indices[i] = i;
        }

        Arrays.sort(indices, (i1, i2) -> {
            int cmp = Character.compare(chars[(int)i1], chars[(int)i2]);
            if (cmp != 0) return cmp;
            return Integer.compare(i1, i2);
        });

        return indices;
    }

    private static String encrypt(String plaintext, String key) {
        int col = key.length();
        int row = (plaintext.length() + col - 1)/col;

        char[][] matrix = new char[row][col];
        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = (k < plaintext.length()) ? plaintext.charAt(k++) : '_';
            }
        }

        Integer[] order = getColumnOrder(key);
        String ciphertext = "";
        for (int colIdx : order) {
            for (int i = 0; i < row; i++) {
                ciphertext += matrix[i][colIdx];
            }
        }

        return ciphertext;
    }

    private static String decrypt(String ciphertext, String key) {
        int col = key.length();
        int row = (ciphertext.length())/col;

        char[][] matrix = new char[row][col];
        Integer[] order = getColumnOrder(key);
        int k = 0;

        for (int colIdx : order) {
            for (int j = 0; j < row; j++) {
                matrix[j][colIdx] = ciphertext.charAt(k++);
            }
        }

        String plaintext = "";

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] != '_') plaintext += matrix[i][j];
            }
        }

        return plaintext;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String key = "3142";
        String plaintext = "NETWORKSECURITY";

        String ciphertext = encrypt(plaintext, key);
        System.out.println("Encrypted: " + ciphertext);

        String decrypted = decrypt(ciphertext, key);
        System.out.println("Decrypted: " + decrypted);

        sc.close();
    }
}