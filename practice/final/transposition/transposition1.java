import java.util.*;
import java.lang.*;

public class transposition1 {
    static Integer[] getColumnOrder(String key) {
        int n = key.length();
        Character[] chars = new Character[n];
        Integer[] indices = new Integer[n];

        for (int i = 0; i < n; i++) {
            chars[i] = key.charAt(i);
            indices[i] = i;
        }

        Arrays.sort(indices, (i1, i2) -> {
            int comp = Character.compare(chars[i1], chars[i2]);
            if (comp != 0) return comp;
            return Integer.compare(i1, i2);
        });

        return indices;
    }
    static String encrypt(String message, String key) {
        Integer[] colOrder = getColumnOrder(key);
        int cols = key.length();
        int rows = (message.length() + cols - 1)/cols;

        char[][] matrix = new char[rows][cols];

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (k < message.length()) ? message.charAt(k++) : '_';
            }
        }

        String cipher = "";
        
        for (int c : colOrder) {
            for (int r = 0; r < rows; r++) {
                cipher += matrix[r][c];
            }
        }
        return cipher;
    }
    static String decrypt(String cipher, String key) {
        int cols = key.length();
        int rows = cipher.length() / cols;

        char[][] matrix = new char[rows][cols];
        Integer[] colOrder = getColumnOrder(key);

        int k = 0;
        for (int c : colOrder) {
            for (int r = 0; r < rows; r++) {
                matrix[r][c] = cipher.charAt(k++);
            }
        }

        String plain = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != '_') plain += matrix[i][j];
            }
        }
        return plain;
    }
    public static void main(String[] args) {
        String message = "HELLOABCDEFGH";
        String key = "OKAY";

        String cipher = encrypt(message, key);
        System.out.println(cipher);

        System.out.println(decrypt(cipher, key));
    }
}
