package client.controller;

import client.gui.RispondiRecensioneView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

public class RispondiRecensioneController {

    private final RispondiRecensioneView view;
    private final ClientConnection connection;
    private final Runnable onSuccess;

    private final int idRecensione;
    private final String testoRecensione;

    public RispondiRecensioneController(RispondiRecensioneView view,
                                        ClientConnection connection,
                                        Runnable onSuccess,
                                        int idRecensione,
                                        String testoRecensione) {

        this.view = view;
        this.connection = connection;
        this.onSuccess = onSuccess;
        this.idRecensione = idRecensione;
        this.testoRecensione = testoRecensione;

        view.setRecensioneText(testoRecensione);

        initHandlers();
    }

    private void initHandlers() {

        view.getBtnInvia().setOnAction(e -> inviaRisposta());

        view.getBtnAnnulla().setOnAction(e -> {
            if (onSuccess != null) onSuccess.run();
        });
    }

    private void inviaRisposta() {

        String risposta = view.getTxtRisposta().getText().trim();

        if (risposta.isEmpty()) {
            System.out.println("La risposta non può essere vuota");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("idRecensione", idRecensione);
        params.addProperty("risposta", risposta);

        Request req = new Request(MessageType.RISPONDI_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Risposta inviata");
                onSuccess.run();
            } else {
                System.out.println("Errore: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione");
        }
    }
}
