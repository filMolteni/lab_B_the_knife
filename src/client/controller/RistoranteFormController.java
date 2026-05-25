package client.controller;

import client.gui.RistoranteFormView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.stage.Stage;

/**
 * Controller del form di creazione/modifica ristorante.
 * Gestisce:
 * - la validazione dei campi
 * - l'invio della richiesta di aggiunta o modifica al server
 * - la chiusura della finestra al termine dell'operazione
 *
 * Il comportamento cambia automaticamente in base alla presenza o meno dell'idRistorante.
 */
public class RistoranteFormController {

    private final RistoranteFormView view;
    private final ClientConnection connection;
    private final Runnable onSuccess;
    private final Integer idRistorante;

    private final Stage stage;

    /**
     * Costruisce il controller del form ristorante.
     *
     * @param view interfaccia grafica del form
     * @param connection connessione al server
     * @param onSuccess callback eseguita dopo un salvataggio riuscito
     * @param idRistorante id del ristorante da modificare (null se aggiunta)
     * @param stage finestra del popup da chiudere dopo il salvataggio
     */
    public RistoranteFormController(
            RistoranteFormView view,
            ClientConnection connection,
            Runnable onSuccess,
            Integer idRistorante,
            Stage stage) {

        this.view = view;
        this.connection = connection;
        this.onSuccess = onSuccess;
        this.idRistorante = idRistorante;
        this.stage = stage;

        initHandlers();
    }

    /**
     * Inizializza gli handler dei pulsanti:
     * - Salva → invia i dati al server
     * - Annulla → chiude il form richiamando la callback onSuccess
     */
    private void initHandlers() {

        view.getBtnSalva().setOnAction(e -> salva());

        view.getBtnAnnulla().setOnAction(e -> {
            if (onSuccess != null) onSuccess.run();
        });
    }

    /**
     * Salva i dati del ristorante.
     *
     * Funzionamento:
     * 1. Legge e valida i campi obbligatori.
     * 2. Recupera latitudine e longitudine.
     * 3. Prepara il payload JSON.
     * 4. Se idRistorante è null → aggiunta.
     *    Altrimenti → modifica.
     * 5. Invia la richiesta al server.
     * 6. Se l'operazione va a buon fine:
     *    - esegue la callback onSuccess
     *    - chiude la finestra
     */
    private void salva() {

        String nome = view.getTxtNome().getText().trim();
        String indirizzo = view.getTxtIndirizzo().getText().trim();
        String tipoCucina = view.getTxtTipoCucina().getText().trim();
        String citta = view.getTxtCitta().getText().trim();
        String nazione = view.getTxtNazione().getText().trim();

        if (nome.isEmpty() || indirizzo.isEmpty() || tipoCucina.isEmpty()) {
            System.out.println("Campi obbligatori mancanti");
            return;
        }

        double lat, lon;

        try {
            lat = view.getLatitudine();
            lon = view.getLongitudine();
        } catch (Exception ex) {
            System.out.println("Latitudine/Longitudine non valide");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("nome", nome);
        params.addProperty("indirizzo", indirizzo);
        params.addProperty("tipo_cucina", tipoCucina);
        params.addProperty("fascia_prezzo", view.getFasciaPrezzo());
        params.addProperty("citta", citta);
        params.addProperty("nazione", nazione);
        params.addProperty("latitudine", lat);
        params.addProperty("longitudine", lon);
        params.addProperty("delivery", view.isDelivery());
        params.addProperty("prenotazione", view.isPrenotazione());

        Request req;

        if (idRistorante == null) {
            // AGGIUNTA
            params.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId());
            req = new Request(MessageType.AGGIUNGI_RISTORANTE, params);
        } else {
            // ⭐ MODIFICA
            params.addProperty("id", idRistorante);
            req = new Request(MessageType.MODIFICA_RISTORANTE, params);
        }

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Operazione completata");

                // Aggiorna la tabella principale
                if (onSuccess != null)
                    onSuccess.run();

                // Chiudi il popup
                if (stage != null)
                    stage.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione");
        }
    }
}
