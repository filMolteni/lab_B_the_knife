package client.controller;

import client.gui.RistoranteDettagliView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;

public class RistoranteDettagliController {

    private final RistoranteDettagliView view;
    private final ClientConnection connection;
    private final Runnable onBack;

    public RistoranteDettagliController(RistoranteDettagliView view,
                                        ClientConnection connection,
                                        int idRistorante,
                                        Runnable onBack) {

        this.view = view;
        this.connection = connection;
        this.onBack = onBack;

        caricaDettagli(idRistorante);
        initHandlers();
    }

    private void initHandlers() {
        view.getBtnIndietro().setOnAction(e -> onBack.run());
    }

    private void caricaDettagli(int idRistorante) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("id", idRistorante);

            // 🔥 usa VISUALIZZA (non VISUALIZZA_RISTORANTE)
            Request req = new Request(MessageType.VISUALIZZA, payload);
            Response res = connection.sendRequest(req);

            if (!res.isOk()) {
                view.getAreaRecensioni().setText("Errore: " + res.getMessage());
                return;
            }

            JsonObject data = res.getData();
            if (data == null) {
                view.getAreaRecensioni().setText("Nessun dato ricevuto dal server.");
                return;
            }

            view.getLblNome().setText(data.get("nome").getAsString());
            view.getLblIndirizzo().setText(data.get("indirizzo").getAsString());
            view.getLblTipoCucina().setText("Tipo cucina: " + data.get("tipo_cucina").getAsString());

            caricaRecensioni(idRistorante);

        } catch (Exception e) {
            e.printStackTrace();
            view.getAreaRecensioni().setText("Errore durante il caricamento dei dettagli.");
        }
    }

    private void caricaRecensioni(int idRistorante) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("idRistorante", idRistorante);

            Request req = new Request(MessageType.VISUALIZZA_RECENSIONI_ANONIME, payload);
            Response res = connection.sendRequest(req);

            if (!res.isOk()) {
                view.getAreaRecensioni().setText("Errore: " + res.getMessage());
                return;
            }

            JsonObject data = res.getData();
            if (data == null) {
                view.getAreaRecensioni().setText("Nessuna recensione disponibile.");
                return;
            }

            JsonArray arr = data.getAsJsonArray("recensioni");
            StringBuilder sb = new StringBuilder();

            arr.forEach(el -> {
                JsonObject r = el.getAsJsonObject();
                sb.append("⭐ ").append(r.get("voto").getAsInt()).append("/5\n");
                sb.append(r.get("testo").getAsString()).append("\n\n");
            });

            view.getAreaRecensioni().setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            view.getAreaRecensioni().setText("Errore durante il caricamento delle recensioni.");
        }
    }
}
