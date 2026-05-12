package client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ClientConnection {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private Gson gson = new Gson();

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 5555;

    public ClientConnection() throws IOException {
        connect();
    }

    private void connect() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Invia una richiesta al server e restituisce la risposta
     */
    public Response sendRequest(Request req) throws IOException {

        // Serializza la richiesta in JSON
        String json = gson.toJson(req);
        out.println(json);

        // Legge la risposta JSON
        String responseJson = in.readLine();

        // Deserializza la risposta
        return gson.fromJson(responseJson, Response.class);
    }

    /**
     * Chiude la connessione
     */
    public void close() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
