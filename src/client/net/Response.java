package client.net;

import com.google.gson.JsonObject;
import common.MessageType;

public class Response {

    private MessageType type;
    private JsonObject data;
    private boolean success;
    private String message;

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
}

