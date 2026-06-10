package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.RistoranteDAO;
import server.dao.RistoranteUtenteDAO;
import server.dao.RecensioneDAO;
import server.model.Ristorante;
import server.model.Recensione;

import java.util.List;

/**
 * Service che gestisce tutte le operazioni relative ai ristoranti:
 *
 * - ricerca ristoranti (THEKNIFE + UTENTE)
 * - controllo proprietà ristorante
 * - visualizzazione dettagli ristorante
 * - CRUD ristoranti utente (aggiungi, modifica, elimina)
 * - riepilogo ristoranti di un gestore
 * - visualizzazione recensioni anonime
 *
 * Ogni metodo:
 * - legge i parametri dal payload della {@link Request}
 * - interagisce con i DAO (RistoranteDAO, RistoranteUtenteDAO, RecensioneDAO)
 * - costruisce una {@link Response} JSON per il client
 */
public class RistoranteService {

    // ============================================================
    // CERCA RISTORANTI (ALL)
    // ============================================================

    /**
     * Esegue una ricerca combinata tra:
     * - ristoranti THEKNIFE
     * - ristoranti UTENTE
     *
     * Applica filtri su:
     * - nome
     * - tipo cucina
     * - località
     * - fascia prezzo
     * - delivery
     * - prenotazione
     * - stelle minime
     *
     * @param req richiesta contenente i filtri
     * @return lista JSON dei ristoranti trovati
     */
  public static Response cerca(Request req) {
        try {

            // ============================
            // LETTURA FILTRI DAL PAYLOAD
            // ============================

            String query = req.payload.has("query")
                    ? req.payload.get("query").getAsString()
                    : "";

            String tipoCucina = req.payload.has("tipoCucina")
                    ? req.payload.get("tipoCucina").getAsString()
                    : "Tutte";

            String localita = req.payload.has("localita")
                    ? req.payload.get("localita").getAsString()
                    : "Tutte";

            int prezzoMin = req.payload.has("prezzoMin")
                    ? req.payload.get("prezzoMin").getAsInt()
                    : 1;

            int prezzoMax = req.payload.has("prezzoMax")
                    ? req.payload.get("prezzoMax").getAsInt()
                    : 5;

            boolean delivery = req.payload.has("delivery")
                    && req.payload.get("delivery").getAsBoolean();

            boolean prenotazione = req.payload.has("prenotazione")
                    && req.payload.get("prenotazione").getAsBoolean();

            int stelleMin = req.payload.has("stelleMin")
                    ? req.payload.get("stelleMin").getAsInt()
                    : 0;

            // ============================
            // 1️⃣ CERCA IN THEKNIFE
            // ============================

            List<Ristorante> listaTK = RistoranteDAO.cerca(
                    query,
                    tipoCucina,
                    localita,
                    prezzoMin,
                    prezzoMax,
                    delivery,
                    prenotazione,
                    stelleMin
            );

            // ============================
            // 2️⃣ CERCA IN RISTORANTI UTENTE
            // ============================

            List<Ristorante> listaUT = RistoranteUtenteDAO.cerca(
                    query,
                    tipoCucina,
                    localita,
                    prezzoMin,
                    prezzoMax,
                    delivery,
                    prenotazione,
                    stelleMin
            );

            // ============================
            // 3️⃣ UNISCI LE DUE LISTE
            // ============================

            JsonArray arr = new JsonArray();

            // THEKNIFE
            for (Ristorante r : listaTK) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("nome", r.getNome());
                o.addProperty("indirizzo", r.getIndirizzo());
                o.addProperty("citta", r.getCitta());
                o.addProperty("nazione", r.getNazione());
                o.addProperty("latitudine", r.getLatitudine());
                o.addProperty("longitudine", r.getLongitudine());
                o.addProperty("tipo_cucina", r.getTipoCucina());
                o.addProperty("fascia_prezzo", r.getFasciaPrezzo());
                o.addProperty("delivery", r.isDelivery());
                o.addProperty("prenotazione", r.isPrenotazione());
                o.addProperty("fonte", "THEKNIFE");
                arr.add(o);
            }

            // UTENTE
            for (Ristorante r : listaUT) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("nome", r.getNome());
                o.addProperty("indirizzo", r.getIndirizzo());
                o.addProperty("citta", r.getCitta());
                o.addProperty("nazione", r.getNazione());
                o.addProperty("latitudine", r.getLatitudine());
                o.addProperty("longitudine", r.getLongitudine());
                o.addProperty("tipo_cucina", r.getTipoCucina());
                o.addProperty("fascia_prezzo", r.getFasciaPrezzo());
                o.addProperty("delivery", r.isDelivery());
                o.addProperty("prenotazione", r.isPrenotazione());
                o.addProperty("fonte", "UTENTE");
                arr.add(o);
            }

