import java.lang.StringBuilder;
import java.nio.charset.StandardCharsets;

public class SHA_Hash_Algorithm {
    // Helper Functions
    // Left Rotate Integer
    private static int leftRotate (int x, int n) {
        return (x << n) | (x >>> (32 - n));
    }
    // Right Rotate Integer
    private static int rightRotate (int x, int n) {
        return (x >>> n) | (x << (32 - n));
    }
    // Convert bytes to hex string
    private static String bytesToHex(byte[] bytes) {
        String result = "";
        for (byte b : bytes) {
            result += String.format("%02x", b & 0xFF);
        }
        return result;
    }

    // SHA-128 Implementation
    private static byte[] sha128(byte[] input) {
        // Padding
        int originalLength = input.length;
        int bitLength = originalLength * 8;
        // Calculate padded length
        int paddedLength = originalLength + 1; // for the 0x80 byte
        while (paddedLength % 64 != 56) {
            paddedLength++;
        }
        paddedLength += 8; // for length
        byte[] padded = new byte[paddedLength];
        System.arraycopy(input, 0, padded, 0, originalLength);
        padded[originalLength] = (byte) 0x80;
        // Added length at the end (big-endian)
        for (int i = 0; i < 8; i++) {
            padded[paddedLength - 8 + i] = (byte) ((bitLength >>> (56 - i * 8)) & 0xFF);
        }
        // Initial hash values
        int h0 = 0x67452301;
        int h1 = 0xEFCDAB89;
        int h2 = 0x98BADCFE;
        int h3 = 0x10325476;
        int h4 = 0xC3D2E1F0;
        // Process each 64-byte chunk
        for (int offset = 0; offset < paddedLength; offset += 64) {
            int[] w = new int[80];
            // Prepare message schedule
            for (int i = 0; i < 16; i++) {
                w[i] = ((padded[offset + i*4] & 0xFF) << 24) |
                       ((padded[offset + i*4 + 1] & 0xFF) << 16) |
                       ((padded[offset + i*4 + 2] & 0xFF) << 8) |
                       (padded[offset + i*4 + 3] & 0xFF);
            }
            for (int i = 16; i < 80; i++) {
                w[i] = leftRotate(w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16], 1);
            }
            // Initialize working variables
            int a = h0;
            int b = h1;
            int c = h2;
            int d = h3;
            int e = h4;
            // Main loop
            for (int i = 0; i < 80; i++) {
                int f, k;
                
                if (i < 20) {
                    f = (b & c) | ((~b) & d);
                    k = 0x5A827999;
                } else if (i < 40) {
                    f = b ^ c ^ d;
                    k = 0x6ED9EBA1;
                } else if (i < 60) {
                    f = (b & c) | (b & d) | (c & d);
                    k = 0x8F1BBCDC;
                } else {
                    f = b ^ c ^ d;
                    k = 0xCA62C1D6;
                }
                
                int temp = leftRotate(a, 5) + f + e + w[i] + k;
                e = d;
                d = c;
                c = leftRotate(b, 30);
                b = a;
                a = temp;
            }
            // Add this chunk's hash to result
            h0 += a;
            h1 += b;
            h2 += c;
            h3 += d;
            h4 += e;
        }
        // Convert to byte array
        byte[] result = new byte[20];
        result[0] = (byte)((h0 >>> 24) & 0xFF);
        result[1] = (byte)((h0 >>> 16) & 0xFF);
        result[2] = (byte)((h0 >>> 8) & 0xFF);
        result[3] = (byte)(h0 & 0xFF);
        result[4] = (byte)((h1 >>> 24) & 0xFF);
        result[5] = (byte)((h1 >>> 16) & 0xFF);
        result[6] = (byte)((h1 >>> 8) & 0xFF);
        result[7] = (byte)(h1 & 0xFF);
        result[8] = (byte)((h2 >>> 24) & 0xFF);
        result[9] = (byte)((h2 >>> 16) & 0xFF);
        result[10] = (byte)((h2 >>> 8) & 0xFF);
        result[11] = (byte)(h2 & 0xFF);
        result[12] = (byte)((h3 >>> 24) & 0xFF);
        result[13] = (byte)((h3 >>> 16) & 0xFF);
        result[14] = (byte)((h3 >>> 8) & 0xFF);
        result[15] = (byte)(h3 & 0xFF);
        result[16] = (byte)((h4 >>> 24) & 0xFF);
        result[17] = (byte)((h4 >>> 16) & 0xFF);
        result[18] = (byte)((h4 >>> 8) & 0xFF);
        result[19] = (byte)(h4 & 0xFF);
        
