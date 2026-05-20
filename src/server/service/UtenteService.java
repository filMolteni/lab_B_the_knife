package server.service;

import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.UtenteDAO;
import server.model.Utente;

public class UtenteService {

    // ============================
    // LOGIN
    // ============================
    public static Response login(Request req) {
        try {
            String email = req.payload.get("email").getAsString();
            String password = req.payload.get("password").getAsString();

            Utente u = UtenteDAO.login(email, password);

            if (u == null)
                return Response.error("Credenziali non valide");

            JsonObject payload = new JsonObject();
            payload.addProperty("id", u.getId());
            payload.addProperty("username", u.getNome());   // 🔥 il client vuole "username"
            payload.addProperty("email", u.getEmail());
            payload.addProperty("ruolo", u.getRuolo());

            return Response.ok(payload);

        } catch (Exception e) {
            return Response.error("Errore login: " + e.getMessage());
        }
    }

    // ============================
    // REGISTRAZIONE
    // ============================
        public static Response registrati(Request req) {
            try {
                // Lettura parametri dal client
                String username = req.payload.get("username").getAsString();
                String email = req.payload.get("email").getAsString();
                String password = req.payload.get("password").getAsString();

                // Ora leggiamo il ruolo inviato dal client
                String ruolo = req.payload.has("ruolo")
                        ? req.payload.get("ruolo").getAsString()
                        : "CLIENTE"; // fallback di sicurezza

                // Registrazione nel DB
                boolean ok = UtenteDAO.registrati(username, email, password, ruolo);

                if (!ok)
                    return Response.error("Registrazione fallita");

                // Recupera l'utente appena registrato
                Utente u = UtenteDAO.getByEmail(email);

                if (u == null)
                    return Response.error("Errore: utente non trovato dopo registrazione");

                // Costruzione risposta JSON
                JsonObject payload = new JsonObject();
                payload.addProperty("id", u.getId());
                payload.addProperty("username", u.getNome());
                payload.addProperty("email", u.getEmail());
                payload.addProperty("ruolo", u.getRuolo());

                return Response.ok(payload);

            } catch (Exception e) {
                return Response.error("Errore registrazione: " + e.getMessage());
            }
        }

}
