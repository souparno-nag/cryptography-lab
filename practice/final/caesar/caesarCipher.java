public class caesarCipher {

	private static String encrpt(String plaintext, int key) {
		String ciphertext = "";
		for (char ch : plaintext.toCharArray()) {
			ciphertext += (char)((ch - 'A' + key)%26 + 'A');
		}
		return ciphertext;
	}

        private static String decrypt(String ciphertext, int key) {
                String plaintext = "";
                for (char ch : ciphertext.toCharArray()) {
                        plaintext += (char)(((ch - 'A' - key)%26 + 26)%26 + 'A');
                }
                return plaintext;
        }

	public static void main(String[] args) {
		String message = "AppleInTheOrchard";
		int key = 77;

		message = message.toUpperCase();

		String ciphertext = encrpt(message, key);
		System.out.println(ciphertext);

		String plaintext = decrypt(ciphertext, key);
		System.out.println(plaintext);
	}
}
