import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;

public class rsa_server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started on port 5000");
            System.out.println("Waiting for client connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");    
            Scanner sc = new Scanner(System.in);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            System.out.print("Enter the first prime number: ");
            BigInteger p = BigInteger.valueOf(sc.nextInt());
            System.out.print("Enter the second prime number: ");
            BigInteger q = BigInteger.valueOf(sc.nextInt());
            System.out.print("Enter the private encryption key: ");
            BigInteger publicKey = BigInteger.valueOf(sc.nextInt());

            BigInteger N = p.multiply(q);
            out.writeUTF(publicKey.toString());
            out.writeUTF(N.toString());
            BigInteger privateKey = rsa_encryption.generatePrivateKey(p, q, publicKey);

            String encryptedMessage_string = in.readUTF();
            BigInteger encryptedMessage = new BigInteger(encryptedMessage_string);

            BigInteger decrypted = rsa_encryption.decryption(encryptedMessage, privateKey, N);
            System.out.println("\nThe decrypted message is " + decrypted);
            sc.close();
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
