import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MD5_Hash_Algorithm {
    // MD5 Constants
    // Intial hash values
    private static final int H0 = 0x67452310;
    private static final int H1 = 0xEFCDAB89;
    private static final int H2 = 0x98BADCFE;
    private static final int H3 = 0x10325476;
    // Shift amounts for each of the 64 steps
    private static final int[] SHIFT = {
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, // Round 1
        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, // Round 2
        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, // Round 3
        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21 // Round 4
    };
    // Round constants - T[i] = floor(2^32 * abs(sin(i + 1)))
    private static final int[] T = new int[64];
    static {
        // Initialize the T array with the precomputed sine values
        for (int i = 0; i < 64; i++) {
            T[i] = (int) (Math.pow(2, 32) * Math.abs(Math.sin(i + 1)));
        }
    }

    // Helper Methods
    // Convert 4 bytes (little-endian) to an integer
    private static int bytesToInt(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) |
                ((bytes[offset+1] & 0xFF) << 8) |
                ((bytes[offset+2] & 0xFF) << 16) |
                ((bytes[offset+3] & 0xFF) << 24);
    }
    // Convert final hash values to byte array (little-endian)
    private static byte[] hashToBytes(int h0, int h1, int h2, int h3) {
        byte[] result = new byte[16];
        // h0 (4 bytes)
        result[0] = (byte) (h0 & 0xFF);
        result[1] = (byte) ((h0 >>> 8) & 0xFF);
        result[2] = (byte) ((h0 >>> 16) & 0xFF);
        result[3] = (byte) ((h0 >>> 24) & 0xFF);
        // h1 (4 bytes)
        result[4] = (byte) (h1 & 0xFF);
        result[5] = (byte) ((h1 >>> 8) & 0xFF);
        result[6] = (byte) ((h1 >>> 16) & 0xFF);
        result[7] = (byte) ((h1 >>> 24) & 0xFF);
        // h2 (4 bytes)
        result[8] = (byte) (h2 & 0xFF);
        result[9] = (byte) ((h2 >>> 8) & 0xFF);
        result[10] = (byte) ((h2 >>> 16) & 0xFF);
        result[11] = (byte) ((h2 >>> 24) & 0xFF);
        // h0 (4 bytes)
        result[12] = (byte) (h3 & 0xFF);
        result[13] = (byte) ((h3 >>> 8) & 0xFF);
        result[14] = (byte) ((h3 >>> 16) & 0xFF);
        result[15] = (byte) ((h3 >>> 24) & 0xFF);
        return result;
    }
    // Convert byte array to hex string
    private static String bytesToHex(byte[] bytes) {
        String str = "";
        for (byte b : bytes) {
            str += String.format("%02x", b & 0xFF);
        }
        return str;
    }

    // Main method to compute the MD5 hash of a given input string
    // Pad message according to MD5 specification
    static private byte[] padMessage(byte[] message) {
        int originalLength = message.length;
        int bitLength = originalLength*8;
        // Calculate padding needed
        int paddingLength;
        if (originalLength % 64 < 56) {
            paddingLength = 56 - (originalLength % 64);
        } else {
            paddingLength = 120 - (originalLength % 64);
        }
        // Create padded array: original + padding + 8 bytes for length
        byte[] padded = new byte[originalLength + paddingLength + 8];
        // Copy original message
        System.arraycopy(message, 0, padded, 0, originalLength);
        // Add the 1 bit (0x80)
        padded[originalLength] = (byte) 0x80;
        // Add the original length in bits (as 64-bit little-endian)
        for (int i = 0; i < 8; i++) {
            padded[originalLength + paddingLength + i] = (byte) ((bitLength >>> (i*8)) & 0xFF);
        }
        return padded;
    }
    private static int[] processBlock(byte[] block, int h0, int h1, int h2, int h3) {
        // Break block into 16 32-bit words (little-endian)
        int[] M = new int[16];
        for (int i = 0; i < 16; i++) {
            M[i] = bytesToInt(block, i*4);
        }
        // Initialize working variables
        int a = h0, b = h1, c = h2, d = h3;
        // Main loop - 64 steps
        for (int i = 0; i < 64; i++) {
            int f, g;
            // Determine which round function to use
            if (i < 16) { // Round 1
                f = (b & c) | ((~b) & d);
                g = i;
            } else if (i < 32) { // Round 2
                f = (b & d) | (c & (~d));
                g = (5 * i + 1) % 16;
            } else if (i < 48) { // Round 3
                f = b ^ c ^ d;
                g = (3 * i + 5) % 16;
            } else { // Round 4
                f = c ^ (b | (~d));
                g = (7 * i) % 16;
            }
            // Main MD5 operation
            int temp = d;
            d = c;
            c = b;
            b += Integer.rotateLeft(a + f + M[g] + T[i], SHIFT[i]);
            a = temp;
        }
        // Add results to hash
        return new int[] {
            h0 + a,
            h1 + b,
            h2 + c,
            h3 + d
        };
    }
    // Compute MD5 hash of input bytes
    private static byte[] computeMD5(byte[] input) {
        // Pad the input to multiple of 512 bytes (64 bytes)
        byte[] padded = padMessage(input);
        // Initialize hash values
        int h0 = H0, h1 = H1, h2 = H2, h3 = H3;
        // Process each 64-byte block
        for (int blockStart = 0; blockStart < padded.length; blockStart += 64) {
            // Get current block
            byte[] block = new byte[64];
            System.arraycopy(padded, blockStart, block, 0, 64);
            // Process the block and update hash values
            int[] result = processBlock(block, h0, h1, h2, h3);
            h0 = result[0];
            h1 = result[1];
            h2 = result[2];
            h3 = result[3];
        }
        // Convert final hash values to bytes
        return hashToBytes(h0, h1, h2, h3);
    }
    public static String generateMAC(String key, String message) {
        // Combine key and message (simple concatenation)
        String combined = key + message;
        // Combined to bytes
        byte[] inputBytes = combined.getBytes(StandardCharsets.UTF_8);
        // Compute MD5 hash
        byte[] hash = computeMD5(inputBytes);
        // Convert to hex string and return
        return bytesToHex(hash);
    }
    public static boolean verifyMAC(String key, String message, String macToVerify) {
        String computedMAC = generateMAC(key, message);
        return computedMAC.equals(macToVerify);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = sc.nextLine();
        System.out.print("Enter the message: ");
        String msg = sc.nextLine();
        String mac = generateMAC(key, msg);
        System.out.println("MAC: " + mac);
        sc.close();
    }
}