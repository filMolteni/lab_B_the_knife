package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.RistoranteDAO;
import server.model.Ristorante;

import java.util.List;

public class RistoranteService {

    public static Response cerca(Request req) {
        try {
            String query = req.payload.get("query").getAsString();

            List<Ristorante> lista = RistoranteDAO.cerca(query);

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
}
