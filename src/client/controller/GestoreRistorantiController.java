package client.controller;

import client.gui.GestoreRistorantiView;
import client.gui.RistoranteRow;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.application.Platform;

public class GestoreRistorantiController {

    private final GestoreRistorantiView view;
    private final ClientConnection connection;
    private final Runnable onGoBack;

    public GestoreRistorantiController(GestoreRistorantiView view,
                                       ClientConnection connection,
                                       Runnable onGoBack) {

        this.view = view;
        this.connection = connection;
        this.onGoBack = onGoBack;

        initHandlers();
    }

    private void initHandlers() {

        // SELEZIONE RIGA → POPOLA FORM
        view.getTabella().setOnMouseClicked(e -> {
            RistoranteRow row = view.getTabella().getSelectionModel().getSelectedItem();
            if (row != null) {
                view.getTxtNome().setText(row.getNome());
                view.getTxtIndirizzo().setText(row.getIndirizzo());
                view.getTxtCategoria().setText(row.getCategoria());
                // La view ha txtDescrizione, ma RistoranteRow NON ha descrizione → lo lasciamo vuoto
                view.getTxtDescrizione().clear();
            }
        });

        // AGGIUNGI
        view.getBtnAggiungi().setOnAction(e -> aggiungiRistorante());

        // MODIFICA
        view.getBtnModifica().setOnAction(e -> modificaRistorante());

        // ELIMINA
        view.getBtnElimina().setOnAction(e -> eliminaRistorante());

        // PULISCI
        view.getBtnPulisci().setOnAction(e -> pulisciForm());
    }

    /**
     * Carica il riepilogo dei ristoranti del gestore
     */
    public void loadRiepilogo() {

        Request req = new Request(MessageType.VISUALIZZA_RIEPILOGO_GESTORE, new JsonObject());

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

                    RistoranteRow row = new RistoranteRow(
                            r.get("id").getAsInt(),
                            r.get("nome").getAsString(),
                            r.get("indirizzo").getAsString(),
                            r.get("categoria").getAsString()
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
     * Aggiunge un nuovo ristorante
     */
    private void aggiungiRistorante() {

        String nome = view.getTxtNome().getText().trim();
        String indirizzo = view.getTxtIndirizzo().getText().trim();
        String categoria = view.getTxtCategoria().getText().trim();

        if (nome.isEmpty() || indirizzo.isEmpty() || categoria.isEmpty()) {
            System.out.println("Compila tutti i campi obbligatori");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("nome", nome);
        params.addProperty("indirizzo", indirizzo);
        params.addProperty("categoria", categoria);

        Request req = new Request(MessageType.AGGIUNGI_RISTORANTE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Ristorante aggiunto correttamente");
                loadRiepilogo();
                pulisciForm();
            } else {
                System.out.println("Errore aggiunta: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Modifica un ristorante selezionato
     */
    private void modificaRistorante() {

        RistoranteRow selected = view.getTabella().getSelectionModel().getSelectedItem();

        if (selected == null) {
            System.out.println("Seleziona un ristorante da modificare");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("id", selected.getId());
        params.addProperty("nome", view.getTxtNome().getText().trim());
        params.addProperty("indirizzo", view.getTxtIndirizzo().getText().trim());
        params.addProperty("categoria", view.getTxtCategoria().getText().trim());

        Request req = new Request(MessageType.MODIFICA_RISTORANTE, params);

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Ristorante modificato");
                loadRiepilogo();
            } else {
                System.out.println("Errore modifica: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Elimina un ristorante selezionato
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
                pulisciForm();
            } else {
                System.out.println("Errore eliminazione: " + res.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Pulisce i campi del form
     */
    private void pulisciForm() {
        view.getTxtNome().clear();
        view.getTxtIndirizzo().clear();
        view.getTxtCategoria().clear();
        view.getTxtDescrizione().clear();
    }
}
