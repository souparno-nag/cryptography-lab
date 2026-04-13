import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MD5_Hash_Algorithm {
    private static final int H0 = 0x67452310;
    private static final int H1 = 0xEFCDAB89;
    private static final int H2 = 0x98BADCFE;
    private static final int H3 = 0x10325476;
    private static final int[] SHIFT = { 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 5, 9, 14, 20, 5, 9,
            14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 6, 10, 15,
            21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21 };
    private static final int[] T = new int[64];
    static {
        for (int i = 0; i < 64; i++)
            T[i] = (int) (Math.pow(2, 32) * Math.abs(Math.sin(i + 1)));
    }

    private static int bytesToInt(byte[] b, int o) {
        return (b[o] & 0xFF) | ((b[o+1] & 0xFF) << 8) | ((b[o+2] & 0xFF) << 16) | ((b[o+3] & 0xFF) << 24);
    }

    private static byte[] hashToBytes (int h0, int h1, int h2, int h3) {
        byte[] r = new byte[16];
        r[0] = (byte)(h0 & 0xFF);
        r[1] = (byte)((h0 >>> 8) & 0xFF);
        r[2] = (byte)((h0 >>> 16) & 0xFF);
        r[3] = (byte)((h0 >>> 24) & 0xFF);
        r[4] = (byte)(h1 & 0xFF);
        r[5] = (byte)((h1 >>> 8) & 0xFF);
        r[6] = (byte)((h1 >>> 16) & 0xFF);
        r[7] = (byte)((h1 >>> 24) & 0xFF);
        r[8] = (byte)(h2 & 0xFF);
        r[9] = (byte)((h2 >>> 8) & 0xFF);
        r[10] = (byte)((h2 >>> 16) & 0xFF);
        r[11] = (byte)((h2 >>> 24) & 0xFF);
        r[12] = (byte)(h3 & 0xFF);
        r[13] = (byte)((h3 >>> 8) & 0xFF);
        r[14] = (byte)((h3 >>> 16) & 0xFF);
        r[15] = (byte)((h3 >>> 24) & 0xFF);
        return r;
    }

    private static String bytesToHex(byte[] b) {
        String s = "";
        for (byte x : b) {
            s += String.format("%02x", x & 0xFF);
        }
        return s;
    }

    private static byte[] padMessage(byte[] msg) {
        int len = msg.length;
        int bitLen = len * 8;
        int padLen = (len%64 < 56) ? 56 - len%64 : 120 - len%64;
        byte[] p = new byte[len+padLen+8];
        System.arraycopy(msg, 0, p, 0, len);
        p[len] = (byte)(0x80);
        for (int i = 0; i < 8; i++) {
            p[len + padLen + i] = (byte)((bitLen >>> (i*8)) & 0xFF);
        }
        return p;
    }

    private static int[] processBlocks(byte[] block, int h0, int h1, int h2, int h3) {
        int[] M = new int[16];
        for (int i = 0; i < 16; i++) M[i] = bytesToInt(block, i);
        int a = h0, b = h1, c = h2, d = h3;
        for (int i = 0; i < 64; i++) {
            int f, g;
            if (i < 16) {
                f = (b & c) | (~b & d);
                g = i;
            } else if (i < 32) {
                f = (b & d) | (c & ~d);
                g = (5*i+1)%16;
            } else if (i < 48) {
                f = b^c^d;
                g = (3*i+5)%16;
            } else {
                f = c^(b & ~d);
                g = (7*i)%16;
            }
            int temp = d;
            c = d;
            b += Integer.rotateLeft(a + f + T[i] + M[g], SHIFT[i]);
            a = temp;
        }
        return new int[]{h0+a, h1+b, h2+c, h3+d};
    }

    private static byte[] computeMD5 (byte[] input) {
        byte[] padded = padMessage(input);
        int h0 = H0, h1 = H1, h2 = H2, h3 = H3;
        for (int start = 0; start < padded.length; start += 64) {
            byte[] block = new byte[64];
            System.arraycopy(padded, start, block, 0, 64);
            int[] r = processBlocks(block, h0, h1, h2, h3);
            h0 = r[0]; h1 = r[1]; h2 = r[2]; h3 = r[3];
        }
        return hashToBytes(h0, h1, h2, h3);
    }

    private static String generateMAC(String message, String key) {
        return bytesToHex(computeMD5((message + key).getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = sc.nextLine();
        System.out.print("Enter the message: ");
        String msg = sc.nextLine();
        System.out.println("The MAC generated is " + generateMAC(msg, key));
        sc.close();
    }
}