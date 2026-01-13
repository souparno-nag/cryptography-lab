import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class AESClient {
    public static byte[] pad(byte[] in) {
        int pad = 16 - (in.length % 16);
        byte[] out = Arrays.copyOf(in, in.length + pad);
        for(int i=in.length;i<out.length;i++) out[i]=(byte)pad;
        return out;
    }
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server");

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);

            System.out.print("Select AES mode (1=128, 2=192, 3=256): ");
            int mode = Integer.parseInt(sc.nextLine());

            System.out.print("Enter key: ");
            byte[] key = sc.nextLine().getBytes("UTF-8");
            if(mode==1) key = Arrays.copyOf(key,16);
            else if(mode==2) key = Arrays.copyOf(key,24);
            else if(mode==3) key = Arrays.copyOf(key,32);
            else {
                sc.close();
                socket.close();
                System.out.println("Connection closed");
                throw new Error("Invalid mode");
            };
            AESEncryption aes = new AESEncryption(key, mode);

            while (true) {
                System.out.print("Enter message: ");
                byte[] msg = sc.nextLine().getBytes("UTF-8");
                
                if (new String(msg,"UTF-8").equalsIgnoreCase("exit")) {
                    out.writeInt(0);
                    out.flush();
                    break;
                }   

                byte[] padded = pad(msg);
                byte[] cipher = new byte[padded.length];

                for(int i=0;i<padded.length;i+=16)
                    System.arraycopy(aes.encryptBlock(Arrays.copyOfRange(padded,i,i+16)),0,cipher,i,16);
                System.out.println("Encrypted: "+Arrays.toString(cipher));

                out.writeInt(mode);
                out.writeInt(cipher.length);
                out.write(cipher);

                out.writeInt(key.length);
                out.write(key);
                out.flush();
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
