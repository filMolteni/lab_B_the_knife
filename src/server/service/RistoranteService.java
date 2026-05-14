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

    // ============================
    // CERCA RISTORANTI
    // ============================
    public static Response cerca(Request req) {
        try {
            String query = req.payload.get("query").getAsString();

            String tipoCucina = req.payload.has("tipoCucina")
                    ? req.payload.get("tipoCucina").getAsString()
                    : "Tutte";

            List<Ristorante> lista = RistoranteDAO.cerca(query, tipoCucina);

            JsonArray arr = new JsonArray();
            for (Ristorante r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("id", r.getId());
                o.addProperty("nome", r.getNome());
                o.addProperty("indirizzo", r.getIndirizzo());
                o.addProperty("citta", r.getCitta());
                o.addProperty("nazione", r.getNazione());
                o.addProperty("tipo_cucina", r.getTipoCucina());
                o.addProperty("fascia_prezzo", r.getFasciaPrezzo());
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
    // VISUALIZZA DETTAGLI RISTORANTE (USATO DAL CLIENT)
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
            o.addProperty("citta", r.getCitta());
            o.addProperty("nazione", r.getNazione());
            o.addProperty("latitudine", r.getLatitudine());
            o.addProperty("longitudine", r.getLongitudine());
            o.addProperty("tipo_cucina", r.getTipoCucina());
            o.addProperty("fascia_prezzo", r.getFasciaPrezzo());
            o.addProperty("delivery", r.isDelivery());
            o.addProperty("prenotazione", r.isPrenotazione());

            return Response.ok(o);

        } catch (Exception e) {
            return Response.error("Errore visualizzazione ristorante: " + e.getMessage());
        }
    }

    // ============================
    // VISUALIZZA DETTAGLI (UTENTE LOGGATO)
    // ============================
    public static Response visualizzaUtente(Request req) {
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

            return Response.ok(o);

        } catch (Exception e) {
            return Response.error("Errore visualizzazione ristorante utente: " + e.getMessage());
        }
    }

    // ============================
    // AGGIUNGI RISTORANTE
    // ============================
    public static Response aggiungi(Request req) {
        try {
            int idGestore = req.payload.get("idGestore").getAsInt();
            String nome = req.payload.get("nome").getAsString();
            String indirizzo = req.payload.get("indirizzo").getAsString();
            String tipoCucina = req.payload.get("tipo_cucina").getAsString();
            int fasciaPrezzo = req.payload.get("fascia_prezzo").getAsInt();
            String citta = req.payload.get("citta").getAsString();
            String nazione = req.payload.get("nazione").getAsString();
            double lat = req.payload.get("latitudine").getAsDouble();
            double lon = req.payload.get("longitudine").getAsDouble();
            boolean delivery = req.payload.get("delivery").getAsBoolean();
            boolean prenotazione = req.payload.get("prenotazione").getAsBoolean();

            boolean ok = RistoranteUtenteDAO.aggiungi(
                    idGestore, nome, indirizzo, tipoCucina,
                    fasciaPrezzo, citta, nazione, lat, lon,
                    delivery, prenotazione
            );

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
            String tipoCucina = req.payload.get("tipo_cucina").getAsString();
            int fasciaPrezzo = req.payload.get("fascia_prezzo").getAsInt();
            boolean delivery = req.payload.get("delivery").getAsBoolean();
            boolean prenotazione = req.payload.get("prenotazione").getAsBoolean();

            boolean ok = RistoranteUtenteDAO.modifica(
                    id, nome, indirizzo, tipoCucina,
                    fasciaPrezzo, delivery, prenotazione
            );

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

            boolean ok = RistoranteUtenteDAO.elimina(id);

            if (!ok) return Response.error("Impossibile eliminare ristorante");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore eliminazione ristorante: " + e.getMessage());
        }
    }

    // ============================
    // RIEPILOGO GESTORE
    // ============================
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
    // RECENSIONI ANONIME
    // ============================
    public static Response visualizzaRecensioniAnonime(Request req) {
        try {
            int idRistorante = req.payload.get("idRistorante").getAsInt();
            List<Recensione> lista = RecensioneDAO.getByRistorante(idRistorante);

            JsonArray arr = new JsonArray();
            for (Recensione r : lista) {
                JsonObject o = new JsonObject();
                o.addProperty("voto", r.getVoto());
                o.addProperty("testo", r.getTesto());
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
