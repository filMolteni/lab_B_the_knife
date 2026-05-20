package client.controller;

import client.gui.RecensioniUtenteView;
import client.model.UtenteDTO;
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
        loadRecensioni();
    }

    private void initHandlers() {

        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        view.getBtnModifica().setOnAction(e -> {
            System.out.println("Funzione modifica non implementata nella view");
        });

        view.getBtnElimina().setOnAction(e -> eliminaRecensione());
    }

    public void loadRecensioni() {

        JsonObject params = new JsonObject();
        params.addProperty("idUtente", UtenteDTO.getUtenteLoggato().getId());

        Request req = new Request(MessageType.VISUALIZZA_RECENSIONI_UTENTE, params);

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
                            r.get("idRistorante").getAsInt(),
                            r.get("nomeRistorante").getAsString(),   
                            r.get("voto").getAsInt(),
                            r.get("testo").getAsString(),
                            r.get("data").getAsString(),
                            r.get("fonte").getAsString()
                    );

                    view.getTabella().getItems().add(row);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }

    private void eliminaRecensione() {

        RecensioneRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Nessuna recensione selezionata");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("idRecensione", selected.getId());

        Request req = new Request(MessageType.ELIMINA_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Recensione eliminata");
                loadRecensioni();
            } else {
                System.out.println("Errore eliminazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
