package server.service;

import com.google.gson.JsonObject;
import common.Request;
import common.Response;
import server.dao.UtenteDAO;
import server.model.Utente;

/**
 * Service dedicato alla gestione degli utenti.
 *
 * Gestisce:
 * - login
 * - registrazione
 *
 * Ogni metodo:
 * - legge i parametri dal payload della {@link Request}
 * - interagisce con il {@link UtenteDAO}
 * - costruisce una {@link Response} JSON per il client
 */
public class UtenteService {

    // ============================
    // LOGIN
    // ============================

    /**
     * Effettua il login di un utente verificando email e password.
     *
     * Logica:
     * 1. Legge email e password dal payload
     * 2. Interroga il {@link UtenteDAO}
     * 3. Se l'utente esiste, restituisce i suoi dati
     * 4. Altrimenti ritorna un errore
     *
     * @param req richiesta contenente email e password
     * @return risposta OK con i dati dell'utente o errore
     */
    public static Response login(Request req) {
        try {
            String email = req.payload.get("email").getAsString();
            String password = req.payload.get("password").getAsString();

            Utente u = UtenteDAO.login(email, password);

            if (u == null)
                return Response.error("Credenziali non valide");

            JsonObject payload = new JsonObject();
            payload.addProperty("id", u.getId());
            payload.addProperty("username", u.getNome());   // il client vuole "username"
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

    /**
     * Registra un nuovo utente.
     *
     * Logica:
     * 1. Legge username, email, password e ruolo dal payload
     * 2. Inserisce l'utente nel DB tramite {@link UtenteDAO}
     * 3. Recupera l'utente appena registrato
     * 4. Restituisce i suoi dati al client
     *
     * @param req richiesta contenente i dati di registrazione
     * @return risposta OK con i dati dell'utente o errore
     */
    public static Response registrati(Request req) {
        try {
            String username = req.payload.get("username").getAsString();
            String email = req.payload.get("email").getAsString();
            String password = req.payload.get("password").getAsString();

            // Ruolo opzionale, default CLIENTE
            String ruolo = req.payload.has("ruolo")
                    ? req.payload.get("ruolo").getAsString()
                    : "CLIENTE";

            boolean ok = UtenteDAO.registrati(username, email, password, ruolo);

            if (!ok)
                return Response.error("Registrazione fallita");

            // Recupera l'utente appena registrato
            Utente u = UtenteDAO.getByEmail(email);

            if (u == null)
                return Response.error("Errore: utente non trovato dopo registrazione");

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
