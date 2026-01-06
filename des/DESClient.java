import java.io.*;
import java.net.*;
import java.util.Scanner;

public class DESClient {
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server");

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter key: ");
            String key = sc.nextLine();
            
            while (true) {
                System.out.print("\nEnter plaintext (or 'exit' to quit): ");
                String plaintext = sc.nextLine();
                
                if (plaintext.equalsIgnoreCase("exit")) {
                    out.writeUTF("exit");
                    break;
                }                
                
                System.out.println("\nOriginal Message: " + plaintext);
                System.out.println("Key: " + key);
                
                String cipherText = DESEncryption.encryptionFinal(plaintext, key);
                
                System.out.println("\n=== CLIENT SIDE ===");
                System.out.println("Encrypted Message: " + cipherText);
                
                out.writeUTF(cipherText);
                out.writeUTF(key);
                
                System.out.println("\nSent encrypted message to server...");
            }
            
            sc.close();
            socket.close();
            System.out.println("Connection closed");
            
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}