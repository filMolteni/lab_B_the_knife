package client.controller;

import client.gui.PreferitiView;
import client.gui.PreferitoRow;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

import java.util.function.BiConsumer;

public class PreferitiController {

    private final PreferitiView view;
    private final ClientConnection connection;
    private final Runnable onBack;
    private final BiConsumer<Integer, String> onOpenRistorante; // ⭐ NUOVO

    public PreferitiController(PreferitiView view,
                               ClientConnection connection,
                               Runnable onBack,
                               BiConsumer<Integer, String> onOpenRistorante) { // ⭐ NUOVO

        this.view = view;
        this.connection = connection;
        this.onBack = onBack;
        this.onOpenRistorante = onOpenRistorante;

        initHandlers();
        loadPreferiti();
    }

    private void initHandlers() {

        view.getBtnIndietro().setOnAction(e -> onBack.run());

        // ⭐ DOPPIO CLICK → APRI DETTAGLI
        view.getTabella().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                PreferitoRow r = view.getTabella().getSelectionModel().getSelectedItem();
                if (r != null) {
                    onOpenRistorante.accept(r.getId(), r.getFonte()); // ⭐ PASSIAMO ID + FONTE
                }
            }
        });
    }

    private void loadPreferiti() {

        JsonObject params = new JsonObject();
        params.addProperty("idUtente", UtenteDTO.getUtenteLoggato().getId());

        Request req = new Request(MessageType.VISUALIZZA_PREFERITI, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento preferiti: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("preferiti");

            Platform.runLater(() -> {
                view.getTabella().getItems().clear();

                arr.forEach(el -> {
                    JsonObject o = el.getAsJsonObject();

                    view.getTabella().getItems().add(
                        new PreferitoRow(
                            o.get("id").getAsInt(),
                            o.get("nome").getAsString(),
                            o.get("indirizzo").getAsString(),
                            o.get("citta").getAsString(),
                            o.get("nazione").getAsString(),
                            o.get("tipoCucina").getAsString(),
                            o.get("fasciaPrezzo").getAsInt(),
                            o.get("delivery").getAsBoolean(),
                            o.get("prenotazione").getAsBoolean(),
                            o.get("fonte").getAsString() // ⭐ NUOVO
                        )
                    );
                });
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
