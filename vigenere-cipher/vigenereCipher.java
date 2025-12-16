import java.util.*;

public class vigenereCipher {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = sc.nextLine();
        System.out.print("Enter the string to be encrypted: ");
        String plaintext = sc.nextLine();
        System.out.println("Encryption Table -");
        System.out.print(" ");
        for (int i = 0; i < 26; i++) {
            System.out.print(String.format("  %c", (char)('A'+i)));
        }
        System.out.println();
        for (int i = 0; i < 26; i++) {
            char ch = (char) ('A'+i);
            System.out.print(String.format("%c  ", ch));
            for (int j = 0; j < 26; j++) {
                System.out.print(String.format("%c  ", (char)((ch - 'A' + j)%26 + 'A')));
            }
            System.out.println();
        }
        String keyword = "";
        for (int i = 0; i < plaintext.length(); i++) {
            int idx = i%key.length();
            keyword += key.charAt(idx);
        }
        System.out.println("Key word: " + keyword);
        String encrypted = "";
        for (int i = 0; i < plaintext.length(); i++) {
            int n1 = plaintext.charAt(i) - 'A';
            int n2 = keyword.charAt(i) - 'A';
            char ch = (char) ((n1 + n2)%26 + 'A');
            encrypted += ch;
        }
        System.out.println("Encrypted text: " + encrypted);
        String decrypted = "";
        for (int i = 0; i < encrypted.length(); i++) {
            int n1 = encrypted.charAt(i) - 'A';
            int n2 = keyword.charAt(i) - 'A';
            char ch = (char) ((n1 - n2 + 26)%26 + 'A');
            decrypted += ch;
        }
        System.out.println("Decrypted Text: " + decrypted);
        sc.close();
    }
}
