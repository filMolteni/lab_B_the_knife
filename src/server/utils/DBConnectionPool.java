package server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Semplice pool di connessioni MySQL basato su {@link ArrayBlockingQueue}.
 *
 * Funzionalità principali:
 * - inizializzazione del pool con N connessioni persistenti
 * - recupero di una connessione tramite {@link #get()}
 * - rilascio della connessione tramite {@link #release(Connection)}
 *
 * Questo pool evita la creazione continua di connessioni,
 * migliorando le prestazioni del server.
 *
 * ⚠ Nota:
 * - Il pool contiene un numero fisso di connessioni (10)
 * - Le connessioni NON vengono ricreate automaticamente in caso di errore
 * - È pensato per un ambiente didattico / prototipale
 */
public class DBConnectionPool {

    /** Coda bloccante che contiene le connessioni disponibili. */
    private static ArrayBlockingQueue<Connection> pool;

    /**
     * Inizializza il pool creando 10 connessioni MySQL.
     *
     * @param host indirizzo del server MySQL (es. "localhost:3306")
     * @param dbName nome del database
     * @param user username MySQL
     * @param password password MySQL
     *
     * @throws RuntimeException se il driver non è trovato o la connessione fallisce
     */
    public static void initialize(String host, String dbName, String user, String password) {
        try {
            // Carica il driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            pool = new ArrayBlockingQueue<>(10);

            // URL per MySQL
            String url = "jdbc:mysql://" + host + "/" + dbName + "?useSSL=false&serverTimezone=UTC";

            // Crea 10 connessioni e mettile nel pool
            for (int i = 0; i < 10; i++) {
                Connection conn = DriverManager.getConnection(url, user, password);
                pool.add(conn);
            }

            System.out.println("Pool di connessioni MySQL inizializzato correttamente.");

        } catch (Exception e) {
            throw new RuntimeException("Errore inizializzazione pool DB", e);
        }
    }

    /**
     * Restituisce una connessione dal pool.
     * Se nessuna connessione è disponibile, il thread attende.
     *
     * @return una connessione MySQL pronta all'uso
     * @throws InterruptedException se il thread viene interrotto mentre attende
     */
    public static Connection get() throws InterruptedException {
        return pool.take();
    }

    /**
     * Rilascia una connessione e la rimette nel pool.
     *
     * @param conn connessione da restituire al pool
     */
    public static void release(Connection conn) {
        if (conn != null) {
            pool.offer(conn);
        }
    }
}
