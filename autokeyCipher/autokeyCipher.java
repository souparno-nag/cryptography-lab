package autokeyCipher;
import java.util.*;

public class autokeyCipher {
    static String lowerCaseAndRemoveSpace (String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isWhitespace(ch)) continue;
            result += Character.toLowerCase(ch);
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the autokey: ");
        char ak = Character.toLowerCase(sc.next().charAt(0));
        sc.nextLine(); // clear the buffer
        System.out.print("Enter the plain text (text to be encrypted): ");
        String str = sc.nextLine();
        str = lowerCaseAndRemoveSpace(str);
        String key = "";
        key += ak;
        for (int i = 0; i < str.length()-1; i++) {
            key += str.charAt(i);
        } 
        System.out.println("Key: "+key);
        // encryption
        String ciphertext = "";
        for (int i = 0; i < str.length(); i++) {
            int c1 = str.charAt(i) - 'a';
            int c2 = key.charAt(i) - 'a';
            char ch = (char) ((c1 + c2)%26 + 'a');
            ciphertext += ch;
        }
        System.out.println("Encrypted text: " + ciphertext);
        // decryption
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            int c1 = ciphertext.charAt(i) - 'a';
            int c2 = key.charAt(i) - 'a';
            char ch = (char) ((c1 - c2 + 26)%26 + 'a');
            plaintext += ch;
        }
        System.out.println("Decrypted text: " + plaintext);
        sc.close();
    }
}
