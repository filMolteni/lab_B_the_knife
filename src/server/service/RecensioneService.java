package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.RecensioneDAO;
import server.dao.RistoranteDAO;
import server.model.Recensione;

import java.util.List;

/**
 * Service che gestisce tutte le operazioni relative alle recensioni:
 *
 * - aggiunta di una recensione
 * - modifica
 * - eliminazione
 * - recupero recensioni per ristorante
 * - recupero recensioni non anonime
 * - recupero recensioni scritte da un utente
 * - recupero recensioni dei ristoranti di un gestore
 * - risposta del gestore a una recensione
 *
 * Ogni metodo:
 * - legge i parametri dal payload della {@link Request}
 * - interagisce con il {@link RecensioneDAO}
 * - costruisce una {@link Response} JSON per il client
 */
public class RecensioneService {

    // ============================
    // AGGIUNGI RECENSIONE
    // ============================

    /**
     * Aggiunge una nuova recensione.
     *
     * Logica:
     * 1. Legge idUtente, idRistorante, voto, testo
     * 2. Determina automaticamente la fonte (THEKNIFE / UTENTE)
     * 3. Verifica che l'utente non abbia già recensito il ristorante
     * 4. Inserisce la recensione tramite DAO
     *
     * @param req richiesta contenente i dati della recensione
     * @return risposta OK o errore
     */
    public static Response aggiungi(Request req) {
        try {
            JsonObject p = req.payload;

            int idUtente = p.get("idUtente").getAsInt();
            int idRistorante = p.get("idRistorante").getAsInt();
            int voto = p.get("voto").getAsInt();
            String testo = p.get("testo").getAsString();

            // Determina la fonte del ristorante
            String fonte = RistoranteDAO.esisteUtente(idRistorante) ? "UTENTE" : "THEKNIFE";

            if (RecensioneDAO.recensioneEsiste(idUtente, idRistorante)) {
                return Response.error("Hai già recensito questo ristorante.");
            }

            boolean ok = RecensioneDAO.aggiungi(idUtente, idRistorante, voto, testo, fonte);

            if (!ok)
                return Response.error("Impossibile aggiungere recensione");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore aggiunta recensione: " + e.getMessage());
        }
    }

    // ============================
    // MODIFICA RECENSIONE
    // ============================

    /**
     * Modifica una recensione esistente.
     *
     * @param req richiesta contenente idRecensione, voto e testo aggiornati
     * @return risposta OK o errore
     */
    public static Response modifica(Request req) {
        try {
            JsonObject p = req.payload;

            int idRecensione = p.get("idRecensione").getAsInt();
            int voto = p.get("voto").getAsInt();
            String testo = p.get("testo").getAsString();

            boolean ok = RecensioneDAO.modifica(idRecensione, voto, testo);

            if (!ok)
                return Response.error("Impossibile modificare recensione");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore modifica recensione: " + e.getMessage());
        }
    }

    // ============================
    // ELIMINA RECENSIONE
    // ============================

    /**
     * Elimina una recensione.
     *
     * @param req richiesta contenente idRecensione
     * @return risposta OK o errore
     */
    public static Response elimina(Request req) {
        try {
            int idRecensione = req.payload.get("idRecensione").getAsInt();

            boolean ok = RecensioneDAO.elimina(idRecensione);

            if (!ok)
                return Response.error("Impossibile eliminare recensione");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore eliminazione recensione: " + e.getMessage());
        }
    }

    // ============================
    // RECENSIONI DI UN RISTORANTE
    // ============================

    /**
     * Restituisce tutte le recensioni (anonime + non anonime)
     * di un ristorante.
     *
     * @param req richiesta contenente idRistorante
     * @return lista JSON delle recensioni
     */
    public static Response getByRistorante(Request req) {
        try {
            int idRistorante = req.payload.get("idRistorante").getAsInt();

            List<Recensione> lista = RecensioneDAO.getByRistorante(idRistorante);

            JsonArray arr = new JsonArray();

            for (Recensione r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("idUtente", r.getIdUtente());
                o.addProperty("idRistorante", r.getIdRistorante());
                o.addProperty("nomeRistorante", r.getNomeRistorante());
                o.addProperty("voto", r.getVoto());
                o.addProperty("testo", r.getTesto());
                o.addProperty("data", r.getData());
                o.addProperty("fonte", r.getFonte());
                arr.add(o);
            }

            JsonObject res = new JsonObject();
            res.add("recensioni", arr);

            return Response.ok(res);

        } catch (Exception e) {
            return Response.error("Errore caricamento recensioni: " + e.getMessage());
        }
    }

