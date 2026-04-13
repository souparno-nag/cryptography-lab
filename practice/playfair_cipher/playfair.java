import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class playfair {
    static char[][] keyTable = new char[5][5];

    static void createTable(String key) {
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) != 'J') {
                set.add(key.charAt(i));
            } else {
                set.add('I');
            }
        }
        char[] hash = new char[25];
        Iterator<Character> it = set.iterator();
        int k = 0;
        while (it.hasNext()) {
            hash[k++] = it.next();
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (!set.contains(ch) && ch != 'J'){
                set.add(ch);
                hash[k++] = ch;
            }
        }
        k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyTable[i][j] = hash[k++];
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(keyTable[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int[] search(char a, char b) {
        if (a == 'J') a = 'I';
        if (b == 'J') a = 'I';
        if (a == b) b = 'X';
        int[] arr = new int[4];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyTable[i][j] == a) {
                    arr[0] = i; arr[1] = j;
                }
                if (keyTable[i][j] == b) {
                    arr[2] = i; arr[3] = j;
                }
            }
        }
        return arr;
    }

    static String encrypt(String plaintext) {
        if (plaintext.length() % 2 != 0) plaintext += 'Z';
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i += 2) {
            int[] arr = search(plaintext.charAt(i), plaintext.charAt(i+1));
            if (arr[0] == arr[2]) {
                ciphertext += keyTable[arr[0]][(arr[1] + 1)%5];
                ciphertext += keyTable[arr[2]][(arr[3] + 1)%5];
            } else if (arr[1] == arr[3]) {
                ciphertext += keyTable[(arr[0] + 1)%5][arr[1]];
                ciphertext += keyTable[(arr[2] + 1)%5][arr[3]];
            } else {
                ciphertext += keyTable[arr[0]][arr[3]];
                ciphertext += keyTable[arr[2]][arr[1]];
            }
        }
        return ciphertext;
    }

    static String decrypt(String ciphertext) {
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int[] arr = search(ciphertext.charAt(i), ciphertext.charAt(i+1));
            if (arr[0] == arr[2]) {
                plaintext += keyTable[arr[0]][(arr[1] - 1 + 5)%5];
                plaintext += keyTable[arr[2]][(arr[3] - 1 + 5)%5];
            } else if (arr[1] == arr[3]) {
                plaintext += keyTable[(arr[0] - 1 + 5)%5][arr[1]];
                plaintext += keyTable[(arr[2] - 1 + 5)%5][arr[3]];
            } else {
                plaintext += keyTable[arr[0]][arr[3]];
                plaintext += keyTable[arr[2]][arr[1]];
            }
        }
        return plaintext;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the key:");
        String key = sc.nextLine();
        System.out.println("Enter the plaintext");
        String plaintext = sc.nextLine();

        key = key.toUpperCase();
        plaintext = plaintext.toUpperCase();

        createTable(key);

        String ciphertext = encrypt(plaintext);
        System.out.println("The encrypted text is " + ciphertext);

        String decryptedPlaintext = decrypt(ciphertext);
        System.out.println("The decrypted text is " + decryptedPlaintext);

        sc.close();
    }
}
