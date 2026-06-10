package server.connection;

import com.google.gson.Gson;
import common.Request;
import common.Response;

import server.service.UtenteService;
import server.service.RistoranteService;
import server.service.PreferitiService;
import server.service.RecensioneService;
import server.dao.PreferitiDAO;

import java.io.*;
import java.net.Socket;

/**
 * Gestisce una singola connessione client-server.
 *
 * Ogni client connesso al server viene servito da un thread dedicato
 * che esegue questa classe. Il ClientHandler:
 *
 * - legge richieste JSON dal socket
 * - le converte in oggetti {@link Request}
 * - le inoltra ai vari servizi tramite il metodo {@code process()}
 * - invia al client una {@link Response} serializzata in JSON
 *
 * Il ciclo continua finché il client rimane connesso.
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final Gson gson = new Gson();

    /**
     * Costruisce un handler per un client connesso.
     *
     * @param socket socket della connessione client
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Ciclo principale del thread:
     * - apre gli stream
     * - legge continuamente richieste JSON
     * - le elabora tramite {@code process()}
     * - invia la risposta al client
     */
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

    /**
     * Smista la richiesta al servizio corretto in base al {@link common.MessageType}.
     *
     * @param req richiesta ricevuta dal client
     * @return risposta generata dal servizio appropriato
     */
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
            // RISTORANTI (MICHELIN + UTENTE)
            // ============================
            case CERCA_RISTORANTI -> RistoranteService.cerca(req);
            case VISUALIZZA -> RistoranteService.visualizza(req);
            case VISUALIZZA_UTENTE -> RistoranteService.visualizzaUtente(req);

            case AGGIUNGI_RISTORANTE -> RistoranteService.aggiungi(req);
            case MODIFICA_RISTORANTE -> RistoranteService.modifica(req);
            case ELIMINA_RISTORANTE -> RistoranteService.elimina(req);
            case VISUALIZZA_RIEPILOGO_GESTORE -> RistoranteService.riepilogoGestore(req);

            case CONTROLLA_PROPRIETA_RISTORANTE -> RistoranteService.controllaProprieta(req);

            // ============================
            // RECENSIONI
            // ============================
            case AGGIUNGI_RECENSIONE -> RecensioneService.aggiungi(req);
            case MODIFICA_RECENSIONE -> RecensioneService.modifica(req);
            case ELIMINA_RECENSIONE -> RecensioneService.elimina(req);

            case VISUALIZZA_RECENSIONI_UTENTE ->
                    RecensioneService.getByUtente(req);

            case VISUALIZZA_RECENSIONI_ANONIME ->
                    RistoranteService.visualizzaRecensioniAnonime(req);

            case VISUALIZZA_RECENSIONI_NON_ANONIME ->
                    RecensioneService.getNonAnonime(req);

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
                String fonte = req.payload.get("fonte").getAsString();

                boolean ok = PreferitiDAO.aggiungi(idUtente, idRistorante, fonte);

                if (!ok)
                    yield Response.error("Impossibile aggiungere ai preferiti");

                yield Response.ok();
            }

            case RIMUOVI_PREFERITO -> {
                int idUtente = req.payload.get("idUtente").getAsInt();
                int idRistorante = req.payload.get("idRistorante").getAsInt();

                boolean ok = PreferitiDAO.rimuovi(idUtente, idRistorante);

                if (!ok)
                    yield Response.error("Impossibile rimuovere dai preferiti");

                yield Response.ok();
            }

            case VISUALIZZA_PREFERITI -> {
                yield PreferitiService.visualizzaPreferiti(req);
            }

            // ============================
            // DEFAULT
            // ============================
            default -> Response.error("Tipo messaggio non gestito: " + req.type);
        };
    }

}
