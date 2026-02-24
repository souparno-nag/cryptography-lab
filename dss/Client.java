import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // User inputs
        System.out.print("Enter p: ");
        BigInteger p = new BigInteger(sc.nextLine());

        System.out.print("Enter q: ");
        BigInteger q = new BigInteger(sc.nextLine());

        System.out.print("Enter g: ");
        BigInteger g = new BigInteger(sc.nextLine());

        System.out.print("Enter private key x (< q): ");
        BigInteger x = new BigInteger(sc.nextLine());

        System.out.print("Enter random k (< q): ");
        BigInteger k = new BigInteger(sc.nextLine());

        System.out.print("Enter message: ");
        String message = sc.nextLine();

        // Generate public key
        BigInteger y = Digital_Signature.generatePublicKey(g, x, p);

        // Sign
        BigInteger[] sig = Digital_Signature.sign(message, x, p, q, g, k);
        BigInteger r = sig[0];
        BigInteger s = sig[1];

        System.out.println("\nPublic key y: " + y);
        System.out.println("Signature (r,s): " + r + ", " + s);

        // Send to server
        Socket socket = new Socket("localhost", 5000);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        out.write(message + "\n");
        out.write(r.toString() + "\n");
        out.write(s.toString() + "\n");
        out.write(y.toString() + "\n");
        out.write(p.toString() + "\n");
        out.write(q.toString() + "\n");
        out.write(g.toString() + "\n");

        out.flush();
        socket.close();
        sc.close();
    }
}