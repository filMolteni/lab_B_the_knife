package client.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.MessageType;

/**
 * Rappresenta una risposta ricevuta dal server.
 *
 * Ogni risposta contiene:
 * - un {@link MessageType} che identifica il tipo di operazione
 * - un oggetto JSON "data" (equivalente al "payload" del server)
 * - un flag booleano "success" che indica l'esito dell'operazione
 * - un messaggio testuale opzionale (es. errori o conferme)
 *
 * La classe fornisce inoltre metodi di compatibilità come {@code isOk()}
 * e {@code getPayload()}, utili per mantenere retrocompatibilità con versioni precedenti.
 *
 * Include un metodo statico {@code fromJson()} per convertire una stringa JSON
 * in un oggetto Response tramite Gson.
 */
public class Response {

    private MessageType type;
    private JsonObject data;   // <-- corrisponde a "payload" del server
    private boolean success;
    private String message;

    // === GETTER ===

    /** @return tipo della risposta */
    public MessageType getType() {
        return type;
    }

    /** @return dati JSON restituiti dal server */
    public JsonObject getData() {
        return data;
    }

    /** @return true se l'operazione è andata a buon fine */
    public boolean isSuccess() {
        return success;
    }

    /** @return messaggio associato alla risposta (errore o conferma) */
    public String getMessage() {
        return message;
    }

    // === COMPATIBILITÀ ===

    /**
     * Alias di {@link #isSuccess()} per compatibilità con versioni precedenti.
     *
     * @return true se la risposta indica successo
     */
    public boolean isOk() {
        return success;
    }

    /**
     * Alias di {@link #getData()} per compatibilità con versioni precedenti.
     *
     * @return payload JSON della risposta
     */
    public JsonObject getPayload() {
        return data;
    }

    // === PARSING JSON ===

    /**
     * Converte una stringa JSON in un oggetto Response tramite Gson.
     *
     * @param json stringa JSON ricevuta dal server
     * @return oggetto Response deserializzato
     */
    public static Response fromJson(String json) {
        return new Gson().fromJson(json, Response.class);
    }
}
