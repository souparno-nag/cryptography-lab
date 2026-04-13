import java.net.Socket;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;

public class rsa_client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            Scanner sc = new Scanner(System.in);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.print("Enter the plaintext: ");
            BigInteger M = BigInteger.valueOf(sc.nextInt());
            String publicKey_string = in.readUTF();
            BigInteger publicKey = new BigInteger(publicKey_string);
            String N_string = in.readUTF();
            BigInteger N = new BigInteger(N_string);

            BigInteger encryptedMessage = rsa_encryption.encryption(M, publicKey, N);
            out.writeUTF(encryptedMessage.toString());
            
            sc.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
