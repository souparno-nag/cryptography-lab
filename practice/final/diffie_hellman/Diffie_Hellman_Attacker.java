import java.net.Socket;
import java.net.ServerSocket;
import java.math.BigInteger;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Diffie_Hellman_Attacker {
    public static void main(String[] args) {
        try {
            ServerSocket server1 = new ServerSocket(9000);
            ServerSocket server2 = new ServerSocket(9002);
            Socket socket1 = server1.accept();
            Socket socket2 = server2.accept();

            BigInteger q = BigInteger.valueOf(23);
            BigInteger alpha = BigInteger.valueOf(5);

            BigInteger X = BigInteger.valueOf(13);
            BigInteger Y = Diffie_Hellman_Encryption.calculatePublicKey(X, q, alpha);

            DataInputStream in1 = new DataInputStream(socket1.getInputStream());
            DataOutputStream out1 = new DataOutputStream(socket1.getOutputStream());

            DataInputStream in2 = new DataInputStream(socket2.getInputStream());
            DataOutputStream out2 = new DataOutputStream(socket2.getOutputStream());

            out1.writeUTF(Y.toString());
            out2.writeUTF(Y.toString());

            String y1 = in1.readUTF();
            String y2 = in2.readUTF();

            BigInteger Y1 = new BigInteger(y1);
            BigInteger Y2 = new BigInteger(y2);

            BigInteger K1 = Diffie_Hellman_Encryption.calculateSharedSecretKey(Y1, X, q);
            BigInteger K2 = Diffie_Hellman_Encryption.calculateSharedSecretKey(Y2, X, q);

            System.out.println(K1);
            System.out.println(K2);

            socket1.close(); socket2.close();
            server1.close(); server2.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
