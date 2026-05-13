package client.net;

import java.io.*;
import java.net.Socket;
import com.google.gson.Gson;
import client.net.Request;
import client.net.Response;


public class ClientConnection {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientConnection(String host, int port) {
        try {
            socket = new Socket(host, port);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            out = new PrintWriter(
                    socket.getOutputStream(), true
            );

            System.out.println("Connessione stabilita con il server");

        } catch (IOException e) {
            System.err.println("Errore di connessione al server: " + e.getMessage());
            throw new RuntimeException("Connessione fallita");
        }
    }

    public Response sendRequest(Request req) {
        try {
            out.println(req.toJson());   // <-- ora NON è più null
            String json = in.readLine();
            Gson gson = new Gson();
            return gson.fromJson(json, Response.class);

        } catch (Exception e) {
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            Gson gson = new Gson();
            return gson.fromJson("{\"success\":false,\"message\":\"Errore di comunicazione\"}", Response.class);
        }
    }
}
