package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScriviRecensioneView extends BorderPane {

    private ComboBox<Integer> cmbVoto;
    private TextArea txtCommento;
    private Label lblErrore;
    private Button btnInvia;
    private Button btnAnnulla;

    public ScriviRecensioneView() {
        creaLayout();
    }

    private void creaLayout() {

        Label titolo = new Label("Scrivi una Recensione");
        titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        cmbVoto = new ComboBox<>();
        cmbVoto.getItems().addAll(1, 2, 3, 4, 5);
        cmbVoto.setPromptText("Seleziona voto (1-5)");

        txtCommento = new TextArea();
        txtCommento.setPromptText("Scrivi qui la tua recensione...");
        txtCommento.setWrapText(true);
        txtCommento.setPrefHeight(150);

        lblErrore = new Label();
        lblErrore.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        btnInvia = new Button("Invia");
        btnInvia.setPrefWidth(120);

        btnAnnulla = new Button("Annulla");
        btnAnnulla.setPrefWidth(120);

        HBox bottoni = new HBox(20, btnInvia, btnAnnulla);
        bottoni.setAlignment(Pos.CENTER);

        VBox box = new VBox(15, titolo, cmbVoto, txtCommento, lblErrore, bottoni);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);

        this.setCenter(box);
    }

    public ComboBox<Integer> getCmbVoto() { return cmbVoto; }
    public TextArea getTxtCommento() { return txtCommento; }
    public Label getLblErrore() { return lblErrore; }
    public Button getBtnInvia() { return btnInvia; }
    public Button getBtnAnnulla() { return btnAnnulla; }
}
