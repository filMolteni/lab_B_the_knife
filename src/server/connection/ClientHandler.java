package server.connection;

import com.google.gson.Gson;
import common.Request;
import common.Response;

import server.service.UtenteService;
import server.service.RistoranteService;
import server.service.RecensioneService;
import server.dao.PreferitiDAO;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final Gson gson = new Gson();

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

            // ============================
            // UTENTE
            // ============================
            case LOGIN -> UtenteService.login(req);
            case REGISTRAZIONE -> UtenteService.registrati(req);

            // ============================
            // RISTORANTI
            // ============================
            case CERCA_RISTORANTI -> RistoranteService.cerca(req);
            case VISUALIZZA_RISTORANTE -> RistoranteService.visualizza(req);
            case VISUALIZZA_RISTORANTE_UTENTE -> RistoranteService.visualizzaUtente(req);
            case AGGIUNGI_RISTORANTE -> RistoranteService.aggiungi(req);
            case MODIFICA_RISTORANTE -> RistoranteService.modifica(req);
            case ELIMINA_RISTORANTE -> RistoranteService.elimina(req);
            case VISUALIZZA_RIEPILOGO_GESTORE -> RistoranteService.riepilogoGestore(req);

            // ============================
            // RECENSIONI
            // ============================
            case AGGIUNGI_RECENSIONE -> RecensioneService.aggiungi(req);
            case MODIFICA_RECENSIONE -> RecensioneService.modifica(req);
            case ELIMINA_RECENSIONE -> RecensioneService.elimina(req);

            case VISUALIZZA_RECENSIONI_RISTORANTE_UTENTE ->
                    RecensioneService.getByRistorante(req);

            case VISUALIZZA_RECENSIONI_RISTORANTE ->
                    RecensioneService.getByRistorante(req);

            case VISUALIZZA_RECENSIONI_GESTORE ->
                    RecensioneService.getByGestore(req);

            case RISPONDI_RECENSIONE ->
                    RecensioneService.rispondi(req);

            // ============================
            // PREFERITI
            // ============================
            case AGGIUNGI_PREFERITO -> {
                int idUtente = req.payload.get("idUtente").getAsInt();
                int idRistorante = req.payload.get("idRistorante").getAsInt();

                boolean ok = PreferitiDAO.aggiungi(idUtente, idRistorante);
                if (!ok) yield Response.error("Impossibile aggiungere ai preferiti");
                yield Response.ok();
            }

            case RIMUOVI_PREFERITO -> {
                int idUtente = req.payload.get("idUtente").getAsInt();
                int idRistorante = req.payload.get("idRistorante").getAsInt();

                boolean ok = PreferitiDAO.rimuovi(idUtente, idRistorante);
                if (!ok) yield Response.error("Impossibile rimuovere dai preferiti");
                yield Response.ok();
            }

            case VISUALIZZA_PREFERITI -> {
                int idUtente = req.payload.get("idUtente").getAsInt();

                var lista = PreferitiDAO.getPreferiti(idUtente);

                var payload = new com.google.gson.JsonObject();
                var arr = new com.google.gson.JsonArray();

                for (Integer id : lista) arr.add(id);

                payload.add("preferiti", arr);

                yield Response.ok(payload);
            }

            // ============================
            // DEFAULT
            // ============================
            default -> Response.error("Tipo messaggio non gestito: " + req.type);
        };
    }

}
