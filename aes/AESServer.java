import java.io.*;
import java.net.*;
import java.util.Arrays;

public class AESServer {
    public static byte[] pad(byte[] in) {
        int pad = 16 - (in.length % 16);
        byte[] out = Arrays.copyOf(in, in.length + pad);
        for(int i=in.length;i<out.length;i++) out[i]=(byte)pad;
        return out;
    }
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started on port 5000");
            System.out.println("Waiting for client connection...");
            
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            DataInputStream in = new DataInputStream(socket.getInputStream());

            while (true) {
                int mode = in.readInt();
                if (mode == 0) {
                    socket.close();
                    serverSocket.close();
                    System.out.println("Connection closed");
                    return;
                }
                int clen = in.readInt();
                byte[] cipher = new byte[clen];
                in.readFully(cipher);
                
                
                int klen = in.readInt();
                byte[] key = new byte[klen];
                in.readFully(key);

                AESEncryption aes = new AESEncryption(key, mode);

                byte[] plain = new byte[cipher.length];
                for(int i=0;i<cipher.length;i+=16)
                    System.arraycopy(aes.decryptBlock(Arrays.copyOfRange(cipher,i,i+16)),0,plain,i,16);
                int p = plain[plain.length-1] & 0xFF;
                plain = Arrays.copyOf(plain, plain.length-p);
                System.out.println("Decrypted: "+new String(plain,"UTF-8"));
            }
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
