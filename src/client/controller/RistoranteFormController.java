package client.controller;

import client.gui.RistoranteFormView;
import client.model.UtenteDTO;
import client.net.ClientConnection;
import client.net.Request;
import client.net.Response;
import com.google.gson.JsonObject;
import common.MessageType;

public class RistoranteFormController {

    private final RistoranteFormView view;
    private final ClientConnection connection;
    private final Runnable onSuccess;
    private final Integer idRistorante;

    public RistoranteFormController(RistoranteFormView view,
                                    ClientConnection connection,
                                    Runnable onSuccess,
                                    Integer idRistorante) {

        this.view = view;
        this.connection = connection;
        this.onSuccess = onSuccess;
        this.idRistorante = idRistorante;

        initHandlers();
    }

    private void initHandlers() {

        view.getBtnSalva().setOnAction(e -> salva());

        view.getBtnAnnulla().setOnAction(e -> {
            if (onSuccess != null) onSuccess.run();
        });
    }

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
            params.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId());
            req = new Request(MessageType.AGGIUNGI_RISTORANTE, params);
        } else {
            params.addProperty("id", idRistorante);
            req = new Request(MessageType.MODIFICA_RISTORANTE, params);
        }

        try {
            Response res = connection.sendRequest(req);

            if (res.isSuccess()) {
                System.out.println("Operazione completata");
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
