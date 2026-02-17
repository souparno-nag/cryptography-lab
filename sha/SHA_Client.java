import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SHA_Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server ...");

            Scanner sc = new Scanner(System.in);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // set values
            System.out.print("\nEnter key: ");
            String key = sc.nextLine();
            System.out.print("Enter message: ");
            String msg = sc.nextLine();
            
            String HMAC = SHA_Hash_Algorithm.hmacSHA256(msg, key);
            System.out.println("The HMAC for the given message and key is: " + HMAC);

            out.writeUTF(key);
            out.writeUTF(HMAC);

            sc.close();
            socket.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}