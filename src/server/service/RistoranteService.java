package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.RistoranteDAO;
import server.dao.PreferitiDAO;
import server.model.Ristorante;

import java.util.List;

public class RistoranteService {

    // ============================
    //  CERCA RISTORANTI
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
    //  VISUALIZZA DETTAGLI
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
    //  AGGIUNGI AI PREFERITI
    // ============================
    public static Response aggiungiPreferito(Request req) {
        try {
            int idRistorante = req.payload.get("id").getAsInt();
            int idUtente = req.payload.get("utente").getAsInt();

            boolean ok = PreferitiDAO.aggiungi(idUtente, idRistorante);

            if (ok) {
                JsonObject payload = new JsonObject();
                payload.addProperty("status", "ok");
                return Response.ok(payload);
            } else {
                return Response.error("Impossibile aggiungere ai preferiti");
            }

        } catch (Exception e) {
            return Response.error("Errore aggiunta preferito: " + e.getMessage());
        }
    }
}
