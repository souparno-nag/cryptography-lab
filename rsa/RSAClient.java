import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.Socket;

public class RSAClient {
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            
            BigInteger M = BigInteger.valueOf(9);
            BigInteger e = new BigInteger(in.readUTF());
            BigInteger N = new BigInteger(in.readUTF());

            BigInteger encrypted = M.modPow(e, N);
            System.out.println("\nPlaintext: " + M);
            System.out.println(M + "^" + e + " mod " + N + " = " + encrypted);
            System.out.println("Ciphertext: " + encrypted);
                
            out.writeUTF(encrypted.toString());                
            System.out.println("\nSent encrypted message to server...");            
            socket.close();
            System.out.println("Connection closed");            
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}