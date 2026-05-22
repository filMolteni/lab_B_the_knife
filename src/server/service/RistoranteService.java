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

public class RistoranteService {

    // ============================================================
    // CERCA RISTORANTI (ALL)
    // ============================================================
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

            return Response.ok(payload);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Errore ricerca ristoranti: " + e.getMessage());
        }
    }

    public static Response controllaProprieta(Request req) {
    try {
        int idRistorante = req.payload.get("idRistorante").getAsInt();
        int idGestore = req.payload.get("idGestore").getAsInt();

        boolean proprietario = RistoranteUtenteDAO.isOwnedBy(idRistorante, idGestore);

        if (proprietario) {
            return Response.ok(); 
        } else {
            return Response.error("Non sei il proprietario del ristorante");
        }

    } catch (Exception e) {
        return Response.error("Errore controllo proprietà: " + e.getMessage());
    }
}


    // ============================================================
    // VISUALIZZA DETTAGLI RISTORANTE (THEKNIFE)
    // ============================================================
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

            if (!ok)
                return Response.error("Impossibile aggiungere ristorante");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore aggiunta ristorante: " + e.getMessage());
        }
    }

    // ============================================================
    // MODIFICA RISTORANTE UTENTE
    // ============================================================
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

            if (!ok)
                return Response.error("Impossibile modificare ristorante");

            return Response.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("Errore modifica ristorante: " + e.getMessage());
        }
    }


    // ============================================================
    // ELIMINA RISTORANTE
    // ============================================================
    public static Response elimina(Request req) {
        try {
            int id = req.payload.get("id").getAsInt();

            boolean ok = RistoranteUtenteDAO.elimina(id);

            if (!ok)
                return Response.error("Impossibile eliminare ristorante");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore eliminazione ristorante: " + e.getMessage());
        }
    }

    // ============================================================
    // RIEPILOGO GESTORE
    // ============================================================
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
    public static Response visualizzaRecensioniAnonime(Request req) {
    try {
        int idRistorante = req.payload.get("idRistorante").getAsInt();
        List<Recensione> lista = RecensioneDAO.getByRistorante(idRistorante);

        JsonArray arr = new JsonArray();

        for (Recensione r : lista) {
            JsonObject o = new JsonObject();

            o.addProperty("id", r.getId());               // ⭐ NECESSARIO PER RISPOSTE
            o.addProperty("voto", r.getVoto());
            o.addProperty("testo", r.getTesto());

            // ⭐ AGGIUNGI LA RISPOSTA (può essere null)
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
