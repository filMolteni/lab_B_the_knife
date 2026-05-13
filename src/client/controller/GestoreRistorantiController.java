package client.controller;

import client.gui.GestoreRistorantiView;
import client.gui.RistoranteFormView;
import client.gui.RistoranteRow;
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

public class GestoreRistorantiController {

    private final GestoreRistorantiView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;

    public GestoreRistorantiController(GestoreRistorantiView view,
                                       ClientConnection connection,
                                       Runnable onGoBack) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;

        initHandlers();
    }

    private void initHandlers() {

        // AGGIUNGI → apre form vuota
        view.getBtnAggiungi().setOnAction(e -> apriFormAggiunta());

        // MODIFICA → apre form con dati selezionati
        view.getBtnModifica().setOnAction(e -> apriFormModifica());

        // ELIMINA
        view.getBtnElimina().setOnAction(e -> eliminaRistorante());

        // TORNA INDIETRO
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());
    }

    // ============================
    // CARICA LISTA RISTORANTI
    // ============================
    public void loadRiepilogo() {

        JsonObject params = new JsonObject();
        params.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId());

        Request req = new Request(MessageType.VISUALIZZA_RIEPILOGO_GESTORE, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento riepilogo: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("ristoranti");

            Platform.runLater(() -> {
                view.getTabella().getItems().clear();

                for (int i = 0; i < arr.size(); i++) {
                    JsonObject r = arr.get(i).getAsJsonObject();

                    RistoranteRow row = new RistoranteRow(
                            r.get("id").getAsInt(),
                            r.get("nome").getAsString(),
                            r.get("categoria").getAsString(),
                            "" // indirizzo non presente nel riepilogo
                    );

                    view.getTabella().getItems().add(row);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ============================
    // APRI FORM AGGIUNTA
    // ============================
    private void apriFormAggiunta() {

        RistoranteFormView formView = new RistoranteFormView();

        new RistoranteFormController(
                formView,
                connection,
                this::loadRiepilogo,
                null // idRistorante = null → aggiunta
        );

        Stage stage = new Stage();
        stage.setTitle("Aggiungi Ristorante");
        stage.setScene(new Scene(formView, 500, 400));
        stage.show();
    }

    // ============================
    // APRI FORM MODIFICA
    // ============================
    private void apriFormModifica() {

        RistoranteRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Seleziona un ristorante da modificare");
            return;
        }

        RistoranteFormView formView = new RistoranteFormView();

        // Precompila i campi
        formView.setValues(
                selected.getNome(),
                selected.getIndirizzo(),
                selected.getCategoria(),
                "" // descrizione non presente nel riepilogo
        );

        new RistoranteFormController(
                formView,
                connection,
                this::loadRiepilogo,
                selected.getId() // idRistorante → modifica
        );

        Stage stage = new Stage();
        stage.setTitle("Modifica Ristorante");
        stage.setScene(new Scene(formView, 500, 400));
        stage.show();
    }

    // ============================
    // ELIMINA RISTORANTE
    // ============================
    private void eliminaRistorante() {

        RistoranteRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Seleziona un ristorante da eliminare");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("id", selected.getId());

        Request req = new Request(MessageType.ELIMINA_RISTORANTE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Ristorante eliminato");
                loadRiepilogo();
            } else {
                System.out.println("Errore eliminazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
