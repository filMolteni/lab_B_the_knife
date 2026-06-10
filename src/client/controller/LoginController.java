package client.controller;

import client.gui.LoginView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

/**
 * Controller della schermata di Login.
 * Gestisce:
 * - l'autenticazione dell'utente
 * - la navigazione verso la registrazione
 * - il ritorno alla schermata precedente
 *
 * Comunica con il server per verificare le credenziali e,
 * in caso di successo, crea l'utente loggato tramite UtenteDTO.
 */
public class LoginController {

    private final LoginView view;
    private final ClientConnection connection;
    private final Runnable onLoginSuccess;
    private final Runnable onGoRegister;
    private final Runnable onGoBack;

    /**
     * Costruisce il controller del login e inizializza gli handler dei pulsanti.
     *
     * @param view interfaccia grafica della schermata di login
     * @param connection connessione al server
     * @param onLoginSuccess callback eseguita quando il login va a buon fine
     * @param onGoRegister callback per aprire la schermata di registrazione
     * @param onGoBack callback per tornare alla schermata precedente
     */
    public LoginController(LoginView view,
                           ClientConnection connection,
                           Runnable onLoginSuccess,
                           Runnable onGoRegister,
                           Runnable onGoBack) {

        this.view = view;
        this.connection = connection;
        this.onLoginSuccess = onLoginSuccess;
        this.onGoRegister = onGoRegister;
        this.onGoBack = onGoBack;

        initHandlers();
        
    }

    /**
     * Inizializza gli handler dei pulsanti:
     * - Accedi → esegue il login
     * - Registrati → apre la schermata di registrazione
     * - Indietro → torna alla schermata precedente
     */
    private void initHandlers() {

        // LOGIN
        view.getBtnAccedi().setOnAction(e -> doLogin());

        // VAI A REGISTRAZIONE
        view.getBtnRegistrati().setOnAction(e -> onGoRegister.run());

        // TORNA INDIETRO
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());
    }

    /**
     * Esegue il login dell'utente.
     *
     * Funzionamento:
     * 1. Legge email e password dalla view.
     * 2. Verifica che i campi non siano vuoti.
     * 3. Invia una richiesta LOGIN al server.
     * 4. Se il server conferma, crea l'utente loggato tramite UtenteDTO.
     * 5. Esegue la callback onLoginSuccess.
     *
     * In caso di errore, stampa un messaggio nella console.
     */
    private void doLogin() {

        String email = view.getTxtEmail().getText().trim();
        String password = view.getTxtPassword().getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Campi vuoti");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("email", email);
        params.addProperty("password", password);

        Request req = new Request(MessageType.LOGIN, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {

                JsonObject p = res.getPayload();

                UtenteDTO.creaUtenteLoggato(
                    p.get("id").getAsInt(),
                    p.get("username").getAsString(),
                    p.get("email").getAsString(),
                    p.get("ruolo").getAsString()
                );

                System.out.println("Login OK: " + UtenteDTO.getUtenteLoggato().getUsername());

                onLoginSuccess.run();

            } else {
                System.out.println("Errore login: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }

}
