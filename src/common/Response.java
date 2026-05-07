package common;

import com.google.gson.JsonObject;

public class Response {

    public boolean success;
    public String message;
    public JsonObject payload;

    private Response(boolean success, String message, JsonObject payload) {
        this.success = success;
        this.message = message;
        this.payload = payload;
    }

    public static Response ok(JsonObject payload) {
        return new Response(true, null, payload);
    }

    public static Response ok() {
        return new Response(true, null, null);
    }

    public static Response error(String message) {
        return new Response(false, message, null);
    }
}
