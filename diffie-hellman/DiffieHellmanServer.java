import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class DiffieHellmanServer {
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

            // set values
            System.out.print("\nEnter prime number: ");
            BigInteger q = BigInteger.valueOf(sc.nextInt());
            System.out.print("Enter primitive root: ");
            BigInteger alpha = BigInteger.valueOf(sc.nextInt());
            System.out.print("Enter private key: ");
            BigInteger privateKey = BigInteger.valueOf(sc.nextInt());

            BigInteger publicKey = DiffieHellmanEncryption.generatePublicKey(q, alpha, privateKey);

            out.writeUTF(publicKey.toString());
            System.out.println("\nSent public key to server...");

            String otherPublicKey = in.readUTF();
            System.out.println("\nThe public key received from client is "+ otherPublicKey + "\n");
            BigInteger sharedPublicKey = new BigInteger(otherPublicKey);

            BigInteger sharedSecretKey = DiffieHellmanEncryption.calculateSharedSecretKey(q, privateKey, sharedPublicKey);
            System.out.println("The shared private key is " + sharedSecretKey);
            sc.close();
            socket.close();
            serverSocket.close();
            System.out.println("Server closed");
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
