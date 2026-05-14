package client.controller;

import client.gui.ScriviRecensioneView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.stage.Stage;

public class ScriviRecensioneController {

    private final ScriviRecensioneView view;
    private final ClientConnection connection;
    private final int idRistorante;
    private final Runnable onSuccess;

    public ScriviRecensioneController(ScriviRecensioneView view,
                                      ClientConnection connection,
                                      int idRistorante,
                                      Runnable onSuccess) {

        this.view = view;
        this.connection = connection;
        this.idRistorante = idRistorante;
        this.onSuccess = onSuccess;

        initHandlers();
    }

    private void initHandlers() {

        view.getBtnInvia().setOnAction(e -> inviaRecensione());

        view.getBtnAnnulla().setOnAction(e -> {
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        });
    }

    private void inviaRecensione() {

        Integer voto = view.getCmbVoto().getValue();
        String commento = view.getTxtCommento().getText().trim();

        if (voto == null) {
            view.getLblErrore().setText("Seleziona un voto.");
            return;
        }

        if (commento.isEmpty()) {
            view.getLblErrore().setText("Il commento non può essere vuoto.");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("idRistorante", idRistorante);
        params.addProperty("idUtente", UtenteDTO.getUtenteLoggato().getId());
        params.addProperty("voto", voto);
        params.addProperty("commento", commento);

        Request req = new Request(MessageType.AGGIUNGI_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                view.getLblErrore().setText(res.getMessage());
                return;
            }

            // chiudi finestra
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();

            // callback
            onSuccess.run();

        } catch (Exception ex) {
            ex.printStackTrace();
            view.getLblErrore().setText("Errore di connessione.");
        }
    }
}
