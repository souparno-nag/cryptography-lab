public class autokeyCipher {

	private static String generateKey(String keyWord, String plaintext) {
		String key = keyWord;
		for (int i = 0; i < plaintext.length() - keyWord.length(); i++) {
			key += plaintext.charAt(i);
		}
		return key;
	}

	private static String encrypt(String plaintext, String key) {
		String ciphertext = "";
		for (int i = 0; i < plaintext.length(); i++) {
			ciphertext += (char)((plaintext.charAt(i) + key.charAt(i) - 'A')%26 + 'A');
		}
		return ciphertext;
	}

	private static String decrypt(String ciphertext, String key) {
		String plaintext = "";
                for (int i = 0; i < ciphertext.length(); i++) {
                        plaintext += (char)(((ciphertext.charAt(i) - key.charAt(i) - 'A')%26 + 26)%26 + 'A');
                }
                return plaintext;
        }

	public static void main(String[] args) {
		String message = "HelloCanYouHearMe";
		String keyWord = "punkrocker";

		message = message.toUpperCase();
		keyWord = keyWord.toUpperCase();

		String key = generateKey(keyWord, message);

		String ciphertext = encrypt(message, key);
		String plaintext = decrypt(ciphertext, key);

		System.out.println(ciphertext);
		System.out.println(plaintext);
	}
}
