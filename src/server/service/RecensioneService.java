package server.service;

import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.RecensioneDAO;

public class RecensioneService {

    public static Response aggiungi(Request req) {
        try {
            JsonObject p = req.payload;

            int idUtente = p.get("idUtente").getAsInt();
            int idRistorante = p.get("idRistorante").getAsInt();
            int voto = p.get("voto").getAsInt();
            String testo = p.get("testo").getAsString();

            boolean ok = RecensioneDAO.aggiungi(idUtente, idRistorante, voto, testo);

            if (!ok)
                return Response.error("Impossibile aggiungere recensione");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore aggiunta recensione: " + e.getMessage());
        }
    }

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
}
