package client.controller;

import client.gui.RecensioniRistoranteView;
import client.gui.RecensioneRistoranteRow;
import client.net.ClientConnection;
import client.net.Request;
import client.model.UtenteDTO;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

public class RecensioniRistoranteController {

    private final RecensioniRistoranteView view;
    private final ClientConnection connection;
    private final Runnable onClose;

    public RecensioniRistoranteController(RecensioniRistoranteView view,
                                          ClientConnection connection,
                                          Runnable onClose) {

        this.view = view;
        this.connection = connection;
        this.onClose = onClose;

        initHandlers();
    }

    private void initHandlers() {
        view.getBtnChiudi().setOnAction(e -> onClose.run());
    }

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

                arr.forEach(el -> {
                    JsonObject o = el.getAsJsonObject();

                    view.getTabella().getItems().add(
                            new RecensioneRistoranteRow(
                                    o.get("utente").getAsString(),
                                    o.get("voto").getAsInt(),
                                    o.get("commento").getAsString(),
                                    o.get("data").getAsString()
                            )
                    );
                });
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

