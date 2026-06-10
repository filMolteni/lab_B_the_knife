package client.controller;

import client.gui.RecensioniRistoranteView;
import client.gui.RecensioneRistoranteRow;
import client.net.ClientConnection;
import client.net.Request;
import client.model.UtenteDTO;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

/**
 * Controller dedicato alla visualizzazione delle recensioni ricevute
 * dai ristoranti gestiti dall'utente attualmente loggato.
 *
 * Gestisce:
 * - il caricamento delle recensioni dal server
 * - la popolazione della tabella nella view
 * - la chiusura della schermata tramite callback
 */
public class RecensioniRistoranteController {

    private final RecensioniRistoranteView view;
    private final ClientConnection connection;
    private final Runnable onClose;

    /**
     * Costruisce il controller e inizializza gli handler della view.
     *
     * @param view interfaccia grafica che mostra le recensioni
     * @param connection connessione al server
     * @param onClose callback eseguita quando l'utente chiude la schermata
     */
    public RecensioniRistoranteController(RecensioniRistoranteView view,
                                          ClientConnection connection,
                                          Runnable onClose) {

        this.view = view;
        this.connection = connection;
        this.onClose = onClose;

        initHandlers();
    }

    /**
     * Inizializza gli handler dei pulsanti della view.
     * Attualmente gestisce solo il pulsante "Chiudi".
     */
    private void initHandlers() {
        view.getBtnChiudi().setOnAction(e -> onClose.run());
    }

    /**
     * Carica dal server tutte le recensioni ricevute dai ristoranti
     * appartenenti al gestore loggato.
     *
     * Funzionamento:
     * 1. Invia una richiesta VISUALIZZA_RECENSIONI_GESTORE con l'id del gestore.
     * 2. Riceve un array JSON contenente tutte le recensioni.
     * 3. Popola la tabella della view tramite Platform.runLater().
     *
     * Ogni riga contiene:
     * - id recensione
     * - id ristorante
     * - id utente autore
     * - voto
     * - testo
     * - data
     * - fonte (THEKNIFE o UTENTE)
     */
    public void loadRecensioni() {

        JsonObject params = new JsonObject();
        params.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId()); // ⭐ singleton

        Request req = new Request(MessageType.VISUALIZZA_RECENSIONI_GESTORE, params);

        try {
            Response res = connection.sendRequest(req);

            if (!res.isSuccess()) {
                System.out.println("Errore caricamento recensioni: " + res.getMessage());
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("recensioni");

            Platform.runLater(() -> {
                view.getTabella().getItems().clear();

                arr.forEach(el -> {
                    JsonObject o = el.getAsJsonObject();

                    RecensioneRistoranteRow row = new RecensioneRistoranteRow(
                            o.get("id").getAsInt(),               // ⭐ id recensione
                            o.get("idRistorante").getAsInt(),     // ⭐ serve per sapere quale ristorante
                            o.get("idUtente").getAsInt() + "",    // ⭐ utente come stringa (o puoi fare lookup)
                            o.get("voto").getAsInt(),
                            o.get("testo").getAsString(),
                            o.get("data").getAsString(),
                            o.get("fonte").getAsString()          // ⭐ fondamentale
                    );

                    view.getTabella().getItems().add(row);
                });
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
