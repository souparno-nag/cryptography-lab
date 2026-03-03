public class RC4 {
    private int[] S;
    private int i;
    private int j;
    
    public RC4(byte[] key) {
        S = new int[256];
        i = 0;
        j = 0;
        
        // Initialize S array
        for (int i = 0; i < 256; i++) {
            S[i] = i;
        }
        
        // Key Scheduling Algorithm (KSA)
        j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + (key[i % key.length] & 0xFF)) % 256;
            swap(i, j);
        }
        
        // Reset i and j for PRGA
        i = 0;
        j = 0;
    }
    
    private void swap(int a, int b) {
        int temp = S[a];
        S[a] = S[b];
        S[b] = temp;
    }
    
    // Pseudo-Random Generation Algorithm (PRGA)
    public int nextByte() {
        i = (i + 1) % 256;
        j = (j + S[i]) % 256;
        swap(i, j);
        
        int t = (S[i] + S[j]) % 256;
        return S[t];
    }
    
    public static void main(String[] args) {
        // Secret key K = [1,2,3,4,5,6,7]
        byte[] key = {1, 2, 3, 4, 5, 6, 7};
        
        System.out.println("RC4 Keystream Generator");
        System.out.println("Key: [1, 2, 3, 4, 5, 6, 7]");
        System.out.println("\nFirst 20 elements of the keystream:");
        System.out.println("Index\tValue (decimal)\tValue (hex)");
        System.out.println("-------------------------------------");
        
        RC4 rc4 = new RC4(key);
        
        for (int index = 0; index < 20; index++) {
            int keystreamByte = rc4.nextByte();
            System.out.printf("%d\t%d\t\t0x%02X\n", 
                index + 1, keystreamByte, keystreamByte);
        }
        
        // Also generate a second set to show more keystream
        System.out.println("\nVerification - Next 10 bytes:");
        System.out.println("Index\tValue (decimal)\tValue (hex)");
        System.out.println("-------------------------------------");
        
        for (int index = 20; index < 30; index++) {
            int keystreamByte = rc4.nextByte();
            System.out.printf("%d\t%d\t\t0x%02X\n", 
                index + 1, keystreamByte, keystreamByte);
        }
    }
}