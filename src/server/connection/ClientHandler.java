package server.connection;

import com.google.gson.Gson;
import common.Request;
import common.Response;
import common.MessageType;

import server.service.UtenteService;
import server.service.RistoranteService;
import server.service.RecensioneService;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final Gson gson = new Gson();

    // 🔥 QUESTO È IL COSTRUTTORE CHE TI MANCA
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {

            String line;
            while ((line = in.readLine()) != null) {

                Request req = gson.fromJson(line, Request.class);
                Response resp = process(req);

                out.println(gson.toJson(resp));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response process(Request req) {

        if (req == null || req.type == null)
            return Response.error("Richiesta non valida");

        return switch (req.type) {

            case LOGIN -> UtenteService.login(req);
            case REGISTRAZIONE -> UtenteService.registra(req);

            case CERCA_RISTORANTI -> RistoranteService.cerca(req);
            case VISUALIZZA_RISTORANTE -> RistoranteService.visualizza(req);

            case AGGIUNGI_RECENSIONE -> RecensioneService.aggiungi(req);
            case MODIFICA_RECENSIONE -> RecensioneService.modifica(req);
            case ELIMINA_RECENSIONE -> RecensioneService.elimina(req);

            default -> Response.error("Tipo messaggio non gestito: " + req.type);
        };
    }
}
