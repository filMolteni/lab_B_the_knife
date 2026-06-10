package client.controller;

import client.gui.HomeView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

/**
 * Controller della schermata Home.
 * Gestisce:
 * - la navigazione verso le varie sezioni dell'app
 * - la visibilità dei pulsanti in base allo stato di login
 * - l'invio di richieste semplici al server (es. caricamento preferiti)
 *
 * Funziona come punto centrale di smistamento delle azioni dell'utente.
 */
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

    /**
     * Costruisce il controller della Home e inizializza gli handler dei pulsanti.
     *
     * @param view interfaccia grafica della Home
     * @param connection connessione al server
     * @param onGoSearch callback per aprire la schermata di ricerca
     * @param onGoPreferiti callback per aprire la schermata dei preferiti
     * @param onGoRecensioni callback per aprire la schermata delle recensioni utente
     * @param onGoGestione callback per aprire la gestione ristoranti (solo gestore)
     * @param onGoRecensioniRicevute callback per aprire le recensioni ricevute (solo gestore)
     * @param onGoLogin callback per aprire la schermata di login
     * @param onLogout callback eseguita al logout
     */
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

    /**
     * Inizializza tutti gli handler dei pulsanti della Home.
     * Ogni pulsante richiama la callback associata.
     */
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
     * Invia una richiesta semplice al server (senza parametri aggiuntivi).
     * Aggiunge automaticamente l'id dell'utente loggato se presente.
     *
     * @param type tipo di messaggio da inviare al server
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
     * Aggiorna la visibilità dei pulsanti della Home.
     * Da chiamare quando si ritorna alla schermata principale.
     */
    public void refresh() {
        view.refreshVisibility();
    }
}
