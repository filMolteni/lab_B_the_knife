package client.controller;

import client.gui.SearchView;
import client.gui.RistoranteRow;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

public class SearchController {

    private final SearchView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;
    private final java.util.function.Consumer<Integer> onOpenRistorante;

    public SearchController(SearchView view,
                            ClientConnection connection,
                            Runnable onGoBack,
                            java.util.function.Consumer<Integer> onOpenRistorante) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;
        this.onOpenRistorante = onOpenRistorante;

        initHandlers();
    }

    private void initHandlers() {

        // CERCA
        view.getBtnCerca().setOnAction(e -> doSearch());

        // INDIETRO
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        // DOPPIO CLICK SU RIGA → APRI DETTAGLIO
        view.getTabella().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                RistoranteRow row = view.getTabella().getSelectionModel().getSelectedItem();
                if (row != null) {
                    onOpenRistorante.accept(row.getId());   // <-- APRE DETTAGLI
                }
            }
        });
    }

    private void doSearch() {

        String query = view.getTxtSearch().getText().trim();
        String tipoCucina = view.getFiltroTipoCucina().getValue();

        JsonObject params = new JsonObject();
        params.addProperty("query", query);
        params.addProperty("tipoCucina", tipoCucina);

        Request req = new Request(MessageType.CERCA_RISTORANTI, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore ricerca: " + res.getMessage());
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
                            r.get("tipo_cucina").getAsString(),
                            r.get("indirizzo").getAsString()
                    );

                    view.getTabella().getItems().add(row);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
