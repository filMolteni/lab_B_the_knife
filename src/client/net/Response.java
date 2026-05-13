package client.net;

import com.google.gson.JsonObject;
import common.MessageType;

public class Response {

    private MessageType type;
    private JsonObject data;
    private boolean success;
    private String message;

    // === GETTER ORIGINALI ===
    public MessageType getType() {
        return type;
    }

    public JsonObject getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    // === METODI AGGIUNTIVI PER COMPATIBILITÀ ===

    // Il controller usa isOk(), quindi lo aggiungiamo
    public boolean isOk() {
        return success;
    }

    // Il controller usa getPayload(), quindi lo aggiungiamo
    public JsonObject getPayload() {
        return data;
    }
}