    // ============================
    // RECENSIONI NON ANONIME
    // ============================

    /**
     * Restituisce solo le recensioni NON anonime di un ristorante,
     * includendo anche la risposta del gestore (se presente).
     *
     * @param req richiesta contenente idRistorante
     * @return lista JSON delle recensioni non anonime
     */
    public static Response getNonAnonime(Request req) {
        try {
            int idRistorante = req.payload.get("idRistorante").getAsInt();

            List<Recensione> lista = RecensioneDAO.getNonAnonimeByRistorante(idRistorante);

            JsonArray arr = new JsonArray();

            for (Recensione r : lista) {
                JsonObject o = new JsonObject();

                o.addProperty("id", r.getId());
                o.addProperty("idUtente", r.getIdUtente());
                o.addProperty("nomeUtente", r.getNomeUtente());
                o.addProperty("idRistorante", r.getIdRistorante());
                o.addProperty("nomeRistorante", r.getNomeRistorante());
                o.addProperty("voto", r.getVoto());
                o.addProperty("testo", r.getTesto());
                o.addProperty("data", r.getData());
                o.addProperty("fonte", r.getFonte());

                // Aggiunge la risposta del gestore, se esiste
                String risposta = RecensioneDAO.getRispostaByRecensione(r.getId());
                if (risposta != null)
                    o.addProperty("risposta", risposta);
                else
                    o.add("risposta", null);

                arr.add(o);
            }

            JsonObject res = new JsonObject();
            res.add("recensioni", arr);

            return Response.ok(res);

        } catch (Exception e) {
            return Response.error("Errore caricamento recensioni non anonime: " + e.getMessage());
        }
    }

    // ============================
    // RECENSIONI SCRITTE DA UN UTENTE
    // ============================

    /**
     * Restituisce tutte le recensioni scritte da un utente.
     *
     * @param req richiesta contenente idUtente
     * @return lista JSON delle recensioni
     */
    public static Response getByUtente(Request req) {
        try {
            int idUtente = req.payload.get("idUtente").getAsInt();

            List<Recensione> lista = RecensioneDAO.getByUtente(idUtente);

            JsonArray arr = new JsonArray();

            for (Recensione r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("idRistorante", r.getIdRistorante());
                o.addProperty("nomeRistorante", r.getNomeRistorante());
                o.addProperty("voto", r.getVoto());
                o.addProperty("testo", r.getTesto());
                o.addProperty("data", r.getData());
                o.addProperty("fonte", r.getFonte());
                arr.add(o);
            }

            JsonObject res = new JsonObject();
            res.add("recensioni", arr);

            return Response.ok(res);

        } catch (Exception e) {
            return Response.error("Errore caricamento recensioni utente: " + e.getMessage());
        }
    }

    // ============================
    // RECENSIONI DEI RISTORANTI DI UN GESTORE
    // ============================

    /**
     * Restituisce tutte le recensioni dei ristoranti gestiti da un gestore.
     *
     * @param req richiesta contenente idGestore
     * @return lista JSON delle recensioni
     */
    public static Response getByGestore(Request req) {
        try {
            int idGestore = req.payload.get("idGestore").getAsInt();

            List<Recensione> lista = RecensioneDAO.getByGestore(idGestore);

            JsonArray arr = new JsonArray();

            for (Recensione r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("idUtente", r.getIdUtente());
                o.addProperty("idRistorante", r.getIdRistorante());
                o.addProperty("nomeRistorante", r.getNomeRistorante());
                o.addProperty("voto", r.getVoto());
                o.addProperty("testo", r.getTesto());
                o.addProperty("data", r.getData());
                o.addProperty("fonte", r.getFonte());
                arr.add(o);
            }

            JsonObject res = new JsonObject();
            res.add("recensioni", arr);

            return Response.ok(res);

        } catch (Exception e) {
            return Response.error("Errore caricamento recensioni gestore: " + e.getMessage());
        }
    }

    // ============================
    // RISPONDI A RECENSIONE
    // ============================

    /**
     * Permette al gestore di rispondere a una recensione.
     *
     * @param req richiesta contenente idRecensione e testo della risposta
     * @return risposta OK o errore
     */
    public static Response rispondi(Request req) {
        try {
            int idRecensione = req.payload.get("idRecensione").getAsInt();
            String risposta = req.payload.get("testo").getAsString();

            boolean ok = RecensioneDAO.rispondi(idRecensione, risposta);

            if (!ok)
                return Response.error("Impossibile inviare risposta");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore risposta recensione: " + e.getMessage());
        }
    }
}
