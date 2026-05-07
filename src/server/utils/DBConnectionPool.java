package server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ArrayBlockingQueue;

public class DBConnectionPool {

    private static ArrayBlockingQueue<Connection> pool;

    public static void initialize(String host, String dbName, String user, String password) {
        try {
            pool = new ArrayBlockingQueue<>(10);

            String url = "jdbc:postgresql://" + host + "/" + dbName;

            for (int i = 0; i < 10; i++) {
                Connection conn = DriverManager.getConnection(url, user, password);
                pool.add(conn);
            }

            System.out.println("Pool di connessioni inizializzato.");

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
