package client.controller;

import client.gui.RispondiRecensioneView;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

/**
 * Controller dedicato alla risposta del gestore a una recensione ricevuta.
 * Gestisce:
 * - la visualizzazione del testo della recensione originale
 * - l'invio della risposta al server
 * - la chiusura della finestra tramite callback
 *
 * Comunica con il server tramite ClientConnection e aggiorna la UI tramite RispondiRecensioneView.
 */
public class RispondiRecensioneController {

    private final RispondiRecensioneView view;
    private final ClientConnection connection;
    private final Runnable onSuccess;

    private final int idRecensione;
    private final String testoRecensione;

    /**
     * Costruisce il controller e inizializza la view con il testo della recensione.
     *
     * @param view interfaccia grafica per rispondere alla recensione
     * @param connection connessione al server
     * @param onSuccess callback eseguita quando la risposta viene inviata correttamente
     * @param idRecensione id della recensione a cui rispondere
     * @param testoRecensione testo originale della recensione
     */
    public RispondiRecensioneController(RispondiRecensioneView view,
                                        ClientConnection connection,
                                        Runnable onSuccess,
                                        int idRecensione,
                                        String testoRecensione) {

        this.view = view;
        this.connection = connection;
        this.onSuccess = onSuccess;
        this.idRecensione = idRecensione;
        this.testoRecensione = testoRecensione;

        view.setRecensioneText(testoRecensione);

        initHandlers();
    }

    /**
     * Inizializza gli handler dei pulsanti:
     * - Invia → invia la risposta al server
     * - Annulla → chiude la finestra tramite callback
     */
    private void initHandlers() {

        view.getBtnInvia().setOnAction(e -> inviaRisposta());

        view.getBtnAnnulla().setOnAction(e -> {
            if (onSuccess != null) onSuccess.run();
        });
    }

    /**
     * Invia la risposta del gestore al server.
     *
     * Funzionamento:
     * 1. Legge il testo della risposta dalla view.
     * 2. Verifica che non sia vuoto.
     * 3. Invia una richiesta RISPONDI_RECENSIONE con idRecensione e risposta.
     * 4. Se il server conferma, esegue la callback onSuccess.
     *
     * In caso di errore, stampa un messaggio nella console.
     */
    private void inviaRisposta() {

        String risposta = view.getTxtRisposta().getText().trim();

        if (risposta.isEmpty()) {
            System.out.println("La risposta non può essere vuota");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("idRecensione", idRecensione);
        params.addProperty("risposta", risposta);

        Request req = new Request(MessageType.RISPONDI_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Risposta inviata");
                onSuccess.run();
            } else {
                System.out.println("Errore: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione");
        }
    }
}
