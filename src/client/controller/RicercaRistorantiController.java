package client.controller;

import client.gui.RistoranteRow;
import client.gui.RicercaRistorantiView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;

public class RicercaRistorantiController {

    private final RicercaRistorantiView view;
    private final ClientConnection connection;

    public RicercaRistorantiController(RicercaRistorantiView view, ClientConnection connection) {
        this.view = view;
        this.connection = connection;
        initHandlers();
    }

    private void initHandlers() {

        // CERCA
        view.getBtnCerca().setOnAction(e -> cerca());

        // CLICK SU RISTORANTE
        view.getTabella().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                RistoranteRow r = view.getTabella().getSelectionModel().getSelectedItem();
                if (r != null) visualizzaDettagli(r.getId());
            }
        });
    }

    private void cerca() {

        String query = view.getTxtRicerca().getText().trim();
        String categoria = view.getFiltroCategoria().getValue();

        JsonObject params = new JsonObject();
        params.addProperty("query", query);
        params.addProperty("categoria", categoria);

        Request req = new Request(MessageType.CERCA_RISTORANTI, params);
        Response res = connection.sendRequest(req);

        view.getTabella().getItems().clear();

        if (res.isOk()) {
            JsonArray arr = res.getPayload().getAsJsonArray("ristoranti");

            arr.forEach(el -> {
                JsonObject o = el.getAsJsonObject();
                view.getTabella().getItems().add(
                        new RistoranteRow(
                                o.get("id").getAsInt(),
                                o.get("nome").getAsString(),
                                o.get("indirizzo").getAsString(),
                                o.get("categoria").getAsString()
                        )
                );
            });

        } else {
            System.out.println("Errore: " + res.getMessage());
        }
    }

    private void visualizzaDettagli(int id) {

        JsonObject params = new JsonObject();
        params.addProperty("id", id);

        Request req = new Request(MessageType.VISUALIZZA_RISTORANTE, params);
        Response res = connection.sendRequest(req);

        if (res.isOk()) {
            JsonObject o = res.getPayload();

            System.out.println("=== DETTAGLI RISTORANTE ===");
            System.out.println("Nome: " + o.get("nome").getAsString());
            System.out.println("Indirizzo: " + o.get("indirizzo").getAsString());
            System.out.println("Categoria: " + o.get("categoria").getAsString());
            System.out.println("Descrizione: " + o.get("descrizione").getAsString());

            // AGGIUNGI AI PREFERITI
            aggiungiAiPreferiti(id);

        } else {
            System.out.println("Errore: " + res.getMessage());
        }
    }

    private void aggiungiAiPreferiti(int id) {

        JsonObject params = new JsonObject();
        params.addProperty("id", id);

        Request req = new Request(MessageType.AGGIUNGI_PREFERITO, params);
        Response res = connection.sendRequest(req);

        if (res.isOk()) {
            System.out.println("Aggiunto ai preferiti");
        } else {
            System.out.println("Errore: " + res.getMessage());
        }
    }
}
