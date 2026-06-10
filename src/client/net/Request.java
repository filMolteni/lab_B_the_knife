package client.net;

import com.google.gson.JsonObject;
import common.MessageType;

/**
 * Rappresenta una richiesta inviata dal client al server.
 *
 * Ogni richiesta contiene:
 * - un {@link MessageType} che identifica l'operazione da eseguire
 * - un payload JSON con i dati necessari per l'operazione
 *
 * La classe fornisce un metodo {@code toJson()} che converte la richiesta
 * in una stringa JSON pronta per essere inviata tramite socket.
 */
public class Request {

    private MessageType type;
    private JsonObject payload;

    /**
     * Costruisce una richiesta completa.
     *
     * @param type tipo di messaggio/operazione da eseguire
     * @param payload contenuto JSON della richiesta
     */
    public Request(MessageType type, JsonObject payload) {
        this.type = type;
        this.payload = payload;
    }

    /**
     * Converte la richiesta in una stringa JSON serializzabile.
     * Il formato prodotto è:
     *
     * {
     *   "type": "NOME_TIPO",
     *   "payload": { ... }
     * }
     *
     * @return stringa JSON rappresentante la richiesta
     */
    public String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", type.toString());
        obj.add("payload", payload);
        return obj.toString();
    }
}
