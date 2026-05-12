package client.controller;

import client.gui.PreferitiView;
import client.gui.RistoranteRow;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

public class PreferitiController {

    private final PreferitiView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;

    public PreferitiController(PreferitiView view,
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

        // RIMUOVI DAI PREFERITI
        view.getBtnRimuovi().setOnAction(e -> removeSelected());
    }

    /**
     * Carica i preferiti dal server
     */
    public void loadPreferiti() {

        Request req = new Request(MessageType.VISUALIZZA_PREFERITI, new JsonObject());

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento preferiti: " + res.getMessage());
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
                            r.get("indirizzo").getAsString(),
                            r.get("categoria").getAsString()
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
     * Rimuove il ristorante selezionato dai preferiti
     */
    private void removeSelected() {

        RistoranteRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Nessun ristorante selezionato");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("id", selected.getId());

        Request req = new Request(MessageType.RIMUOVI_PREFERITO, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Ristorante rimosso dai preferiti");
                loadPreferiti(); // aggiorna tabella
            } else {
                System.out.println("Errore rimozione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
