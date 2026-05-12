package client.controller;

import client.gui.RecensioniUtenteView;
import client.gui.RecensioneRow;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

public class RecensioniUtenteController {

    private final RecensioniUtenteView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;

    public RecensioniUtenteController(RecensioniUtenteView view,
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

        // MODIFICA (non implementato perché la view non ha campi di modifica)
        view.getBtnModifica().setOnAction(e -> {
            System.out.println("Funzione modifica non implementata nella view");
        });

        // ELIMINA RECENSIONE
        view.getBtnElimina().setOnAction(e -> eliminaRecensione());
    }

    /**
     * Carica le recensioni dell’utente dal server
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

                    RecensioneRow row = new RecensioneRow(
                            r.get("id").getAsInt(),
                            r.get("ristorante").getAsString(),
                            r.get("voto").getAsInt(),
                            r.get("commento").getAsString(),
                            r.get("data").getAsString()
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
     * Elimina la recensione selezionata
     */
    private void eliminaRecensione() {

        RecensioneRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Nessuna recensione selezionata");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("id", selected.getId());

        Request req = new Request(MessageType.ELIMINA_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Recensione eliminata");
                loadRecensioni(); // aggiorna tabella
            } else {
                System.out.println("Errore eliminazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
