package server.connection;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread responsabile dell'ascolto delle nuove connessioni client.
 *
 * Il ServerListener:
 * - apre una ServerSocket sulla porta specificata
 * - rimane in ascolto in un ciclo infinito
 * - accetta ogni nuova connessione in arrivo
 * - crea un {@link ClientHandler} per gestire il client
 * - delega l'esecuzione del ClientHandler al {@link ServerThreadPool}
 *
 * Questo thread rimane attivo per tutta la durata del server.
 */
public class ServerListener extends Thread {

    private final int port;

    /**
     * Costruisce un listener che accetta connessioni sulla porta indicata.
     *
     * @param port porta su cui il server deve rimanere in ascolto
     */
    public ServerListener(int port) {
        this.port = port;
    }

    /**
     * Avvia il ciclo di ascolto:
     * - crea la ServerSocket
     * - accetta connessioni
     * - per ogni client crea un ClientHandler e lo invia al thread pool
     */
    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {

            System.out.println("Server in ascolto sulla porta " + port);

            while (true) {
                Socket client = server.accept();
                System.out.println("Nuovo client: " + client.getInetAddress());

                ClientHandler handler = new ClientHandler(client);
                ServerThreadPool.submit(handler);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
