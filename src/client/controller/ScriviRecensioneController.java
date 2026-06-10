package client.controller;

import client.gui.ScriviRecensioneView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.stage.Stage;

/**
 * Controller dedicato alla scrittura di una recensione da parte dell'utente.
 * Gestisce:
 * - la validazione dei campi (voto e commento)
 * - l'invio della recensione al server
 * - la chiusura della finestra
 * - la callback di aggiornamento dopo l'invio
 */
public class ScriviRecensioneController {

    private final ScriviRecensioneView view;
    private final ClientConnection connection;
    private final int idRistorante;
    private final Runnable onSuccess;

    /**
     * Costruisce il controller e inizializza gli handler.
     *
     * @param view interfaccia grafica per scrivere la recensione
     * @param connection connessione al server
     * @param idRistorante id del ristorante a cui associare la recensione
     * @param onSuccess callback eseguita dopo l'invio corretto della recensione
     */
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

    /**
     * Inizializza gli handler dei pulsanti:
     * - Invia → invia la recensione
     * - Annulla → chiude la finestra
     */
    private void initHandlers() {

        view.getBtnInvia().setOnAction(e -> inviaRecensione());

        view.getBtnAnnulla().setOnAction(e -> {
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        });
    }

    /**
     * Invia la recensione al server.
     *
     * Funzionamento:
     * 1. Legge voto e commento dalla view.
     * 2. Valida i campi (voto obbligatorio, commento non vuoto).
     * 3. Prepara il payload JSON.
     * 4. Invia una richiesta AGGIUNGI_RECENSIONE.
     * 5. Se la risposta è positiva:
     *    - chiude la finestra
     *    - esegue la callback onSuccess
     *
     * In caso di errore, mostra il messaggio nella label della view.
     */
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
