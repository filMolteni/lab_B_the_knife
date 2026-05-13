package server.service;

import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.UtenteDAO;
import server.model.Utente;

public class UtenteService {

    public static Response login(Request req) {
        try {
            String email = req.payload.get("email").getAsString();
            String password = req.payload.get("password").getAsString();

            Utente u = UtenteDAO.login(email, password);

            if (u == null)
                return Response.error("Credenziali non valide");

            JsonObject payload = new JsonObject();
            payload.addProperty("id", u.getId());
            payload.addProperty("nome", u.getNome());
            payload.addProperty("email", u.getEmail());
            payload.addProperty("ruolo", u.getRuolo());

            return Response.ok(payload);

        } catch (Exception e) {
            return Response.error("Errore login: " + e.getMessage());
        }
    }

    public static Response registrati(Request req) {
        try {
            String nome = req.payload.get("nome").getAsString();
            String email = req.payload.get("email").getAsString();
            String password = req.payload.get("password").getAsString();
            String ruolo = req.payload.get("ruolo").getAsString();

            boolean ok = UtenteDAO.registrati(nome, email, password, ruolo);

            if (ok)
                return Response.ok(new JsonObject());
            else
                return Response.error("Registrazione fallita");

        } catch (Exception e) {
            return Response.error("Errore registrazione: " + e.getMessage());
        }
    }
}
