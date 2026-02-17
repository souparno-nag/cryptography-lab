import java.io.DataInputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public class SHA_Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started on port 5000");
            System.out.println("Waiting for client connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected!"); 

            Scanner sc = new Scanner(System.in);
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // set values
            System.out.print("\nEnter message for verification: ");
            String msg = sc.nextLine();
            
            String key = in.readUTF();
            String HMAC = in.readUTF();

            String generateHMAC = SHA_Hash_Algorithm.hmacSHA256(msg, key);

            if (generateHMAC.equals(HMAC)) {
                System.out.println("The HMAC is verified.");
            } else {
                System.out.println("The HMAC is not verified.");
            }
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