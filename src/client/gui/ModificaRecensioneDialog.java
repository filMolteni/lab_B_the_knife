package client.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModificaRecensioneDialog {

    private final Stage stage;
    private final TextArea txtCommento;
    private final Spinner<Integer> spVoto;
    private boolean confermato = false;

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

    public void show() {
        stage.showAndWait();
    }

    public boolean isConfermato() {
        return confermato;
    }

    public int getVoto() {
        return spVoto.getValue();
    }

    public String getCommento() {
        return txtCommento.getText();
    }
}
