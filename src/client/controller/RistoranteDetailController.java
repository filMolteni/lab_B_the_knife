package client.controller;

import client.gui.RistoranteDetailView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

public class RistoranteDetailController {

    private final RistoranteDetailView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;
    private final java.util.function.Consumer<Integer> onShowRecensioni;

    private int ristoranteId;

    public RistoranteDetailController(RistoranteDetailView view,
                                      ClientConnection connection,
                                      Runnable onGoBack,
                                      java.util.function.Consumer<Integer> onShowRecensioni) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;
        this.onShowRecensioni = onShowRecensioni;

        initHandlers();
    }

    private void initHandlers() {

        // TORNA INDIETRO
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        // MOSTRA RECENSIONI
        view.getBtnRecensioni().setOnAction(e -> onShowRecensioni.accept(ristoranteId));
    }

    /**
     * Carica i dettagli del ristorante dal server
     */
    public void loadRistorante(int id) {
        this.ristoranteId = id;

        JsonObject params = new JsonObject();
        params.addProperty("id", id);

        Request req = new Request(MessageType.VISUALIZZA_RISTORANTE, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento ristorante: " + res.getMessage());
                return;
            }

            JsonObject data = res.getData();

            view.getTxtNome().setText(data.get("nome").getAsString());
            view.getTxtIndirizzo().setText(data.get("indirizzo").getAsString());
            view.getTxtCategoria().setText(data.get("categoria").getAsString());
            view.getTxtDescrizione().setText(data.get("descrizione").getAsString());

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
