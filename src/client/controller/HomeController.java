package client.controller;

import client.gui.HomeView;
import client.model.UtenteDTO;
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
    private final Runnable onGoRecensioniRicevute;   // <-- NUOVO
    private final Runnable onGoLogin;
    private final Runnable onLogout;

    public HomeController(HomeView view,
                      ClientConnection connection,
                      Runnable onGoSearch,
                      Runnable onGoPreferiti,
                      Runnable onGoRecensioni,
                      Runnable onGoGestione,
                      Runnable onGoRecensioniRicevute,
                      Runnable onGoLogin,
                      Runnable onLogout){

        this.view = view;
        this.connection = connection;

        this.onGoSearch = onGoSearch;
        this.onGoPreferiti = onGoPreferiti;
        this.onGoRecensioni = onGoRecensioni;
        this.onGoGestione = onGoGestione;
        this.onGoRecensioniRicevute = onGoRecensioniRicevute;   // <-- NUOVO
        this.onGoLogin = onGoLogin;
        this.onLogout = onLogout;

        initHandlers();

        // aggiorna visibilità all'avvio
        view.refreshVisibility();
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

        // GESTIONE RISTORANTI (solo gestore)
        view.getBtnGestione().setOnAction(e -> onGoGestione.run());

        // RECENSIONI RICEVUTE (solo gestore)
        view.getBtnRecensioniRicevute().setOnAction(e -> onGoRecensioniRicevute.run());  // <-- NUOVO

        // LOGIN
        view.getBtnLogin().setOnAction(e -> onGoLogin.run());

        // LOGOUT
        view.getBtnLogout().setOnAction(e -> {
            UtenteDTO.creaUtenteLoggato(0, null, null, null);
            view.refreshVisibility();
            onLogout.run();
        });
    }

    /**
     * Invia una richiesta semplice al server (senza parametri)
     */
    private void sendSimple(MessageType type) {
    try {
        JsonObject params = new JsonObject();

        // Aggiungo automaticamente l'utente loggato
        if (UtenteDTO.getUtenteLoggato() != null) {
            params.addProperty("idUtente", UtenteDTO.getUtenteLoggato().getId());
        }

        Request req = new Request(type, params);
        Response res = connection.sendRequest(req);

        if (res == null) {
            System.out.println("❌ Nessuna risposta dal server");
            return;
        }

        System.out.println("Risposta server: " + res.getMessage());

    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("Errore di connessione al server");
    }
}


    /**
     * Metodo da chiamare quando si torna alla Home
     */
    public void refresh() {
        view.refreshVisibility();
    }
}
