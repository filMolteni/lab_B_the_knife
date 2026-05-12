package client.controller;

import client.gui.HomeView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

public class HomeController {

    private final HomeView view;
    private final ClientConnection connection;

    // callback per cambiare schermata
    private final Runnable onGoSearch;
    private final Runnable onGoPreferiti;
    private final Runnable onGoRecensioni;
    private final Runnable onGoGestione;
    private final Runnable onLogout;

    public HomeController(HomeView view,
                          ClientConnection connection,
                          Runnable onGoSearch,
                          Runnable onGoPreferiti,
                          Runnable onGoRecensioni,
                          Runnable onGoGestione,
                          Runnable onLogout) {

        this.view = view;
        this.connection = connection;

        this.onGoSearch = onGoSearch;
        this.onGoPreferiti = onGoPreferiti;
        this.onGoRecensioni = onGoRecensioni;
        this.onGoGestione = onGoGestione;
        this.onLogout = onLogout;

        initHandlers();
    }

    private void initHandlers() {

        // CERCA RISTORANTI
        view.getBtnCerca().setOnAction(e -> onGoSearch.run());

        // PREFERITI
        view.getBtnPreferiti().setOnAction(e -> {
            sendSimple(MessageType.VISUALIZZA_PREFERITI);
            onGoPreferiti.run();
        });

        // RECENSIONI UTENTE
        view.getBtnRecensioni().setOnAction(e -> {
            sendSimple(MessageType.VISUALIZZA_RECENSIONI_GESTORE);
            onGoRecensioni.run();
        });

        // GESTIONE RISTORANTI (solo gestori)
        view.getBtnGestione().setOnAction(e -> {
            sendSimple(MessageType.VISUALIZZA_RIEPILOGO_GESTORE);
            onGoGestione.run();
        });

        // LOGOUT
        view.getBtnLogout().setOnAction(e -> onLogout.run());
    }

    /**
     * Invia una richiesta senza parametri
     */
    private void sendSimple(MessageType type) {
        try {
            JsonObject params = new JsonObject();
            Request req = new Request(type, params);
            Response res = connection.sendRequest(req);

            System.out.println("Risposta server: " + res.getMessage());

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
