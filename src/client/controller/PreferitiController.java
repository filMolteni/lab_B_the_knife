package client.controller;

import client.gui.PreferitiView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;

public class PreferitiController {

    private final PreferitiView view;
    private final ClientConnection connection;
    private final Runnable onBack;

    public PreferitiController(PreferitiView view,
                               ClientConnection connection,
                               Runnable onBack) {

        this.view = view;
        this.connection = connection;
        this.onBack = onBack;

        caricaPreferiti();
        initHandlers();
    }

    private void initHandlers() {
        view.getBtnIndietro().setOnAction(e -> onBack.run());
    }

    private void caricaPreferiti() {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("idUtente", UtenteDTO.getUtenteLoggato().getId());

            Request req = new Request(MessageType.VISUALIZZA_PREFERITI, payload);
            Response res = connection.sendRequest(req);

            if (res == null || !res.isOk()) {
                System.out.println("Errore caricamento preferiti");
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("preferiti");

            view.getTabella().getItems().clear();
            arr.forEach(el -> view.getTabella().getItems().add(el.getAsInt()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
