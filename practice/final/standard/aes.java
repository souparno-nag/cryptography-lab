import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class aes {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        SecretKeySpec sks = new SecretKeySpec(sc.next().getBytes(), "AES");

        Cipher cp = Cipher.getInstance("AES");
        cp.init(Cipher.ENCRYPT_MODE, sks);
        String b64 = Base64.getEncoder().encodeToString(cp.doFinal(sc.next().getBytes()));
        System.out.println(b64);

        cp.init(Cipher.DECRYPT_MODE, sks);
        System.out.println(new String(cp.doFinal(Base64.getDecoder().decode(b64))));

        sc.close();
    }
}
