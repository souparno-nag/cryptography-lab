import java.util.*;

public class transpositionCipher {
    static Map<Character, Integer> keyMap = new HashMap<>();
    static void setPermutationOrder (String key) {
        for (int i = 0; i < key.length(); i++) {
            keyMap.put(key.charAt(i), i);
        }
    }
    static String encryptMessage (String msg, String key) {
        String cipher = "";
        int col = key.length();
        int row = (int) Math.ceil((double) msg.length()/col);
        char[][] matrix = new char[row][col];
        for (int i = 0, k = 0; i < row; i++) {
            for (int j = 0; j < col;) {
                if (k < msg.length()) {
                    char ch = msg.charAt(k);
                    if (Character.isLetter(ch) || ch == ' ') {
                        matrix[i][j] = ch;
                        j++;
                    }
                    k++;
                } else {
                    matrix[i][j] = '_';
                    j++;
                }
            }
        }
        for (Map.Entry<Character, Integer> entry : keyMap.entrySet()) {
            int columnIndex = entry.getValue();
            for (int i = 0; i < row; i++) {
                char ch = matrix[i][columnIndex];
                if (Character.isLetter(ch) || ch == ' ' || ch == '_') {
                    cipher += ch;
                }
            }
        }
        return cipher;
    }
    static String decryptMessage (String cipher, String key) {
        StringBuilder msg = new StringBuilder();
        int col = key.length();
        int row = (int) Math.ceil((double) cipher.length()/col);
        char[][] matrix = new char[row][col];
        int k = 0;
        for (int j = 0; j < col; j++) {
            for (int i = 0; i < row; i++) {
                matrix[i][j] = cipher.charAt(k++);
            }
        }
        int index = 0;
        for (Map.Entry<Character, Integer> entry : keyMap.entrySet()) {
            entry.setValue(index++);
        }
        char[][] decCipher = new char[row][col];
        for (int l = 0; l < key.length(); l++) {
            int colIndex = keyMap.get(key.charAt(l));
            for (int i = 0; i < row; i++) {
                decCipher[i][l] = matrix[i][colIndex];
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (decCipher[i][j] != '_') {
                    msg.append(decCipher[i][j]);
                }
            }
        }
        return msg.toString();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the text to be encrypted: ");
        String plaintext = sc.nextLine();
        System.out.print("Enter the key: ");
        String key = sc.nextLine();
        setPermutationOrder(key);
        String encrypted = encryptMessage(plaintext, key);
        System.out.println("Encrypted Text: " + encrypted);
        String decrypted = decryptMessage(encrypted, key);
        System.out.println("Decrypted Text: " + decrypted);
        sc.close();
    }
}
