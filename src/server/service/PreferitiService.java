package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.PreferitiDAO;
import server.model.Ristorante;

public class PreferitiService {

    public static Response visualizzaPreferiti(Request req) {
        try {
            int idUtente = req.payload.get("idUtente").getAsInt();

            var lista = PreferitiDAO.getRistorantiPreferiti(idUtente);

            JsonArray arr = new JsonArray();

            for (Ristorante r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("nome", r.getNome());
                o.addProperty("indirizzo", r.getIndirizzo());
                o.addProperty("citta", r.getCitta());
                o.addProperty("nazione", r.getNazione());
                o.addProperty("latitudine", r.getLatitudine());
                o.addProperty("longitudine", r.getLongitudine());
                o.addProperty("fasciaPrezzo", r.getFasciaPrezzo());
                o.addProperty("tipoCucina", r.getTipoCucina());
                o.addProperty("delivery", r.isDelivery());
                o.addProperty("prenotazione", r.isPrenotazione());

                // ⭐ AGGIUNTA FONDAMENTALE
                // Se il ristorante proviene da THEKNIFE o UTENTE
                if (r.isTheKnife()) {
                    o.addProperty("fonte", "THEKNIFE");
                } else {
                    o.addProperty("fonte", "UTENTE");
                }

                arr.add(o);
            }

            JsonObject payload = new JsonObject();
            payload.add("preferiti", arr);

            return Response.ok(payload);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Errore caricamento preferiti");
        }
    }
}
