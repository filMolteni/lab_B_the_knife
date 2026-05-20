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

import java.util.function.BiConsumer;

public class GestoreRistorantiController {

    private final GestoreRistorantiView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;
    private final BiConsumer<Integer, String> onOpenRistorante; 

    public GestoreRistorantiController(GestoreRistorantiView view,
                                       ClientConnection connection,
                                       Runnable onGoBack,
                                       BiConsumer<Integer, String> onOpenRistorante) { 

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;
        this.onOpenRistorante = onOpenRistorante;

        initHandlers();
    }

    private void initHandlers() {

        view.getBtnAggiungi().setOnAction(e -> apriFormAggiunta());
        view.getBtnModifica().setOnAction(e -> apriFormModifica());
        view.getBtnElimina().setOnAction(e -> eliminaRistorante());
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        // ⭐ DOPPIO CLICK → APRI DETTAGLI RISTORANTE UTENTE
        view.getTabella().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                RistoranteRow r = view.getTabella().getSelectionModel().getSelectedItem();
                if (r != null) {
                    onOpenRistorante.accept(r.getId(), "UTENTE"); // ⭐ SEMPRE UTENTE
                }
            }
        });
    }

    // ============================
    // CARICA LISTA RISTORANTI UTENTE
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

                String tipo = r.has("tipo_cucina") && !r.get("tipo_cucina").isJsonNull()
                        ? r.get("tipo_cucina").getAsString()
                        : "";

                RistoranteRow row = new RistoranteRow(
                        r.get("id").getAsInt(),
                        r.get("nome").getAsString(),
                        tipo,
                        r.get("indirizzo").getAsString(),
                        "UTENTE"
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

        Stage stage = new Stage();
        stage.setTitle("Aggiungi Ristorante");

        new RistoranteFormController(
                formView,
                connection,
                this::loadRiepilogo,
                null,
                stage
        );

        stage.setScene(new Scene(formView, 600, 600));
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

            // 1️⃣ CHIEDO I DETTAGLI COMPLETI
            JsonObject params = new JsonObject();
            params.addProperty("id", selected.getId());

            Request req = new Request(MessageType.VISUALIZZA_UTENTE, params);

            Response res;
            try {
                res = connection.sendRequest(req);

                if (!res.isSuccess()) {
                    System.out.println("Errore caricamento dettagli: " + res.getMessage());
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            JsonObject r = res.getData();

            // 2️⃣ APRO IL FORM PRECOMPILATO
            RistoranteFormView formView = new RistoranteFormView();

            formView.setValues(
                    r.get("nome").getAsString(),
                    r.get("indirizzo").getAsString(),
                    r.get("tipo_cucina").getAsString(),
                    r.get("fascia_prezzo").getAsInt(),
                    r.get("citta").getAsString(),
                    r.get("nazione").getAsString(),
                    r.get("latitudine").getAsDouble(),
                    r.get("longitudine").getAsDouble(),
                    r.get("delivery").getAsBoolean(),
                    r.get("prenotazione").getAsBoolean()
            );

            // 3️⃣ APRO IL POPUP
            Stage stage = new Stage();
            stage.setTitle("Modifica Ristorante");

            // 4️⃣ QUI PASSO L’ID → IL FORM INVIERÀ MODIFICA_RISTORANTE
            new RistoranteFormController(
                    formView,
                    connection,
                    this::loadRiepilogo,
                    selected.getId(),   // ⭐ ID DEL RISTORANTE DA MODIFICARE
                    stage
            );

            stage.setScene(new Scene(formView, 600, 600));
            stage.show();
        }



    // ============================
    // ELIMINA RISTORANTE UTENTE
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
