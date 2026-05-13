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

    // Callback per cambiare schermata
    private final Runnable onGoSearch;
    private final Runnable onGoPreferiti;
    private final Runnable onGoRecensioni;
    private final Runnable onGoGestione;
    private final Runnable onGoLogin;
    private final Runnable onLogout;

    public HomeController(HomeView view,
                          ClientConnection connection,
                          Runnable onGoSearch,
                          Runnable onGoPreferiti,
                          Runnable onGoRecensioni,
                          Runnable onGoGestione,
                          Runnable onGoLogin,
                          Runnable onLogout) {

        this.view = view;
        this.connection = connection;

        this.onGoSearch = onGoSearch;
        this.onGoPreferiti = onGoPreferiti;
        this.onGoRecensioni = onGoRecensioni;
        this.onGoGestione = onGoGestione;
        this.onGoLogin = onGoLogin;
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
        view.getBtnRecensioni().setOnAction(e -> onGoRecensioni.run());

        // GESTIONE RISTORANTI
        view.getBtnGestione().setOnAction(e -> onGoGestione.run());

        // LOGIN
        view.getBtnLogin().setOnAction(e -> onGoLogin.run());

        // LOGOUT
        view.getBtnLogout().setOnAction(e -> onLogout.run());
    }

    /**
     * Invia una richiesta semplice al server (senza parametri)
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

