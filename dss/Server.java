import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server waiting...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Receive all values
        String message = in.readLine();
        BigInteger r = new BigInteger(in.readLine());
        BigInteger s = new BigInteger(in.readLine());
        BigInteger y = new BigInteger(in.readLine());
        BigInteger p = new BigInteger(in.readLine());
        BigInteger q = new BigInteger(in.readLine());
        BigInteger g = new BigInteger(in.readLine());

        System.out.println("Message: " + message);
        System.out.println("Signature: (" + r + ", " + s + ")");
        System.out.println("Public key y: " + y);

        // Verify
        boolean valid = Digital_Signature.verify(message, r, s, y, p, q, g);

        if (valid) {
            System.out.println("✅ Signature VERIFIED");
        } else {
            System.out.println("❌ Signature INVALID");
        }

        socket.close();
        serverSocket.close();
    }
}