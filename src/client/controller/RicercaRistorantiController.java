package client.controller;

import client.gui.RistoranteRow;
import client.gui.RicercaRistorantiView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;

import java.util.function.BiConsumer;

public class RicercaRistorantiController {

    private final RicercaRistorantiView view;
    private final ClientConnection connection;
    private final Runnable onBack;
    private final BiConsumer<Integer, String> onOpenRistorante; 

    public RicercaRistorantiController(RicercaRistorantiView view,
                                       ClientConnection connection,
                                       Runnable onBack,
                                       BiConsumer<Integer, String> onOpenRistorante) { 

        this.view = view;
        this.connection = connection;
        this.onBack = onBack;
        this.onOpenRistorante = onOpenRistorante;

        initHandlers();
    }

    private void initHandlers() {

        // Bottone indietro
        view.getBtnIndietro().setOnAction(e -> onBack.run());

        // Cerca
        view.getBtnCerca().setOnAction(e -> cerca());

        // Doppio click sulla tabella → apri dettagli
        view.getTabella().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                RistoranteRow r = view.getTabella().getSelectionModel().getSelectedItem();
                if (r != null) {
                    onOpenRistorante.accept(r.getId(), r.getFonte()); 
                }
            }
        });
    }

    private void cerca() {

        String query = view.getTxtRicerca().getText().trim();
        String tipoCucina = view.getFiltroCategoria().getValue();
        String localita = view.getFiltroLocalita().getValue();

        int prezzoMin = view.getFiltroPrezzoMin().getValue();
        int prezzoMax = view.getFiltroPrezzoMax().getValue();

        boolean delivery = view.getChkDelivery().isSelected();
        boolean prenotazione = view.getChkPrenotazione().isSelected();

        int stelleMin = view.getFiltroStelleMin().getValue();

        JsonObject payload = new JsonObject();
        payload.addProperty("query", query);
        payload.addProperty("tipoCucina", tipoCucina);
        payload.addProperty("localita", localita);
        payload.addProperty("prezzoMin", prezzoMin);
        payload.addProperty("prezzoMax", prezzoMax);
        payload.addProperty("delivery", delivery);
        payload.addProperty("prenotazione", prenotazione);
        payload.addProperty("stelleMin", stelleMin);

        try {
            Request req = new Request(MessageType.CERCA_RISTORANTI, payload);
            Response res = connection.sendRequest(req);

            view.getTabella().getItems().clear();

            if (!res.isOk()) {
                System.out.println("Errore: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("ristoranti");

            arr.forEach(el -> {
                JsonObject o = el.getAsJsonObject();

                view.getTabella().getItems().add(
                    new RistoranteRow(
                        o.get("id").getAsInt(),
                        o.get("nome").getAsString(),
                        o.get("tipo_cucina").getAsString(),
                        o.get("indirizzo").getAsString(),
                        o.get("fonte").getAsString()
                    )
                );
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore durante la comunicazione col server");
        }
    }

}
