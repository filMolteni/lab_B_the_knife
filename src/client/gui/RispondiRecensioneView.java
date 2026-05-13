package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RispondiRecensioneView extends BorderPane {

    private Label lblRecensione;
    private TextArea txtRisposta;
    private Button btnInvia;
    private Button btnAnnulla;

    public RispondiRecensioneView() {
        creaLayout();
    }

    private void creaLayout() {

        lblRecensione = new Label("Recensione:");
        lblRecensione.setWrapText(true);
        lblRecensione.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        txtRisposta = new TextArea();
        txtRisposta.setPromptText("Scrivi qui la tua risposta...");
        txtRisposta.setPrefRowCount(5);

        VBox center = new VBox(15, lblRecensione, txtRisposta);
        center.setPadding(new Insets(20));
        this.setCenter(center);

        btnInvia = new Button("Invia risposta");
        btnAnnulla = new Button("Annulla");

        HBox buttons = new HBox(15, btnInvia, btnAnnulla);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        this.setBottom(buttons);
    }

    public void setRecensioneText(String testo) {
        lblRecensione.setText("Recensione: " + testo);
    }

    public TextArea getTxtRisposta() { return txtRisposta; }
    public Button getBtnInvia() { return btnInvia; }
    public Button getBtnAnnulla() { return btnAnnulla; }
}
