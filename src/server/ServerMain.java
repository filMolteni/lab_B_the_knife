package server;

import server.connection.ServerListener;
import server.utils.DBConnectionPool;

import java.util.Scanner;

public class ServerMain {

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

        DBConnectionPool.initialize(host, dbName, user, password);

        int port = 5555;
        System.out.println("Avvio server sulla porta " + port + "...");

        ServerListener listener = new ServerListener(port);
        listener.start();
    }
}
