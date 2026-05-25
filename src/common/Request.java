package common;

import com.google.gson.JsonObject;

/**
 * Rappresenta una richiesta generica inviata dal client al server.
 *
 * Questa classe è utilizzata principalmente lato server per deserializzare
 * le richieste JSON ricevute tramite socket. Contiene:
 *
 * - {@link MessageType} type → il tipo di operazione richiesta
 * - {@link JsonObject} payload → i dati necessari per eseguire l'operazione
 *
 * È progettata per essere compatibile con Gson, grazie al costruttore vuoto.
 */
public class Request {

    /** Tipo dell'operazione richiesta (LOGIN, CERCA_RISTORANTI, ecc.) */
    public MessageType type;

    /** Dati JSON associati alla richiesta */
    public JsonObject payload;

    /**
     * Costruttore vuoto richiesto da Gson per la deserializzazione automatica.
     */
    public Request() {}

    /**
     * Costruisce una richiesta completa.
     *
     * @param type tipo di messaggio/operazione
     * @param payload contenuto JSON della richiesta
     */
    public Request(MessageType type, JsonObject payload) {
        this.type = type;
        this.payload = payload;
    }
}
