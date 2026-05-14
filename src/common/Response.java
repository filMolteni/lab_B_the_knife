package common;

import com.google.gson.JsonObject;

public class Response {

    public String type;
    public boolean success;
    public String message;
    public JsonObject data;

    public static Response ok() {
        Response r = new Response();
        r.success = true;
        r.message = "";
        r.data = new JsonObject();
        return r;
    }

    public static Response ok(JsonObject data) {
        Response r = new Response();
        r.success = true;
        r.message = "";
        r.data = data;
        return r;
    }

    public static Response error(String msg) {
        Response r = new Response();
        r.success = false;
        r.message = msg;
        r.data = null;
        return r;
    }
}
