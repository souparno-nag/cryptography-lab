import java.io.*;
import java.net.*;

public class DESServer {
    
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started on port 5000");
            System.out.println("Waiting for client connection...");
            
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            
            while (true) {
                String cipherText = in.readUTF();
                
                if (cipherText.equalsIgnoreCase("exit")) {
                    System.out.println("\nClient disconnected");
                    break;
                }
                
                String key = in.readUTF();
                
                System.out.println("\n=== SERVER SIDE ===");
                System.out.println("Received Encrypted Message: " + cipherText);
                System.out.println("Key: " + key);
                
                System.out.println("\nDecrypting message...");
                DESEncryption.decryptFinal(cipherText, key);
            }
            
            socket.close();
            serverSocket.close();
            System.out.println("\nServer closed");
            
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}