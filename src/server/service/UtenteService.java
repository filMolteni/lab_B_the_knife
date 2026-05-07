package server.service;

import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.UtenteDAO;
import server.model.Utente;

public class UtenteService {

    public static Response login(Request req) {
        try {
            JsonObject p = req.payload;

            String email = p.get("email").getAsString();
            String password = p.get("password").getAsString();

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

    public static Response registra(Request req) {
        try {
            JsonObject p = req.payload;

            String nome = p.get("nome").getAsString();
            String email = p.get("email").getAsString();
            String password = p.get("password").getAsString();
            String ruolo = p.get("ruolo").getAsString();

            boolean ok = UtenteDAO.registra(nome, email, password, ruolo);

            if (!ok)
                return Response.error("Registrazione fallita");

            return Response.ok();

        } catch (Exception e) {
            return Response.error("Errore registrazione: " + e.getMessage());
        }
    }
}
