package client.controller;

import client.gui.RegisterView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

public class RegisterController {

    private final RegisterView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;
    private final Runnable onRegisterSuccess;

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

    private void initHandlers() {

        // TORNA INDIETRO
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        // REGISTRAZIONE
        view.getBtnRegistrati().setOnAction(e -> doRegister());
    }

    private void doRegister() {

        String username = view.getTxtUsername().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String password = view.getTxtPassword().getText().trim();
        String conferma = view.getTxtConferma().getText().trim();

        // VALIDAZIONI BASE
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || conferma.isEmpty()) {
            System.out.println("Compila tutti i campi");
            return;
        }

        if (!password.equals(conferma)) {
            System.out.println("Le password non coincidono");
            return;
        }

        // COSTRUZIONE PARAMETRI JSON
        JsonObject params = new JsonObject();
        params.addProperty("username", username);
        params.addProperty("email", email);
        params.addProperty("password", password);

        Request req = new Request(MessageType.REGISTRAZIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Registrazione completata");
                onRegisterSuccess.run();  // torna al login
            } else {
                System.out.println("Errore registrazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
