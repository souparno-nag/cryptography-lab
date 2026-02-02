import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

public class DiffieHellmanClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server ...");

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
            System.out.println("\nThe public key received from server is "+ otherPublicKey + "\n");
            BigInteger sharedPublicKey = new BigInteger(otherPublicKey);

            BigInteger sharedSecretKey = DiffieHellmanEncryption.calculateSharedSecretKey(q, privateKey, sharedPublicKey);
            System.out.println("The shared private key is " + sharedSecretKey);
            sc.close();
            socket.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
