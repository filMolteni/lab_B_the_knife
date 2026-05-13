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

    private final Integer idRistorante; // null = aggiunta

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
        String categoria = view.getTxtCategoria().getText().trim();
        String descrizione = view.getTxtDescrizione().getText().trim();

        if (nome.isEmpty() || indirizzo.isEmpty() || categoria.isEmpty()) {
            System.out.println("Campi obbligatori mancanti");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("nome", nome);
        params.addProperty("indirizzo", indirizzo);
        params.addProperty("categoria", categoria);
        params.addProperty("descrizione", descrizione);

        Request req;

        if (idRistorante == null) {
           
            params.addProperty("idGestore", UtenteDTO.getUtenteLoggato().getId());

            req = new Request(MessageType.AGGIUNGI_RISTORANTE, params);
        } else {
            // Modifica
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
