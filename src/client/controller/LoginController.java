package client.controller;

import client.gui.LoginView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

public class LoginController {

    private final LoginView view;
    private final ClientConnection connection;
    private final Runnable onLoginSuccess;
    private final Runnable onGoRegister;
    private final Runnable onGoBack;

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

    private void initHandlers() {

        // LOGIN
        view.getBtnAccedi().setOnAction(e -> doLogin());

        // VAI A REGISTRAZIONE
        view.getBtnRegistrati().setOnAction(e -> onGoRegister.run());

        // TORNA INDIETRO
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());
    }

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
