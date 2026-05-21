package client.controller;

import client.gui.RistoranteDettagliView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class RistoranteDettagliController {

    private final RistoranteDettagliView view;
    private final ClientConnection connection;
    private final Runnable onBack;
    private final int idRistorante;
    private final String fonte;

    private boolean gestoreProprietario = false;

    public RistoranteDettagliController(RistoranteDettagliView view,
                                        ClientConnection connection,
                                        int idRistorante,
                                        String fonte,
                                        Runnable onBack) {

        this.view = view;
        this.connection = connection;
        this.onBack = onBack;
        this.idRistorante = idRistorante;
        this.fonte = fonte;

        
        initHandlers();
        controllaPermessiCliente();
        controllaPermessiGestore();
        caricaDettagli(idRistorante);
    }

    private void initHandlers() {
        view.getBtnIndietro().setOnAction(e -> onBack.run());
        view.getBtnPreferiti().setOnAction(e -> aggiungiPreferito());
        view.getBtnScriviRecensione().setOnAction(e -> inviaRecensione());
    }

    // ============================================================
    // ⭐ PERMESSI CLIENTE
    // ============================================================
    private void controllaPermessiCliente() {
        if (!UtenteDTO.getUtenteLoggato().isCliente()) {
            view.nascondiFunzioniCliente();
        }
    }

    // ============================================================
    // ⭐ PERMESSI GESTORE (controllo proprietà)
    // ============================================================
    private void controllaPermessiGestore() {
        try {
            if (!UtenteDTO.getUtenteLoggato().isGestore()) {
                gestoreProprietario = false;
                return;
            }

            int idGestore = UtenteDTO.getUtenteLoggato().getId();

            JsonObject payload = new JsonObject();
            payload.addProperty("idRistorante", idRistorante);
            payload.addProperty("idGestore", idGestore);

            Request req = new Request(MessageType.CONTROLLA_PROPRIETA_RISTORANTE, payload);
            Response res = connection.sendRequest(req);

           

            gestoreProprietario = res != null && res.isOk();

        } catch (Exception e) {
            gestoreProprietario = false;
            e.printStackTrace();
        }
    }

    // ============================================================
    // ⭐ AGGIUNGI AI PREFERITI
    // ============================================================
    private void aggiungiPreferito() {
        try {
            int idUtente = UtenteDTO.getUtenteLoggato().getId();

            JsonObject payload = new JsonObject();
            payload.addProperty("idUtente", idUtente);
            payload.addProperty("idRistorante", idRistorante);
            payload.addProperty("fonte", fonte);

            Request req = new Request(MessageType.AGGIUNGI_PREFERITO, payload);
            Response res = connection.sendRequest(req);

            if (res == null) {
                System.out.println("❌ Nessuna risposta dal server");
                return;
            }

            if (res.isOk()) {
                System.out.println("⭐ Aggiunto ai preferiti!");
            } else {
                System.out.println("Errore: " + res.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // ⭐ INVIA RECENSIONE
    // ============================================================
    private void inviaRecensione() {
        try {
            int idUtente = UtenteDTO.getUtenteLoggato().getId();
            String testo = view.getTxtRecensione().getText().trim();
            int voto = view.getVotoSpinner().getValue();

            if (testo.isEmpty()) {
                System.out.println("Scrivi una recensione prima di inviare.");
                return;
            }

            JsonObject payload = new JsonObject();
            payload.addProperty("idUtente", idUtente);
            payload.addProperty("idRistorante", idRistorante);
            payload.addProperty("voto", voto);
            payload.addProperty("testo", testo);

            Request req = new Request(MessageType.AGGIUNGI_RECENSIONE, payload);
            Response res = connection.sendRequest(req);

            if (res == null) {
                System.out.println("❌ Nessuna risposta dal server");
                return;
            }

            if (res.isOk()) {
                System.out.println("Recensione inviata!");
                caricaRecensioni(idRistorante);
            } else {
                System.out.println("Errore: " + res.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // ⭐ CARICA DETTAGLI RISTORANTE
    // ============================================================
    private void caricaDettagli(int idRistorante) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("id", idRistorante);

            MessageType tipoRichiesta =
                    fonte.equalsIgnoreCase("UTENTE")
                            ? MessageType.VISUALIZZA_UTENTE
                            : MessageType.VISUALIZZA;

            Request req = new Request(tipoRichiesta, payload);
            Response res = connection.sendRequest(req);

            if (res == null || !res.isOk()) {
                System.out.println("Errore caricamento dettagli");
                return;
            }

            JsonObject data = res.getData();

            view.getLblNome().setText(data.get("nome").getAsString());
            view.getLblIndirizzo().setText("Indirizzo: " + data.get("indirizzo").getAsString());
            view.getLblCitta().setText("Città: " + data.get("citta").getAsString());
            view.getLblNazione().setText("Nazione: " + data.get("nazione").getAsString());
            view.getLblTipoCucina().setText("Tipo cucina: " + data.get("tipo_cucina").getAsString());
            view.getLblFasciaPrezzo().setText("Fascia prezzo: " + data.get("fascia_prezzo").getAsInt());
            view.getLblLatitudine().setText("Latitudine: " + data.get("latitudine").getAsDouble());
            view.getLblLongitudine().setText("Longitudine: " + data.get("longitudine").getAsDouble());
            view.getLblDelivery().setText("Delivery: " + (data.get("delivery").getAsBoolean() ? "Sì" : "No"));
            view.getLblPrenotazione().setText("Prenotazione: " + (data.get("prenotazione").getAsBoolean() ? "Sì" : "No"));

            caricaRecensioni(idRistorante);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // ⭐ CARICA RECENSIONI (con card dinamiche)
    // ============================================================
    private void caricaRecensioni(int idRistorante) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("idRistorante", idRistorante);

            boolean utenteLoggato = UtenteDTO.getUtenteLoggato() != null;

            MessageType tipo = utenteLoggato
                    ? MessageType.VISUALIZZA_RECENSIONI_NON_ANONIME
                    : MessageType.VISUALIZZA_RECENSIONI_ANONIME;

            Request req = new Request(tipo, payload);
            Response res = connection.sendRequest(req);

            if (res == null || !res.isOk()) {
                System.out.println("Errore caricamento recensioni");
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("recensioni");

            view.getRecensioniContainer().getChildren().clear();

            arr.forEach(el -> {
                JsonObject r = el.getAsJsonObject();

                int idRecensione = r.get("id").getAsInt();
                String utente = r.has("nomeUtente") && !r.get("nomeUtente").isJsonNull()
                        ? r.get("nomeUtente").getAsString()
                        : "Anonimo";

                int voto = r.get("voto").getAsInt();
                String testo = r.get("testo").getAsString();
                String data = r.get("data").getAsString();

                // ⭐ RISPOSTA (può essere null)
                String risposta = null;
                if (r.has("risposta") && !r.get("risposta").isJsonNull()) {
                    risposta = r.get("risposta").getAsString();
                }

                VBox card = new VBox(5);
                card.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #fafafa;");

                Label lblUtente = new Label("👤 " + utente);
                Label lblVoto = new Label("⭐ " + voto + "/5");
                Label lblTesto = new Label(testo);
                Label lblData = new Label("📅 " + data);

                card.getChildren().addAll(lblUtente, lblVoto, lblTesto, lblData);

                // ⭐ SE ESISTE UNA RISPOSTA → MOSTRALA
                if (risposta != null) {
                    Label lblRisposta = new Label("↳ Risposta del gestore: " + risposta);
                    lblRisposta.setStyle("-fx-text-fill: #444; -fx-padding: 0 0 0 20;");
                    card.getChildren().add(lblRisposta);
                }

                // ⭐ TEXTAREA E BOTTONE SOLO SE:
                // - il gestore è proprietario
                // - NON esiste già una risposta
                if (gestoreProprietario && risposta == null) {

                    TextArea rispostaArea = new TextArea();
                    rispostaArea.setPromptText("Scrivi una risposta...");
                    rispostaArea.setPrefHeight(60);

                    Button btnRispondi = new Button("💬 Rispondi");
                    btnRispondi.setOnAction(e -> inviaRisposta(idRecensione, rispostaArea.getText()));

                    card.getChildren().addAll(rispostaArea, btnRispondi);
                }

                view.getRecensioniContainer().getChildren().add(card);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ============================================================
    // ⭐ INVIA RISPOSTA A UNA RECENSIONE
    // ============================================================
    private void inviaRisposta(int idRecensione, String testo) {
        try {
            if (testo.trim().isEmpty()) {
                System.out.println("Risposta vuota");
                return;
            }

            JsonObject payload = new JsonObject();
            payload.addProperty("idRecensione", idRecensione);
            payload.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId());
            payload.addProperty("testo", testo);

            Request req = new Request(MessageType.RISPONDI_RECENSIONE, payload);
            Response res = connection.sendRequest(req);

            if (res != null && res.isOk()) {
                System.out.println("Risposta inviata!");
                caricaRecensioni(idRistorante);
            } else {
                System.out.println("Errore risposta: " + (res != null ? res.getMessage() : "null"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