        return result;
    }
    // SHA-256 Implementation
    private static byte[] sha256(byte[] input) {
        // Initial hash values (first 32 bits of fractional parts of square roots of first 8 primes)
        int[] h = {
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
        };
        // Round constants (first 32 bits of fractional parts of cube roots of first 64 primes)
        int[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
            0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
            0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
            0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
            0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
            0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
            0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
            0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
            0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
        };
        // Padding
        int originalLength = input.length;
        long bitLength = originalLength * 8L;
        // Calculate padded length
        int paddedLength = originalLength + 1;
        while (paddedLength % 64 != 56) {
            paddedLength++;
        }
        paddedLength += 8;
        byte[] padded = new byte[paddedLength];
        System.arraycopy(input, 0, padded, 0, originalLength);
        padded[originalLength] = (byte)0x80;
        // Add length at the end
        for (int i = 0; i < 8; i++) {
            padded[paddedLength - 8 + i] = (byte)((bitLength >>> (56 - i * 8)) & 0xFF);
        }
        // Process each chunk
        for (int chunk = 0; chunk < paddedLength; chunk += 64) {
            int[] w = new int[64];            
            // Prepare message schedule
            for (int i = 0; i < 16; i++) {
                w[i] = ((padded[chunk + i*4] & 0xFF) << 24) |
                       ((padded[chunk + i*4 + 1] & 0xFF) << 16) |
                       ((padded[chunk + i*4 + 2] & 0xFF) << 8) |
                       (padded[chunk + i*4 + 3] & 0xFF);
            }            
            for (int i = 16; i < 64; i++) {
                int s0 = rightRotate(w[i-15], 7) ^ rightRotate(w[i-15], 18) ^ (w[i-15] >>> 3);
                int s1 = rightRotate(w[i-2], 17) ^ rightRotate(w[i-2], 19) ^ (w[i-2] >>> 10);
                w[i] = w[i-16] + s0 + w[i-7] + s1;
            }            
            // Initialize working variables
            int a = h[0];
            int b = h[1];
            int c = h[2];
            int d = h[3];
            int e = h[4];
            int f = h[5];
            int g = h[6];
            int h0 = h[7];
            // Main loop
            for (int i = 0; i < 64; i++) {
                int S1 = rightRotate(e, 6) ^ rightRotate(e, 11) ^ rightRotate(e, 25);
                int ch = (e & f) ^ ((~e) & g);
                int temp1 = h0 + S1 + ch + k[i] + w[i];
                int S0 = rightRotate(a, 2) ^ rightRotate(a, 13) ^ rightRotate(a, 22);
                int maj = (a & b) ^ (a & c) ^ (b & c);
                int temp2 = S0 + maj;                
                h0 = g;
                g = f;
                f = e;
                e = d + temp1;
                d = c;
                c = b;
                b = a;
                a = temp1 + temp2;
            }            
            // Add this chunk's hash to result
            h[0] += a;
            h[1] += b;
            h[2] += c;
            h[3] += d;
            h[4] += e;
            h[5] += f;
            h[6] += g;
            h[7] += h0;
        }        
        // Convert to byte array
        byte[] result = new byte[32];
        for (int i = 0; i < 8; i++) {
            result[i*4] = (byte)((h[i] >>> 24) & 0xFF);
            result[i*4 + 1] = (byte)((h[i] >>> 16) & 0xFF);
            result[i*4 + 2] = (byte)((h[i] >>> 8) & 0xFF);
            result[i*4 + 3] = (byte)(h[i] & 0xFF);
        }        
        return result;
    }

    // Main functions
    // Create a message of specified size
    private static String createMessageOfSize(int size) {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append('A');
        }
        return sb.toString();
    }
    // HMAC-SHA1 Implementation
    public static String hmacSHA1(String message, String key) {
        // Block size for SHA-1 is 64 bytes
        int blockSize = 64;
        
        // Convert to bytes
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        
        // If key is longer than block size, hash it
        if (keyBytes.length > blockSize) {
            keyBytes = sha128(keyBytes);
        }
        
        // Pad key to block size
        byte[] paddedKey = new byte[blockSize];
        System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
        for (int i = keyBytes.length; i < blockSize; i++) {
            paddedKey[i] = 0;
        }
        
        // Create ipad and opad
        byte[] ipad = new byte[blockSize];
        byte[] opad = new byte[blockSize];
        
        for (int i = 0; i < blockSize; i++) {
            ipad[i] = (byte)(paddedKey[i] ^ 0x36); // ipad = 0x36
            opad[i] = (byte)(paddedKey[i] ^ 0x5C); // opad = 0x5C
        }
        
        // Inner hash: H((K ⊕ ipad) || message)
        byte[] innerInput = new byte[blockSize + messageBytes.length];
        System.arraycopy(ipad, 0, innerInput, 0, blockSize);
        System.arraycopy(messageBytes, 0, innerInput, blockSize, messageBytes.length);
        byte[] innerHash = sha128(innerInput);
        
        // Outer hash: H((K ⊕ opad) || innerHash)
        byte[] outerInput = new byte[blockSize + innerHash.length];
        System.arraycopy(opad, 0, outerInput, 0, blockSize);
        System.arraycopy(innerHash, 0, outerInput, blockSize, innerHash.length);
        byte[] outerHash = sha128(outerInput);
        
        // Convert to hex string
        return bytesToHex(outerHash);
    }
    // HMAC-SHA256 Implementation
    public static String hmacSHA256(String message, String key) {
        // Block size for SHA-256 is 64 bytes
        int blockSize = 64;
        
        // Convert to bytes
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        
        // If key is longer than block size, hash it
        if (keyBytes.length > blockSize) {
            keyBytes = sha256(keyBytes);
        }
        
        // Pad key to block size
        byte[] paddedKey = new byte[blockSize];
        System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
        for (int i = keyBytes.length; i < blockSize; i++) {
            paddedKey[i] = 0;
        }
        
        // Create ipad and opad
        byte[] ipad = new byte[blockSize];
        byte[] opad = new byte[blockSize];
        
        for (int i = 0; i < blockSize; i++) {
            ipad[i] = (byte)(paddedKey[i] ^ 0x36);
            opad[i] = (byte)(paddedKey[i] ^ 0x5C);
        }
        
        // Inner hash
        byte[] innerInput = new byte[blockSize + messageBytes.length];
        System.arraycopy(ipad, 0, innerInput, 0, blockSize);
        System.arraycopy(messageBytes, 0, innerInput, blockSize, messageBytes.length);
        byte[] innerHash = sha256(innerInput);
        
        // Outer hash
        byte[] outerInput = new byte[blockSize + innerHash.length];
        System.arraycopy(opad, 0, outerInput, 0, blockSize);
        System.arraycopy(innerHash, 0, outerInput, blockSize, innerHash.length);
        byte[] outerHash = sha256(outerInput);
        
        return bytesToHex(outerHash);
    }
    // Verify HMAC-SHA256
    public static boolean verifyHMACSHA256(String message, String key, String hmacToVerify) {
        String computedHmac = hmacSHA256(message, key);
        return computedHmac.equals(hmacToVerify);
    }
    // Demonstrate HMAC functonality
    private static void demonstrateHMAC() {
        System.out.println("\n--- HMAC DEMONSTRATION ---\n");
        
        String message = "Hello World!";
        String key = "mysecretkey";
        
        System.out.println("Message: \"" + message + "\"");
        System.out.println("Key: \"" + key + "\"\n");
        
        // Generate HMACs
        String hmac1 = hmacSHA1(message, key);
        String hmac256 = hmacSHA256(message, key);
        
        System.out.println("HMAC-SHA1:   " + hmac1);
        System.out.println("HMAC-SHA256: " + hmac256);
        System.out.println("SHA-1 length: " + hmac1.length() * 4 + " bits"); // hex chars * 4
        System.out.println("SHA-256 length: " + hmac256.length() * 4 + " bits");
        
        // Verification example
        System.out.println("\n--- VERIFICATION ---");
        String receivedHmac = hmacSHA256(message, key);
        boolean isValid = verifyHMACSHA256(message, key, receivedHmac);
        System.out.println("Original message verification: " + (isValid ? "✓ PASSED" : "✗ FAILED"));
        
        // Tampered message
        String tamperedMessage = "Hello World!";
        boolean isTamperedValid = verifyHMACSHA256(tamperedMessage + "x", key, receivedHmac);
        System.out.println("Tampered message verification: " + (isTamperedValid ? "✓ PASSED" : "✗ FAILED"));
    }
    // Test HMAC with varying message sizes and measure time
    private static void testMessageSizes() {
        System.out.println("--- TIME MEASUREMENT FOR DIFFERENT MESSAGE SIZES ---\n");
        
        // Different message sizes (in bytes)
        int[] sizes = {10, 100, 1000, 10000, 100000};
        String key = "secretkey123";
        
        System.out.println("Message Size | SHA-1 Time (ms) | SHA-256 Time (ms) | SHA-1 Result (first 8 chars)");
        System.out.println("-------------|-----------------|-------------------|----------------------------");
        
        for (int size : sizes) {
            // Create message of specific size
            String message = createMessageOfSize(size);
            
            // Measure SHA-1 HMAC
            long start1 = System.nanoTime();
            String hmac1 = hmacSHA1(message, key);
            long time1 = (System.nanoTime() - start1) / 1_000_000; // Convert to ms
            
            // Measure SHA-256 HMAC
            long start2 = System.nanoTime();
            String hmac2 = hmacSHA256(message, key);
            long time2 = (System.nanoTime() - start2) / 1_000_000; // Convert to ms
            
            // Display results
            System.out.printf("%6d bytes | %15d | %17d | %s\n", 
                size, time1, time2, hmac1.substring(0, 8));
        }
    }
    public static void main(String[] args) {
        System.out.println("=== SIMPLE HMAC IMPLEMENTATION (LAB) ===\n");
        
        // Test with different message sizes
        testMessageSizes();
        
        // Demonstrate HMAC functionality
        demonstrateHMAC();
    }
}
