package client.controller;

import client.gui.RecensioniUtenteView;
import client.model.UtenteDTO;
import client.gui.ModificaRecensioneDialog;
import client.gui.RecensioneRow;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

/**
 * Controller dedicato alla gestione delle recensioni scritte dall'utente loggato.
 * Gestisce:
 * - caricamento delle recensioni dal server
 * - modifica di una recensione tramite popup dedicato
 * - eliminazione di una recensione
 * - ritorno alla schermata precedente
 *
 * Aggiorna la UI tramite RecensioniUtenteView e comunica con il server tramite ClientConnection.
 */
public class RecensioniUtenteController {

    private final RecensioniUtenteView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;

    /**
     * Costruisce il controller e avvia subito il caricamento delle recensioni.
     *
     * @param view interfaccia grafica delle recensioni utente
     * @param connection connessione al server
     * @param onGoBack callback per tornare alla schermata precedente
     */
    public RecensioniUtenteController(RecensioniUtenteView view,
                                      ClientConnection connection,
                                      Runnable onGoBack) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;

        initHandlers();
        loadRecensioni();
    }

    /**
     * Inizializza gli handler dei pulsanti:
     * - Indietro → torna alla schermata precedente
     * - Modifica → apre il popup di modifica recensione
     * - Elimina → elimina la recensione selezionata
     */
    private void initHandlers() {

        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        view.getBtnModifica().setOnAction(e -> modificaRecensione());

        view.getBtnElimina().setOnAction(e -> eliminaRecensione());
    }

    /**
     * Gestisce la modifica di una recensione selezionata.
     *
     * Funzionamento:
     * 1. Verifica che una recensione sia selezionata.
     * 2. Apre un popup ModificaRecensioneDialog con i valori attuali.
     * 3. Se confermato, invia una richiesta MODIFICA_RECENSIONE al server.
     * 4. Ricarica la lista delle recensioni.
     */
    private void modificaRecensione() {

        RecensioneRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Nessuna recensione selezionata");
            return;
        }

        // Apri popup
        ModificaRecensioneDialog dialog = new ModificaRecensioneDialog(
                selected.getCommento(),
                selected.getVoto()
        );

        dialog.show();

        if (!dialog.isConfermato()) {
            return;
        }

        // Prepara richiesta
        JsonObject params = new JsonObject();
        params.addProperty("idRecensione", selected.getId());
        params.addProperty("voto", dialog.getVoto());
        params.addProperty("testo", dialog.getCommento());

        Request req = new Request(MessageType.MODIFICA_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Recensione modificata");
                loadRecensioni();
            } else {
                System.out.println("Errore modifica: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }

    /**
     * Carica dal server tutte le recensioni scritte dall'utente loggato.
     * Invia una richiesta VISUALIZZA_RECENSIONI_UTENTE e popola la tabella.
     *
     * Ogni riga contiene:
     * - id recensione
     * - id ristorante
     * - nome ristorante
     * - voto
     * - testo
     * - data
     * - fonte (THEKNIFE o UTENTE)
     */
    public void loadRecensioni() {

        JsonObject params = new JsonObject();
        params.addProperty("idUtente", UtenteDTO.getUtenteLoggato().getId());

        Request req = new Request(MessageType.VISUALIZZA_RECENSIONI_UTENTE, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento recensioni: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("recensioni");

            Platform.runLater(() -> {
                view.getTabella().getItems().clear();

                for (int i = 0; i < arr.size(); i++) {
                    JsonObject r = arr.get(i).getAsJsonObject();

                    RecensioneRow row = new RecensioneRow(
                            r.get("id").getAsInt(),
                            r.get("idRistorante").getAsInt(),
                            r.get("nomeRistorante").getAsString(),
                            r.get("voto").getAsInt(),
                            r.get("testo").getAsString(),
                            r.get("data").getAsString(),
                            r.get("fonte").getAsString()
                    );

                    view.getTabella().getItems().add(row);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }

    /**
     * Elimina la recensione selezionata inviando una richiesta ELIMINA_RECENSIONE.
     * Dopo l'eliminazione, ricarica la lista delle recensioni.
     */
    private void eliminaRecensione() {

        RecensioneRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Nessuna recensione selezionata");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("idRecensione", selected.getId());

        Request req = new Request(MessageType.ELIMINA_RECENSIONE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Recensione eliminata");
                loadRecensioni();
            } else {
                System.out.println("Errore eliminazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Errore di connessione al server");
        }
    }
}
