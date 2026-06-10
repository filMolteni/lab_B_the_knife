package common;

import com.google.gson.JsonObject;

/**
 * Rappresenta una risposta generica inviata dal server al client.
 *
 * Questa classe è utilizzata lato server per costruire risposte JSON
 * che verranno poi serializzate e inviate tramite socket.
 *
 * Ogni risposta contiene:
 * - {@code type}: tipo di messaggio (stringa, non enum, per compatibilità)
 * - {@code success}: indica se l'operazione è andata a buon fine
 * - {@code message}: messaggio opzionale (es. errore o conferma)
 * - {@code data}: payload JSON con i dati restituiti
 *
 * La classe fornisce metodi statici di utilità:
 * - {@code ok()} → risposta vuota con successo
 * - {@code ok(data)} → risposta con payload
 * - {@code error(msg)} → risposta di errore
 */
public class Response {

    /** Tipo della risposta (stringa per compatibilità con vari client) */
    public String type;

    /** True se l'operazione è andata a buon fine */
    public boolean success;

    /** Messaggio associato alla risposta (errore o conferma) */
    public String message;

    /** Dati JSON restituiti dal server */
    public JsonObject data;

    /**
     * Crea una risposta di successo senza payload.
     *
     * @return oggetto Response con success=true e payload vuoto
     */
    public static Response ok() {
        Response r = new Response();
        r.success = true;
        r.message = "";
        r.data = new JsonObject();
        return r;
    }

    /**
     * Crea una risposta di successo con payload.
     *
     * @param data contenuto JSON da restituire
     * @return oggetto Response con success=true e payload valorizzato
     */
    public static Response ok(JsonObject data) {
        Response r = new Response();
        r.success = true;
        r.message = "";
        r.data = data;
        return r;
    }

    /**
     * Crea una risposta di errore.
     *
     * @param msg messaggio di errore
     * @return oggetto Response con success=false e nessun payload
     */
    public static Response error(String msg) {
        Response r = new Response();
        r.success = false;
        r.message = msg;
        r.data = null;
        return r;
    }
}
