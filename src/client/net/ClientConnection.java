package client.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Gestisce la connessione TCP tra client e server.
 *
 * Questa classe incapsula:
 * - apertura della connessione tramite Socket
 * - invio delle richieste al server in formato JSON
 * - ricezione delle risposte JSON
 * - chiusura della connessione
 *
 * Viene utilizzata dal client per comunicare con il server in modo semplice e centralizzato.
 */
public class ClientConnection {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Crea una nuova connessione TCP verso il server.
     *
     * @param host indirizzo del server
     * @param port porta del server
     * @throws Exception se la connessione fallisce
     */
    public ClientConnection(String host, int port) throws Exception {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Invia una richiesta al server e attende la risposta.
     *
     * @param req oggetto Request da inviare
     * @return oggetto Response ricevuto dal server
     * @throws Exception se si verifica un errore di comunicazione
     */
    public Response sendRequest(Request req) throws Exception {
        out.println(req.toJson());
        String line = in.readLine();
        return Response.fromJson(line);
    }

    /**
     * Chiude la connessione TCP.
     *
     * @throws Exception se la chiusura fallisce
     */
    public void close() throws Exception {
        socket.close();
    }
}
