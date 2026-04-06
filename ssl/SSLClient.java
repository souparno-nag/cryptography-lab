import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Scanner;

public class SSLClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8443;
    private static final String TRUSTSTORE_PATH = "clienttruststore.jks";
    private static final String TRUSTSTORE_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // ============================================================
            // 1. Load the client's truststore
            //    Contains the server's certificate (or CA certificate)
            //    Used during Handshake Phase 2 to verify server identity
            //    (Peer Certificate verification using X.509v3)
            // ============================================================
            KeyStore trustStore = KeyStore.getInstance("JKS");
            FileInputStream trustStoreFile = new FileInputStream(TRUSTSTORE_PATH);
            trustStore.load(trustStoreFile, TRUSTSTORE_PASSWORD.toCharArray());
            trustStoreFile.close();

            // ============================================================
            // 2. Initialize TrustManagerFactory
            //    The TrustManager validates the server's certificate chain
            //    during the handshake (authentication countermeasure)
            // ============================================================
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            // ============================================================
            // 3. Initialize SSLContext with the TrustManager
            // ============================================================
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            // ============================================================
            // 4. Create SSL Socket and connect to server
            //    The SSL Handshake executes automatically on connect:
            //      - client_hello: sends version, random, cipher suites
            //      - server_hello: server responds with chosen cipher suite
            //      - Server certificate sent and verified by TrustManager
            //      - Key exchange (pre-master secret encrypted with
            //        server's RSA public key)
            //      - change_cipher_spec + finished from both sides
            // ============================================================
            SSLSocketFactory sf = sslContext.getSocketFactory();
            SSLSocket socket = (SSLSocket) sf.createSocket(SERVER_HOST, SERVER_PORT);

            // Force the handshake to complete
            socket.startHandshake();

            // ============================================================
            // 5. Display SSL session information
            // ============================================================
            SSLSession session = socket.getSession();
            System.out.println("===========================================");
            System.out.println("  Connected to SSL Server");
            System.out.println("===========================================");
            System.out.println("Protocol:     " + session.getProtocol());
            System.out.println("Cipher Suite: " + session.getCipherSuite());
            System.out.println("Session ID:   " + bytesToHex(session.getId()));

            // Display server certificate information
            java.security.cert.Certificate[] serverCerts = session.getPeerCertificates();
            if (serverCerts.length > 0 && serverCerts[0] instanceof X509Certificate) {
                X509Certificate cert = (X509Certificate) serverCerts[0];
                System.out.println("Server DN:    " + cert.getSubjectX500Principal());
                System.out.println("Issuer DN:    " + cert.getIssuerX500Principal());
                System.out.println("Valid From:   " + cert.getNotBefore());
                System.out.println("Valid Until:  " + cert.getNotAfter());
                System.out.println("Serial No:    " + cert.getSerialNumber());
            }
            System.out.println("===========================================\n");

            // ============================================================
            // 6. Communicate over the encrypted SSL connection
            //    All data is processed by the SSL Record Protocol:
            //      - Fragmentation (blocks ≤ 16384 bytes)
            //      - Compression (optional, default null)
            //      - MAC computation for integrity
            //      - Encryption for confidentiality
            // ============================================================
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Read welcome messages from server
            String serverMsg;
            for (int i = 0; i < 3; i++) {
                serverMsg = in.readLine();
                if (serverMsg != null) {
                    System.out.println("[Server]: " + serverMsg);
                }
            }

            // Interactive message loop
            System.out.println("\nType messages to send (type 'quit' to exit):");
            while (true) {
                System.out.print("[You]: ");
                String userInput = scanner.nextLine();

                // Send message to server (encrypted by SSL Record Protocol)
                out.println(userInput);

                // Read server response (decrypted by SSL Record Protocol)
                serverMsg = in.readLine();
                if (serverMsg != null) {
                    System.out.println("[Server]: " + serverMsg);
                }

                if ("quit".equalsIgnoreCase(userInput.trim())) {
                    break;
                }
            }

            // ============================================================
            // 7. Close the connection
            //    SSL Alert Protocol sends close_notify alert
            // ============================================================
            scanner.close();
            socket.close();
            System.out.println("\nSSL connection closed.");

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}