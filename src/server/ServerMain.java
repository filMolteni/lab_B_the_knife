package server;

import server.connection.ServerListener;
import server.utils.DBConnectionPool;

import java.util.Scanner;

/**
 * Classe principale del server TheKnife.
 *
 * Responsabilità:
 * - inizializzare il pool di connessioni al database
 * - avviare il listener TCP che accetta i client
 *
 * Il server utilizza:
 * - {@link DBConnectionPool} per la gestione delle connessioni MySQL
 * - {@link ServerListener} per accettare connessioni sulla porta specificata
 */
public class ServerMain {

    /**
     * Entry point del server.
     *
     * Logica:
     * 1. (Opzionale) leggere i parametri DB da input
     * 2. inizializzare il pool di connessioni
     * 3. avviare il listener sulla porta 5555
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== TheKnife Server ===");

        System.out.print("DB Host: ");
        String host = sc.nextLine();

        System.out.print("DB Name: ");
        String dbName = sc.nextLine();

        System.out.print("DB User: ");
        String user = sc.nextLine();

        System.out.print("DB Password: ");
        String password = sc.nextLine();

        // Configurazione predefinita (sviluppo locale)
        // String host = "localhost";
        // String dbName = "theknife";
        // String user = "root";
        // String password = "";

        try {
            // Tentativo di connessione
            DBConnectionPool.initialize(host, dbName, user, password);
            System.out.println("Connessione al database riuscita!");

        } catch (Exception e) {
            System.out.println("Errore di connessione al database:");
            System.out.println(e.getMessage());
            System.out.println("Il server verrà terminato.");
            return; // evita crash
        }

        int port = 5555;
        System.out.println("Avvio server sulla porta " + port + "...");

        ServerListener listener = new ServerListener(port);
        listener.start();
    }
}

