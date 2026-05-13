package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.RistoranteDAO;
import server.dao.RecensioneDAO;
import server.model.Ristorante;
import server.model.Recensione;

import java.util.List;

public class RistoranteService {

    // ============================
    // CERCA RISTORANTI
    // ============================
    public static Response cerca(Request req) {
        try {
            String query = req.payload.get("query").getAsString();

            String categoria = req.payload.has("categoria")
                    ? req.payload.get("categoria").getAsString()
                    : "Tutte";

            List<Ristorante> lista = RistoranteDAO.cerca(query, categoria);

            JsonArray arr = new JsonArray();
            for (Ristorante r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("nome", r.getNome());
                o.addProperty("indirizzo", r.getIndirizzo());
                o.addProperty("categoria", r.getCategoria());
                arr.add(o);
            }

            JsonObject payload = new JsonObject();
            payload.add("ristoranti", arr);

            return Response.ok(payload);

        } catch (Exception e) {
            return Response.error("Errore ricerca ristoranti: " + e.getMessage());
        }
    }

    // ============================
    // VISUALIZZA DETTAGLI
    // ============================
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
            o.addProperty("categoria", r.getCategoria());
            o.addProperty("descrizione", r.getDescrizione());

            return Response.ok(o);

        } catch (Exception e) {
            return Response.error("Errore visualizzazione ristorante: " + e.getMessage());
        }
    }

    // ============================
    // AGGIUNGI RISTORANTE (GESTORE)
    // ============================
    public static Response aggiungi(Request req) {
        try {
            String nome = req.payload.get("nome").getAsString();
            String indirizzo = req.payload.get("indirizzo").getAsString();
            String categoria = req.payload.get("categoria").getAsString();
            String descrizione = req.payload.get("descrizione").getAsString();
            int idGestore = req.payload.get("idGestore").getAsInt();

            boolean ok = RistoranteDAO.aggiungi(nome, indirizzo, categoria, descrizione, idGestore);

            if (!ok) return Response.error("Impossibile aggiungere ristorante");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore aggiunta ristorante: " + e.getMessage());
        }
    }

    // ============================
    // MODIFICA RISTORANTE
    // ============================
    public static Response modifica(Request req) {
        try {
            int id = req.payload.get("id").getAsInt();
            String nome = req.payload.get("nome").getAsString();
            String indirizzo = req.payload.get("indirizzo").getAsString();
            String categoria = req.payload.get("categoria").getAsString();
            String descrizione = req.payload.get("descrizione").getAsString();

            boolean ok = RistoranteDAO.modifica(id, nome, indirizzo, categoria, descrizione);

            if (!ok) return Response.error("Impossibile modificare ristorante");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore modifica ristorante: " + e.getMessage());
        }
    }

    // ============================
    // ELIMINA RISTORANTE
    // ============================
    public static Response elimina(Request req) {
        try {
            int id = req.payload.get("id").getAsInt();

            boolean ok = RistoranteDAO.elimina(id);

            if (!ok) return Response.error("Impossibile eliminare ristorante");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore eliminazione ristorante: " + e.getMessage());
        }
    }

    // ============================
    // RIEPILOGO RISTORANTI DEL GESTORE
    // ============================
    public static Response riepilogoGestore(Request req) {
        try {
            int idGestore = req.payload.get("idGestore").getAsInt();

            List<Ristorante> lista = RistoranteDAO.getByGestore(idGestore);

            JsonArray arr = new JsonArray();
            for (Ristorante r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("nome", r.getNome());
                o.addProperty("categoria", r.getCategoria());
                arr.add(o);
            }

            JsonObject payload = new JsonObject();
            payload.add("ristoranti", arr);

            return Response.ok(payload);

        } catch (Exception e) {
            return Response.error("Errore riepilogo gestore: " + e.getMessage());
        }
    }

    // ============================
    // RECENSIONI DEI RISTORANTI DEL GESTORE
    // ============================
    public static Response recensioniGestore(Request req) {
        try {
            int idGestore = req.payload.get("idGestore").getAsInt();

            List<Recensione> lista = RecensioneDAO.getByGestore(idGestore);

            JsonArray arr = new JsonArray();
            for (Recensione r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("idUtente", r.getIdUtente());
                o.addProperty("idRistorante", r.getIdRistorante());
                o.addProperty("voto", r.getVoto());
                o.addProperty("testo", r.getTesto());
                arr.add(o);
            }

            JsonObject payload = new JsonObject();
            payload.add("recensioni", arr);

            return Response.ok(payload);

        } catch (Exception e) {
            return Response.error("Errore recensioni gestore: " + e.getMessage());
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

            if (!ok) return Response.error("Impossibile inviare risposta");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore risposta recensione: " + e.getMessage());
        }
    }
}
