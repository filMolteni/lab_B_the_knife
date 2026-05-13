package client.controller;

import client.gui.RisposteGestoreView;
import client.gui.RispostaRow;
import client.gui.RispondiRecensioneView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        // APRI FORM RISPOSTA
        view.getBtnRispondi().setOnAction(e -> apriFormRisposta());
    }

    /**
     * Carica tutte le recensioni ricevute dal gestore
     */
    public void loadRecensioni() {

        JsonObject params = new JsonObject();
        params.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId());

        Request req = new Request(MessageType.VISUALIZZA_RECENSIONI_GESTORE, params);

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
                            String.valueOf(r.get("idUtente").getAsInt()), // non hai il nome utente
                            r.get("voto").getAsInt(),
                            r.get("testo").getAsString(),
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
     * Apre la finestra per rispondere alla recensione
     */
    private void apriFormRisposta() {

        RispostaRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Seleziona una recensione a cui rispondere");
            return;
        }

        // VIEW
        RispondiRecensioneView rispostaView = new RispondiRecensioneView();

        // CONTROLLER
        new RispondiRecensioneController(
                rispostaView,
                connection,
                this::loadRecensioni,
                selected.getId(),
                selected.getCommento()
        );

        // MOSTRA FINESTRA
        Stage stage = new Stage();
        stage.setTitle("Rispondi alla recensione");
        stage.setScene(new Scene(rispostaView, 500, 350));
        stage.show();
    }
}