            // ============================
            // 4️⃣ RESTITUISCI RISPOSTA
            // ============================

            JsonObject payload = new JsonObject();
            payload.add("ristoranti", arr);

            System.out.println("Ricerca completata. Ristoranti trovati: " + arr.size());
            return Response.ok(payload);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Errore ricerca ristoranti: " + e.getMessage());
        }
    }

    /**
     * Verifica se un ristorante appartiene a un gestore.
     *
     * @param req richiesta contenente idRistorante e idGestore
     * @return OK se il gestore è proprietario, errore altrimenti
     */
    public static Response controllaProprieta(Request req) {
        try {
            int idRistorante = req.payload.get("idRistorante").getAsInt();
            int idGestore = req.payload.get("idGestore").getAsInt();

            boolean proprietario = RistoranteUtenteDAO.isOwnedBy(idRistorante, idGestore);

            return proprietario
                    ? Response.ok()
                    : Response.error("Non sei il proprietario del ristorante");

        } catch (Exception e) {
            return Response.error("Errore controllo proprietà: " + e.getMessage());
        }
    }

    // ============================================================
    // VISUALIZZA DETTAGLI RISTORANTE (THEKNIFE)
    // ============================================================

    /**
     * Restituisce i dettagli di un ristorante THEKNIFE.
     *
     * @param req richiesta contenente id
     * @return dettagli JSON del ristorante
     */
    public static Response visualizza(Request req) {
        try {
            int id = req.payload.get("id").getAsInt();

            Ristorante r = RistoranteDAO.getById(id);
            if (r == null)
                return Response.error("Ristorante non trovato");

            JsonObject o = new JsonObject();
            o.addProperty("id", r.getId());
            o.addProperty("nome", r.getNome());
            o.addProperty("indirizzo", r.getIndirizzo());
            o.addProperty("citta", r.getCitta());
            o.addProperty("nazione", r.getNazione());
            o.addProperty("latitudine", r.getLatitudine());
            o.addProperty("longitudine", r.getLongitudine());
            o.addProperty("tipo_cucina", r.getTipoCucina());
            o.addProperty("fascia_prezzo", r.getFasciaPrezzo());
            o.addProperty("delivery", r.isDelivery());
            o.addProperty("prenotazione", r.isPrenotazione());
            o.addProperty("fonte", "THEKNIFE");

            return Response.ok(o);

        } catch (Exception e) {
            return Response.error("Errore visualizzazione ristorante: " + e.getMessage());
        }
    }

    // ============================================================
    // VISUALIZZA DETTAGLI RISTORANTE UTENTE
    // ============================================================

    /**
     * Restituisce i dettagli di un ristorante UTENTE.
     *
     * @param req richiesta contenente id
     * @return dettagli JSON del ristorante
     */
    public static Response visualizzaUtente(Request req) {
        try {
            int id = req.payload.get("id").getAsInt();

            Ristorante r = RistoranteUtenteDAO.getById(id);
            if (r == null)
                return Response.error("Ristorante non trovato");

            JsonObject o = new JsonObject();
            o.addProperty("id", r.getId());
            o.addProperty("nome", r.getNome());
            o.addProperty("indirizzo", r.getIndirizzo());
            o.addProperty("citta", r.getCitta());
            o.addProperty("nazione", r.getNazione());
            o.addProperty("latitudine", r.getLatitudine());
            o.addProperty("longitudine", r.getLongitudine());
            o.addProperty("tipo_cucina", r.getTipoCucina());
            o.addProperty("fascia_prezzo", r.getFasciaPrezzo());
            o.addProperty("delivery", r.isDelivery());
            o.addProperty("prenotazione", r.isPrenotazione());
            o.addProperty("fonte", "UTENTE");

            return Response.ok(o);

        } catch (Exception e) {
            return Response.error("Errore visualizzazione ristorante utente: " + e.getMessage());
        }
    }

    // ============================================================
    // AGGIUNGI RISTORANTE UTENTE
    // ============================================================

    /**
     * Aggiunge un nuovo ristorante creato da un gestore.
     *
     * @param req richiesta contenente tutti i campi del ristorante
     * @return OK o errore
     */
    public static Response aggiungi(Request req) {
        try {
            int idGestore = req.payload.get("idGestore").getAsInt();
            String nome = req.payload.get("nome").getAsString();
            String indirizzo = req.payload.get("indirizzo").getAsString();
            String tipoCucina = req.payload.get("tipo_cucina").getAsString();

            int fasciaPrezzo = req.payload.has("fascia_prezzo") ? req.payload.get("fascia_prezzo").getAsInt() : 0;
            String citta = req.payload.has("citta") ? req.payload.get("citta").getAsString() : "";
            String nazione = req.payload.has("nazione") ? req.payload.get("nazione").getAsString() : "";
            double lat = req.payload.has("latitudine") ? req.payload.get("latitudine").getAsDouble() : 0.0;
            double lon = req.payload.has("longitudine") ? req.payload.get("longitudine").getAsDouble() : 0.0;
            boolean delivery = req.payload.has("delivery") && req.payload.get("delivery").getAsBoolean();
            boolean prenotazione = req.payload.has("prenotazione") && req.payload.get("prenotazione").getAsBoolean();

            boolean ok = RistoranteUtenteDAO.aggiungi(
                    idGestore, nome, indirizzo, tipoCucina,
                    fasciaPrezzo, citta, nazione, lat, lon,
                    delivery, prenotazione
            );

            return ok ? Response.ok() : Response.error("Impossibile aggiungere ristorante");

        } catch (Exception e) {
            return Response.error("Errore aggiunta ristorante: " + e.getMessage());
        }
    }

    // ============================================================
    // MODIFICA RISTORANTE UTENTE
    // ============================================================

    /**
     * Modifica un ristorante creato da un gestore.
     *
     * @param req richiesta contenente i nuovi valori
     * @return OK o errore
     */
    public static Response modifica(Request req) {
        try {
            int id = req.payload.get("id").getAsInt();
            String nome = req.payload.get("nome").getAsString();
            String indirizzo = req.payload.get("indirizzo").getAsString();
            String tipoCucina = req.payload.get("tipo_cucina").getAsString();

            int fasciaPrezzo = req.payload.has("fascia_prezzo") && !req.payload.get("fascia_prezzo").isJsonNull()
                    ? req.payload.get("fascia_prezzo").getAsInt()
                    : 0;

            String citta = req.payload.has("citta") && !req.payload.get("citta").isJsonNull()
                    ? req.payload.get("citta").getAsString()
                    : "";

            String nazione = req.payload.has("nazione") && !req.payload.get("nazione").isJsonNull()
                    ? req.payload.get("nazione").getAsString()
                    : "";

            double lat = req.payload.has("latitudine") && !req.payload.get("latitudine").isJsonNull()
                    ? req.payload.get("latitudine").getAsDouble()
                    : 0.0;

            double lon = req.payload.has("longitudine") && !req.payload.get("longitudine").isJsonNull()
                    ? req.payload.get("longitudine").getAsDouble()
                    : 0.0;

            boolean delivery = req.payload.has("delivery") && !req.payload.get("delivery").isJsonNull()
                    ? req.payload.get("delivery").getAsBoolean()
                    : false;

            boolean prenotazione = req.payload.has("prenotazione") && !req.payload.get("prenotazione").isJsonNull()
                    ? req.payload.get("prenotazione").getAsBoolean()
                    : false;

            boolean ok = RistoranteUtenteDAO.modifica(
                    id, nome, indirizzo, tipoCucina,
                    fasciaPrezzo, citta, nazione, lat, lon,
                    delivery, prenotazione
            );

            return ok ? Response.ok() : Response.error("Impossibile modificare ristorante");

        } catch (Exception e) {
            return Response.error("Errore modifica ristorante: " + e.getMessage());
        }
    }

    // ============================================================
    // ELIMINA RISTORANTE
    // ============================================================

    /**
     * Elimina un ristorante creato da un gestore.
     *
     * @param req richiesta contenente id
     * @return OK o errore
     */
    public static Response elimina(Request req) {
        try {
            int id = req.payload.get("id").getAsInt();

            boolean ok = RistoranteUtenteDAO.elimina(id);

            return ok ? Response.ok() : Response.error("Impossibile eliminare ristorante");

        } catch (Exception e) {
            return Response.error("Errore eliminazione ristorante: " + e.getMessage());
        }
    }

    // ============================================================
    // RIEPILOGO GESTORE
    // ============================================================

    /**
     * Restituisce la lista dei ristoranti gestiti da un gestore.
     *
     * @param req richiesta contenente idGestore
     * @return lista JSON dei ristoranti
     */
    public static Response riepilogoGestore(Request req) {
        try {
            int idGestore = req.payload.get("idGestore").getAsInt();

            List<Ristorante> lista = RistoranteUtenteDAO.getByGestore(idGestore);

            JsonArray arr = new JsonArray();
            for (Ristorante r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("nome", r.getNome());
                o.addProperty("indirizzo", r.getIndirizzo());
                o.addProperty("citta", r.getCitta());
                o.addProperty("nazione", r.getNazione());
                o.addProperty("fonte", "UTENTE");
                arr.add(o);
            }

            JsonObject payload = new JsonObject();
            payload.add("ristoranti", arr);

            return Response.ok(payload);

        } catch (Exception e) {
            return Response.error("Errore riepilogo gestore: " + e.getMessage());
        }
    }

    // ============================================================
    // RECENSIONI ANONIME
    // ============================================================

    /**
     * Restituisce le recensioni ANONIME di un ristorante,
     * includendo eventuali risposte del gestore.
     *
     * @param req richiesta contenente idRistorante
     * @return lista JSON delle recensioni anonime
     */
    public static Response visualizzaRecensioniAnonime(Request req) {
        try {
            int idRistorante = req.payload.get("idRistorante").getAsInt();
            List<Recensione> lista = RecensioneDAO.getByRistorante(idRistorante);

            JsonArray arr = new JsonArray();

            for (Recensione r : lista) {
                JsonObject o = new JsonObject();

                o.addProperty("id", r.getId());
                o.addProperty("voto", r.getVoto());
                o.addProperty("testo", r.getTesto());

                // Risposta del gestore (può essere null)
                String risposta = RecensioneDAO.getRispostaByRecensione(r.getId());
                if (risposta != null)
                    o.addProperty("risposta", risposta);
                else
                    o.add("risposta", null);

                arr.add(o);
            }

            JsonObject data = new JsonObject();
            data.add("recensioni", arr);

            return Response.ok(data);

        } catch (Exception e) {
            return Response.error("Errore caricamento recensioni anonime: " + e.getMessage());
        }
    }
}
