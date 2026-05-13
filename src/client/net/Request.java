package client.net;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import common.MessageType;

public class Request {

    private MessageType type;
    private JsonObject params;

    public Request(MessageType type, JsonObject params) {
        this.type = type;
        this.params = params;
    }

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    public JsonObject getParams() { return params; }
    public void setParams(JsonObject params) { this.params = params; }

    public String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", type.toString());
        obj.add("params", params);
        return obj.toString();
    }
}

