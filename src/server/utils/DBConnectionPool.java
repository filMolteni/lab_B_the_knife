package server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;

public class DBConnectionPool {

    private static ArrayBlockingQueue<Connection> pool;

    public static void initialize(String host, String dbName, String user, String password) {
        try {
            // Carica il driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            pool = new ArrayBlockingQueue<>(10);

            // URL per MySQL su XAMPP
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

    public static Connection get() throws InterruptedException {
        return pool.take();
    }

    public static void release(Connection conn) {
        if (conn != null) {
            pool.offer(conn);
        }
    }
}
