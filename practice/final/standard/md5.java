import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class md5 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String message = "HELLO";
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] x = md.digest(message.getBytes());

        String hash = "";
        for (byte b : x) hash += String.format("%02x", b);

        System.out.println(hash);
    }
}
