package server.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.PreferitiDAO;
import server.model.Ristorante;

/**
 * Service dedicato alla gestione dei ristoranti preferiti dell'utente.
 *
 * Questo service:
 * - riceve una richiesta dal client
 * - interroga il {@link PreferitiDAO}
 * - converte i risultati in JSON
 * - restituisce una {@link Response} pronta per essere inviata al client
 *
 * La logica principale consiste nel trasformare i model {@link Ristorante}
 * in oggetti JSON compatibili con il client JavaFX.
 */
public class PreferitiService {

    /**
     * Restituisce la lista completa dei ristoranti preferiti dell'utente.
     *
     * Il metodo:
     * 1. legge l'idUtente dal payload della richiesta
     * 2. ottiene la lista dei ristoranti preferiti dal DAO
     * 3. costruisce un array JSON con tutti i campi necessari al client
     * 4. aggiunge la proprietà "fonte" (THEKNIFE / UTENTE)
     * 5. restituisce una risposta OK con il payload
     *
     * @param req richiesta contenente idUtente
     * @return risposta JSON con la lista dei preferiti
     */
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

                // ⭐ Indica se il ristorante proviene da THEKNIFE o UTENTE
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
