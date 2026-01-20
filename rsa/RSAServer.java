import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.ServerSocket;

public class RSAServer {
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started on port 5000");
            System.out.println("Waiting for client connection...");
            
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            

            RSAEncryption.generateKeys();
            BigInteger[] publicKey = RSAEncryption.getPublicKey();
            out.writeUTF(publicKey[0].toString()); // Send e
            out.writeUTF(publicKey[1].toString()); // Send N
            System.out.println("Sent Public Key to Client.");
            BigInteger[] privateKey = RSAEncryption.getPrivateKey();
            String ciphertext = in.readUTF();
            BigInteger encrypted = new BigInteger(ciphertext);
            
            BigInteger d = privateKey[0];
            BigInteger p = privateKey[1];
            BigInteger q = privateKey[2];

            BigInteger N = p.multiply(q);
            BigInteger decrypted = encrypted.modPow(d, N);

            System.out.println("\n" + encrypted + "^" + d + " mod " + N + " = " + decrypted);
            System.out.println("Decrypted: " + decrypted);
            socket.close();
            serverSocket.close();
            System.out.println("Server closed");
            
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}