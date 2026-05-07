package server.connection;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {

    private final int port;

    public ServerListener(int port) {
        this.port = port;
    }

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
