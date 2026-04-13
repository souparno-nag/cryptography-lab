import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String message = "HELLO";
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] x = md.digest(message.getBytes());

        String hash = "";
        for (byte b : x) hash += String.format("%02x", b);

        System.out.println(hash);
    }
}
