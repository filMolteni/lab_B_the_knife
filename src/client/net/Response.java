package client.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.MessageType;

public class Response {

    private MessageType type;
    private JsonObject data;   // <-- corrisponde a "payload" del server
    private boolean success;
    private String message;

    // === GETTER ===
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

    // === COMPATIBILITÀ ===
    public boolean isOk() {
        return success;
    }

    public JsonObject getPayload() {
        return data;
    }

    // === PARSING JSON ===
    public static Response fromJson(String json) {
        return new Gson().fromJson(json, Response.class);
    }
}
