package client.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Finestra di dialogo modale per modificare una recensione esistente.
 * Permette all’utente di:
 * - modificare il voto tramite Spinner
 * - modificare il commento tramite TextArea
 * - confermare o annullare l’operazione
 *
 * Il dialog è modale e blocca l’interazione con la finestra principale
 * finché non viene chiuso.
 */
public class ModificaRecensioneDialog {

    private final Stage stage;
    private final TextArea txtCommento;
    private final Spinner<Integer> spVoto;
    private boolean confermato = false;

    /**
     * Costruisce il dialog di modifica recensione.
     *
     * @param commentoIniziale testo iniziale della recensione
     * @param votoIniziale voto iniziale (1–5)
     */
    public ModificaRecensioneDialog(String commentoIniziale, int votoIniziale) {

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Modifica Recensione");

        spVoto = new Spinner<>(1, 5, votoIniziale);
        spVoto.setEditable(true);

        txtCommento = new TextArea(commentoIniziale);
        txtCommento.setPrefRowCount(5);

        Button btnSalva = new Button("Salva");
        Button btnAnnulla = new Button("Annulla");

        btnSalva.setOnAction(e -> {
            confermato = true;
            stage.close();
        });

        btnAnnulla.setOnAction(e -> stage.close());

        VBox root = new VBox(10,
                new Label("Voto:"), spVoto,
                new Label("Commento:"), txtCommento,
                btnSalva, btnAnnulla
        );
        root.setPadding(new Insets(15));

        stage.setScene(new Scene(root, 350, 300));
    }

    /**
     * Mostra il dialog in modalità bloccante.
     */
    public void show() {
        stage.showAndWait();
    }

    /**
     * @return true se l’utente ha premuto “Salva”, false se ha annullato.
     */
    public boolean isConfermato() {
        return confermato;
    }

    /**
     * @return il voto selezionato dall’utente.
     */
    public int getVoto() {
        return spVoto.getValue();
    }

    /**
     * @return il commento modificato inserito dall’utente.
     */
    public String getCommento() {
        return txtCommento.getText();
    }
}
