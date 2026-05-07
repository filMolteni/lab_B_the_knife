package common;

import com.google.gson.JsonObject;

public class Request {

    public MessageType type;
    public JsonObject payload;

    public Request() {}

    public Request(MessageType type, JsonObject payload) {
        this.type = type;
        this.payload = payload;
    }
}
