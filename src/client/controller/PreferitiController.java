package client.controller;

import client.gui.PreferitiView;
import client.gui.PreferitoRow;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

import java.util.function.BiConsumer;

/**
 * Controller della schermata dei ristoranti preferiti.
 * Gestisce:
 * - il caricamento dei preferiti dal server
 * - la visualizzazione nella tabella
 * - il doppio click per aprire i dettagli del ristorante
 * - il ritorno alla schermata precedente
 *
 * Comunica con il server tramite ClientConnection e aggiorna la UI tramite PreferitiView.
 */
public class PreferitiController {

    private final PreferitiView view;
    private final ClientConnection connection;
    private final Runnable onBack;
    private final BiConsumer<Integer, String> onOpenRistorante; // ⭐ NUOVO

    /**
     * Costruisce il controller dei preferiti e avvia subito il caricamento dei dati.
     *
     * @param view interfaccia grafica dei preferiti
     * @param connection connessione al server
     * @param onBack callback per tornare alla schermata precedente
     * @param onOpenRistorante callback per aprire i dettagli di un ristorante (id, fonte)
     */
    public PreferitiController(PreferitiView view,
                               ClientConnection connection,
                               Runnable onBack,
                               BiConsumer<Integer, String> onOpenRistorante) { // ⭐ NUOVO

        this.view = view;
        this.connection = connection;
        this.onBack = onBack;
        this.onOpenRistorante = onOpenRistorante;

        initHandlers();
        loadPreferiti();
    }

    /**
     * Inizializza gli handler dei pulsanti e il doppio click sulla tabella.
     * - Indietro → torna alla schermata precedente
     * - Doppio click → apre i dettagli del ristorante selezionato
     */
    private void initHandlers() {

        view.getBtnIndietro().setOnAction(e -> onBack.run());

        // ⭐ DOPPIO CLICK → APRI DETTAGLI
        view.getTabella().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                PreferitoRow r = view.getTabella().getSelectionModel().getSelectedItem();
                if (r != null) {
                    onOpenRistorante.accept(r.getId(), r.getFonte()); // ⭐ PASSIAMO ID + FONTE
                }
            }
        });
    }

    /**
     * Carica dal server la lista dei ristoranti preferiti dell'utente loggato.
     * Invia una richiesta VISUALIZZA_PREFERITI e aggiorna la tabella.
     *
     * L'aggiornamento della UI avviene tramite Platform.runLater().
     */
    private void loadPreferiti() {

        JsonObject params = new JsonObject();
        params.addProperty("idUtente", UtenteDTO.getUtenteLoggato().getId());

        Request req = new Request(MessageType.VISUALIZZA_PREFERITI, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento preferiti: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("preferiti");

            Platform.runLater(() -> {
                view.getTabella().getItems().clear();

                arr.forEach(el -> {
                    JsonObject o = el.getAsJsonObject();

                    view.getTabella().getItems().add(
                        new PreferitoRow(
                            o.get("id").getAsInt(),
                            o.get("nome").getAsString(),
                            o.get("indirizzo").getAsString(),
                            o.get("citta").getAsString(),
                            o.get("nazione").getAsString(),
                            o.get("tipoCucina").getAsString(),
                            o.get("fasciaPrezzo").getAsInt(),
                            o.get("delivery").getAsBoolean(),
                            o.get("prenotazione").getAsBoolean(),
                            o.get("fonte").getAsString() // ⭐ NUOVO
                        )
                    );
                });
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
