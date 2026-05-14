package client.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnection(String host, int port) throws Exception {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Response sendRequest(Request req) throws Exception {
        out.println(req.toJson());
        String line = in.readLine();
        return Response.fromJson(line);
    }

    public void close() throws Exception {
        socket.close();
    }
}
