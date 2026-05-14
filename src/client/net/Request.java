package client.net;

import com.google.gson.JsonObject;
import common.MessageType;

public class Request {

    private MessageType type;
    private JsonObject payload;

    public Request(MessageType type, JsonObject payload) {
        this.type = type;
        this.payload = payload;
    }

    public String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", type.toString());
        obj.add("payload", payload);
        return obj.toString();
    }
}

