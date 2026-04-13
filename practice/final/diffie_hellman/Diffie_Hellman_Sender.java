import java.net.Socket;
import java.math.BigInteger;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Diffie_Hellman_Sender {
    public static void main(String[] args) {
        try {
            BigInteger q = BigInteger.valueOf(23);
            BigInteger alpha = BigInteger.valueOf(5);

            Socket socket = new Socket("localhost", 9000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            BigInteger X = BigInteger.valueOf(6);
            BigInteger Y = Diffie_Hellman_Encryption.calculatePublicKey(X, q, alpha);

            out.writeUTF(Y.toString());
            String y = in.readUTF();

            BigInteger Yc = new BigInteger(y);

            BigInteger K = Diffie_Hellman_Encryption.calculateSharedSecretKey(Yc, X, q);

            System.out.println(K);

            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
