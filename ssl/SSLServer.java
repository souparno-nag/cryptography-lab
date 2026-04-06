import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

public class SSLServer {

    private static final int PORT = 8443;
    private static final String KEYSTORE_PATH = "serverkeystore.jks";
    private static final String KEYSTORE_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // ============================================================
            // 1. Load the server's keystore (contains private key + cert)
            //    This is used during the SSL Handshake Protocol (Phase 2)
            //    where the server sends its certificate to the client
            // ============================================================
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreFile = new FileInputStream(KEYSTORE_PATH);
            keyStore.load(keyStoreFile, KEYSTORE_PASSWORD.toCharArray());
            keyStoreFile.close();

            // ============================================================
            // 2. Initialize KeyManagerFactory with the keystore
            //    The KeyManager decides which authentication credentials
            //    (certificate + private key) to send to the client
            // ============================================================
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, KEYSTORE_PASSWORD.toCharArray());

            // ============================================================
            // 3. Initialize SSLContext — this sets up the SSL/TLS engine
            //    Supports cipher suites like RSA key exchange, AES
            //    encryption, SHA MAC (as described in CipherSpec)
            // ============================================================
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            // ============================================================
            // 4. Create SSL Server Socket Factory and bind to port
            // ============================================================
            SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(PORT);

            System.out.println("===========================================");
            System.out.println("  SSL Server started on port " + PORT);
            System.out.println("  Waiting for client connections...");
            System.out.println("===========================================");

            // ============================================================
            // 5. Accept client connection
            //    The SSL Handshake Protocol executes automatically:
            //    Phase 1: client_hello / server_hello (version, cipher suite, random)
            //    Phase 2: Server sends certificate, server_hello_done
            //    Phase 3: Client key exchange (pre-master secret)
            //    Phase 4: change_cipher_spec, finished
            // ============================================================
            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();

                // Print connection details
                SSLSession session = clientSocket.getSession();
                System.out.println("\n--- New Client Connected ---");
                System.out.println("Client: " + clientSocket.getInetAddress());
                System.out.println("Protocol: " + session.getProtocol());
                System.out.println("Cipher Suite: " + session.getCipherSuite());
                System.out.println("Session ID: " + bytesToHex(session.getId()));

                // Handle the client in a new thread
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles communication with a connected SSL client.
     * Data sent through the SSL socket is automatically processed by the
     * SSL Record Protocol: fragmented, compressed, MAC added, and encrypted.
     */
    static class ClientHandler implements Runnable {
        private SSLSocket clientSocket;

        public ClientHandler(SSLSocket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true)
            ) {
                // Send welcome message to client
                out.println("Welcome to the Secure SSL Server!");
                out.println("Your connection is encrypted using: "
                    + clientSocket.getSession().getCipherSuite());
                out.println("Type 'quit' to disconnect.");

                // Echo loop — read from client and respond
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from client: " + inputLine);

                    if ("quit".equalsIgnoreCase(inputLine.trim())) {
                        out.println("Goodbye! Connection closing...");
                        System.out.println("Client disconnected gracefully.");
                        break;
                    }

                    // Echo the message back in uppercase
                    String response = "Server Echo: " + inputLine.toUpperCase();
                    out.println(response);
                    System.out.println("Sent to client: " + response);
                }

                clientSocket.close();

            } catch (IOException e) {
                System.err.println("Client handler error: " + e.getMessage());
            }
        }
    }

    /**
     * Utility method to convert byte array to hex string (for Session ID display)
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}