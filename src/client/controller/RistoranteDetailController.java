package client.controller;

import client.gui.RistoranteDetailView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

import java.util.function.Consumer;

public class RistoranteDetailController {

    private final RistoranteDetailView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;
    private final Consumer<Integer> onShowRecensioni;
    private final Runnable onScriviRecensione;   // <-- NUOVO

    private int ristoranteId;

    public RistoranteDetailController(RistoranteDetailView view,
                                      ClientConnection connection,
                                      Runnable onGoBack,
                                      Consumer<Integer> onShowRecensioni,
                                      Runnable onScriviRecensione) {   // <-- NUOVO

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;
        this.onShowRecensioni = onShowRecensioni;
        this.onScriviRecensione = onScriviRecensione;   // <-- NUOVO

        initHandlers();
    }

    private void initHandlers() {

        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        view.getBtnRecensioni().setOnAction(e -> onShowRecensioni.accept(ristoranteId));

        //  NUOVO: apre la finestra per scrivere una recensione
        view.getBtnScriviRecensione().setOnAction(e -> onScriviRecensione.run());
    }

    /**
     * Carica i dettagli del ristorante dal server
     */
    public void loadRistorante(int id, boolean isUtente) {

        this.ristoranteId = id;

        JsonObject params = new JsonObject();
        params.addProperty("id", id);

        MessageType type = isUtente
                ? MessageType.VISUALIZZA_RISTORANTE_UTENTE
                : MessageType.VISUALIZZA_RISTORANTE;

        Request req = new Request(type, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento ristorante: " + res.getMessage());
                return;
            }

            JsonObject data = res.getData();

            view.getTxtNome().setText(data.get("nome").getAsString());
            view.getTxtIndirizzo().setText(data.get("indirizzo").getAsString());
            view.getTxtCitta().setText(data.get("citta").getAsString());
            view.getTxtNazione().setText(data.get("nazione").getAsString());
            view.getTxtTipoCucina().setText(data.get("tipo_cucina").getAsString());
            view.getTxtFasciaPrezzo().setText(String.valueOf(data.get("fascia_prezzo").getAsInt()));
            view.getTxtLatitudine().setText(String.valueOf(data.get("latitudine").getAsDouble()));
            view.getTxtLongitudine().setText(String.valueOf(data.get("longitudine").getAsDouble()));

            view.getChkDelivery().setSelected(data.get("delivery").getAsBoolean());
            view.getChkPrenotazione().setSelected(data.get("prenotazione").getAsBoolean());

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
