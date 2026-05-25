package client.controller;

import client.gui.GestoreRistorantiView;
import client.gui.RistoranteFormView;
import client.gui.RistoranteRow;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

/**
 * Controller dedicato alla gestione dei ristoranti appartenenti a un gestore.
 * Si occupa di:
 * - caricare il riepilogo dei ristoranti dell'utente gestore
 * - aprire i form di aggiunta e modifica
 * - eliminare un ristorante
 * - gestire il doppio click sulla tabella per aprire i dettagli
 *
 * Funziona come ponte tra la view, la connessione al server e le azioni dell’utente.
 */
public class GestoreRistorantiController {

    private final GestoreRistorantiView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;
    private final BiConsumer<Integer, String> onOpenRistorante;

    /**
     * Costruisce il controller e inizializza gli handler dei pulsanti.
     *
     * @param view interfaccia grafica associata alla schermata del gestore
     * @param connection connessione al server
     * @param onGoBack callback eseguita quando l’utente torna indietro
     * @param onOpenRistorante callback per aprire i dettagli di un ristorante (id, fonte)
     */
    public GestoreRistorantiController(GestoreRistorantiView view,
                                       ClientConnection connection,
                                       Runnable onGoBack,
                                       BiConsumer<Integer, String> onOpenRistorante) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;
        this.onOpenRistorante = onOpenRistorante;

        initHandlers();
    }

    /**
     * Inizializza gli handler dei pulsanti e il doppio click sulla tabella.
     * - Aggiungi → apre il form di creazione
     * - Modifica → apre il form precompilato
     * - Elimina → elimina il ristorante selezionato
     * - Indietro → esegue la callback onGoBack
     * - Doppio click sulla tabella → apre i dettagli del ristorante utente
     */
    private void initHandlers() {

        view.getBtnAggiungi().setOnAction(e -> apriFormAggiunta());
        view.getBtnModifica().setOnAction(e -> apriFormModifica());
        view.getBtnElimina().setOnAction(e -> eliminaRistorante());
        view.getBtnIndietro().setOnAction(e -> onGoBack.run());

        
        view.getTabella().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                RistoranteRow r = view.getTabella().getSelectionModel().getSelectedItem();
                if (r != null) {
                    onOpenRistorante.accept(r.getId(), "UTENTE"); // ⭐ SEMPRE UTENTE
                }
            }
        });
    }

    // ============================
    // CARICA LISTA RISTORANTI UTENTE
    // ============================

    /**
     * Carica dal server il riepilogo dei ristoranti appartenenti al gestore loggato.
     * Invia una richiesta VISUALIZZA_RIEPILOGO_GESTORE e aggiorna la tabella.
     */
    public void loadRiepilogo() {

        JsonObject params = new JsonObject();
        params.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId());

        Request req = new Request(MessageType.VISUALIZZA_RIEPILOGO_GESTORE, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento riepilogo: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("ristoranti");

            Platform.runLater(() -> {
                view.getTabella().getItems().clear();

                for (int i = 0; i < arr.size(); i++) {
                    JsonObject r = arr.get(i).getAsJsonObject();

                    String tipo = r.has("tipo_cucina") && !r.get("tipo_cucina").isJsonNull()
                            ? r.get("tipo_cucina").getAsString()
                            : "";

                    RistoranteRow row = new RistoranteRow(
                            r.get("id").getAsInt(),
                            r.get("nome").getAsString(),
                            tipo,
                            r.get("indirizzo").getAsString(),
                            "UTENTE"
                    );

                    view.getTabella().getItems().add(row);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ============================
    // APRI FORM AGGIUNTA
    // ============================

    /**
     * Apre una nuova finestra contenente il form per aggiungere un ristorante.
     * Alla conferma, il form invierà AGGIUNGI_RISTORANTE e ricaricherà il riepilogo.
     */
    private void apriFormAggiunta() {

        RistoranteFormView formView = new RistoranteFormView();

        Stage stage = new Stage();
        stage.setTitle("Aggiungi Ristorante");

        new RistoranteFormController(
                formView,
                connection,
                this::loadRiepilogo,
                null,
                stage
        );

        stage.setScene(new Scene(formView, 600, 600));
        stage.show();
    }

    // ============================
    // APRI FORM MODIFICA
    // ============================

    /**
     * Apre il form di modifica per il ristorante selezionato.
     * 1. Recupera i dettagli completi dal server tramite VISUALIZZA_UTENTE.
     * 2. Precompila il form.
     * 3. Apre la finestra di modifica.
     */
    private void apriFormModifica() {

        RistoranteRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Seleziona un ristorante da modificare");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("id", selected.getId());

        Request req = new Request(MessageType.VISUALIZZA_UTENTE, params);

        Response res;
        try {
            res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento dettagli: " + res.getMessage());
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        JsonObject r = res.getData();

        RistoranteFormView formView = new RistoranteFormView();

        formView.setValues(
                r.get("nome").getAsString(),
                r.get("indirizzo").getAsString(),
                r.get("tipo_cucina").getAsString(),
                r.get("fascia_prezzo").getAsInt(),
                r.get("citta").getAsString(),
                r.get("nazione").getAsString(),
                r.get("latitudine").getAsDouble(),
                r.get("longitudine").getAsDouble(),
                r.get("delivery").getAsBoolean(),
                r.get("prenotazione").getAsBoolean()
        );

        Stage stage = new Stage();
        stage.setTitle("Modifica Ristorante");

        new RistoranteFormController(
                formView,
                connection,
                this::loadRiepilogo,
                selected.getId(),
                stage
        );

        stage.setScene(new Scene(formView, 600, 600));
        stage.show();
    }

    // ============================
    // ELIMINA RISTORANTE UTENTE
    // ============================

    /**
     * Elimina il ristorante selezionato inviando ELIMINA_RISTORANTE al server.
     * Dopo l’eliminazione, ricarica il riepilogo.
     */
    private void eliminaRistorante() {

        RistoranteRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Seleziona un ristorante da eliminare");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("id", selected.getId());

        Request req = new Request(MessageType.ELIMINA_RISTORANTE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Ristorante eliminato");
                loadRiepilogo();
            } else {
                System.out.println("Errore eliminazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
