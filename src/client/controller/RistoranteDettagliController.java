package client.controller;

import client.gui.RistoranteDettagliView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import common.MessageType;

public class RistoranteDettagliController {

    private final RistoranteDettagliView view;
    private final ClientConnection connection;
    private final Runnable onBack;
    private final int idRistorante;
    private final String fonte; 

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

        caricaDettagli(idRistorante);
        initHandlers();
    }

    private void initHandlers() {

        view.getBtnIndietro().setOnAction(e -> onBack.run());

        view.getBtnPreferiti().setOnAction(e -> aggiungiPreferito());

        view.getBtnScriviRecensione().setOnAction(e -> inviaRecensione());
    }

    // ============================================================
    // ⭐ AGGIUNGI AI PREFERITI (con fonte)
    // ============================================================
    private void aggiungiPreferito() {
        try {
            int idUtente = UtenteDTO.getUtenteLoggato().getId();

            JsonObject payload = new JsonObject();
            payload.addProperty("idUtente", idUtente);
            payload.addProperty("idRistorante", idRistorante);
            payload.addProperty("fonte", fonte); // ⭐ AUTOMATICO

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
    // 📝 INVIA RECENSIONE
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
                System.out.println(" Nessuna risposta dal server");
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
    // 🔍 CARICA DETTAGLI
    // ============================================================
    private void caricaDettagli(int idRistorante) {
        try {
            JsonObject payload = new JsonObject();
            payload.addProperty("id", idRistorante);

            Request req = new Request(MessageType.VISUALIZZA, payload);
            Response res = connection.sendRequest(req);

            if (res == null || !res.isOk()) {
                view.getAreaRecensioni().setText("Errore: " + (res != null ? res.getMessage() : "nessuna risposta"));
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
    // 📜 CARICA RECENSIONI
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
                view.getAreaRecensioni().setText("Errore caricamento recensioni.");
                return;
            }

            JsonArray arr = res.getData().getAsJsonArray("recensioni");
            StringBuilder sb = new StringBuilder();

            arr.forEach(el -> {
                JsonObject r = el.getAsJsonObject();

                // ⭐ Nome utente solo se presente
                if (utenteLoggato && r.has("nomeUtente") && !r.get("nomeUtente").isJsonNull()) {
                    sb.append("👤 ").append(r.get("nomeUtente").getAsString()).append("\n");
                }

                // ⭐ Voto (sempre presente)
                if (r.has("voto")) {
                    sb.append("⭐ ").append(r.get("voto").getAsInt()).append("/5\n");
                }

                // ⭐ Testo (sempre presente)
                if (r.has("testo")) {
                    sb.append(r.get("testo").getAsString()).append("\n");
                }

                // ⭐ Data (potrebbe NON esserci → controllo)
                if (r.has("data") && !r.get("data").isJsonNull()) {
                    sb.append("📅 ").append(r.get("data").getAsString()).append("\n");
                }

                sb.append("\n");
            });

            view.getAreaRecensioni().setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
