package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.RecensioneDAO;
import server.model.Recensione;

import java.util.List;

public class RecensioneService {

    // ============================
    // AGGIUNGI RECENSIONE
    // ============================
    public static Response aggiungi(Request req) {
        try {
            JsonObject p = req.payload;

            int idUtente = p.get("idUtente").getAsInt();
            int idRistorante = p.get("idRistorante").getAsInt();
            int voto = p.get("voto").getAsInt();
            String testo = p.get("testo").getAsString();
            String fonte = p.get("fonte").getAsString();

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
    // RECENSIONI DI UN RISTORANTE (UTENTE + THEKNIFE)
    // ============================
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
                o.addProperty("nomeRistorante", r.getNomeRistorante()); // ⭐ NUOVO
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

       public static Response getNonAnonime(Request req) {
    try {
        int idRistorante = req.payload.get("idRistorante").getAsInt();

        // ⭐ FUNZIONE CORRETTA
        List<Recensione> lista = RecensioneDAO.getNonAnonimeByRistorante(idRistorante);

        JsonArray arr = new JsonArray();

        for (Recensione r : lista) {
            JsonObject o = new JsonObject();
            o.addProperty("id", r.getId());
            o.addProperty("idUtente", r.getIdUtente());
            o.addProperty("nomeUtente", r.getNomeUtente());          // ⭐ ORA ESISTE
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
        return Response.error("Errore caricamento recensioni non anonime: " + e.getMessage());
    }
}



    // ============================
    // RECENSIONI SCRITTE DA UN UTENTE
    // ============================
    public static Response getByUtente(Request req) {
        try {
            int idUtente = req.payload.get("idUtente").getAsInt();

            List<Recensione> lista = RecensioneDAO.getByUtente(idUtente);

            JsonArray arr = new JsonArray();

            for (Recensione r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("idRistorante", r.getIdRistorante());
                o.addProperty("nomeRistorante", r.getNomeRistorante()); // ⭐ NUOVO
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
                o.addProperty("nomeRistorante", r.getNomeRistorante()); // ⭐ NUOVO
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
    public static Response rispondi(Request req) {
        try {
            int idRecensione = req.payload.get("idRecensione").getAsInt();
            String risposta = req.payload.get("risposta").getAsString();

            boolean ok = RecensioneDAO.rispondi(idRecensione, risposta);

            if (!ok)
                return Response.error("Impossibile inviare risposta");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore risposta recensione: " + e.getMessage());
        }
    }
}
