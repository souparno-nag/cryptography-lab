import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MD5_Client {
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
            
            String MAC = MD5_Hash_Algorithm.generateMAC(key, msg);
            System.out.println("The MAC for the given message and key is: " + MAC);

            out.writeUTF(key);
            out.writeUTF(MAC);

            sc.close();
            socket.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
