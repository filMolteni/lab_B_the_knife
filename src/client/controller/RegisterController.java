package client.controller;

import client.gui.RegisterView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

/**
 * Controller della schermata di registrazione.
 * Gestisce:
 * - la creazione di un nuovo account utente
 * - la validazione dei campi inseriti
 * - la navigazione verso la schermata precedente
 *
 * Comunica con il server tramite ClientConnection per inviare la richiesta di registrazione.
 */
public class RegisterController {

    private final RegisterView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;
    private final Runnable onRegisterSuccess;

    /**
     * Costruisce il controller della registrazione e inizializza gli handler dei pulsanti.
     *
     * @param view interfaccia grafica della schermata di registrazione
     * @param connection connessione al server
     * @param onGoBack callback per tornare alla schermata precedente
     * @param onRegisterSuccess callback eseguita quando la registrazione va a buon fine
     */
    public RegisterController(RegisterView view,
                              ClientConnection connection,
                              Runnable onGoBack,
                              Runnable onRegisterSuccess) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;
        this.onRegisterSuccess = onRegisterSuccess;

        initHandlers();
    }

    /**
     * Inizializza gli handler dei pulsanti:
     * - Indietro → torna alla schermata precedente
     * - Registrati → avvia la procedura di registrazione
     */
    private void initHandlers() {

        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        view.getBtnRegistrati().setOnAction(e -> doRegister());
    }

    /**
     * Esegue la registrazione di un nuovo utente.
     *
     * Funzionamento:
     * 1. Legge i campi dalla view (username, email, password, conferma password).
     * 2. Verifica che nessun campo sia vuoto.
     * 3. Verifica che le password coincidano.
     * 4. Determina il ruolo (CLIENTE o GESTORE).
     * 5. Invia una richiesta REGISTRAZIONE al server.
     * 6. Se la registrazione va a buon fine, esegue la callback onRegisterSuccess.
     *
     * In caso di errore, stampa un messaggio nella console.
     */
    private void doRegister() {

        String username = view.getTxtUsername().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String password = view.getTxtPassword().getText().trim();
        String conferma = view.getTxtConferma().getText().trim();

        String ruolo = view.getChkGestore().isSelected() ? "GESTORE" : "CLIENTE";

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || conferma.isEmpty()) {
            System.out.println("Compila tutti i campi");
            return;
        }

        if (!password.equals(conferma)) {
            System.out.println("Le password non coincidono");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("username", username);
        params.addProperty("email", email);
        params.addProperty("password", password);
        params.addProperty("ruolo", ruolo);

        Request req = new Request(MessageType.REGISTRAZIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Registrazione completata");
                onRegisterSuccess.run();
            } else {
                System.out.println("Errore registrazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
