package client.controller;

import client.gui.RisposteGestoreView;
import client.gui.RispostaRow;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

import javax.swing.JOptionPane;

public class RisposteGestoreController {

    private final RisposteGestoreView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;

    public RisposteGestoreController(RisposteGestoreView view,
                                     ClientConnection connection,
                                     Runnable onGoBack) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;

        initHandlers();
    }

    private void initHandlers() {

        // TORNA INDIETRO
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        // RISPONDI
        view.getBtnRispondi().setOnAction(e -> rispondi());
    }

    /**
     * Carica tutte le recensioni ricevute dal gestore
     */
    public void loadRecensioni() {

        Request req = new Request(MessageType.VISUALIZZA_RECENSIONI_GESTORE, new JsonObject());

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento recensioni: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("recensioni");

            Platform.runLater(() -> {
                view.getTabella().getItems().clear();

                for (int i = 0; i < arr.size(); i++) {
                    JsonObject r = arr.get(i).getAsJsonObject();

                    RispostaRow row = new RispostaRow(
                            r.get("id").getAsInt(),
                            r.get("utente").getAsString(),
                            r.get("voto").getAsInt(),
                            r.get("commento").getAsString(),
                            r.has("risposta") ? r.get("risposta").getAsString() : ""
                    );

                    view.getTabella().getItems().add(row);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }

    /**
     * Risponde alla recensione selezionata
     */
    private void rispondi() {

        RispostaRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Seleziona una recensione a cui rispondere");
            return;
        }

        // Popup per inserire la risposta
        String risposta = JOptionPane.showInputDialog(
                null,
                "Inserisci la risposta del gestore:",
                "Rispondi alla recensione",
                JOptionPane.PLAIN_MESSAGE
        );

        if (risposta == null || risposta.trim().isEmpty()) {
            System.out.println("Risposta vuota, annullato");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("id", selected.getId());
        params.addProperty("risposta", risposta.trim());

        Request req = new Request(MessageType.RISPONDI_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Risposta inviata correttamente");
                loadRecensioni();
            } else {
                System.out.println("Errore risposta: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
