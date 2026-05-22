package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RispondiRecensioneView extends BorderPane {

    private Label lblRecensione;
    private TextArea txtRisposta;
    private Button btnInvia;
    private Button btnAnnulla;

    public RispondiRecensioneView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO RECENSIONE
        // ============================
        lblRecensione = new Label("Recensione:");
        lblRecensione.setWrapText(true);
        lblRecensione.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ============================
        // AREA DI TESTO (ESPANDIBILE)
        // ============================
        txtRisposta = new TextArea();
        txtRisposta.setPromptText("Scrivi qui la tua risposta...");
        txtRisposta.setStyle("-fx-font-size: 16px;");
        txtRisposta.setPrefRowCount(8);

        // ⭐ Permette all’area di testo di espandersi
        VBox centerBox = new VBox(20, lblRecensione, txtRisposta);
        centerBox.setPadding(new Insets(30));
        VBox.setVgrow(txtRisposta, Priority.ALWAYS);

        this.setCenter(centerBox);

        // ============================
        // BOTTONI IN BASSO
        // ============================
        btnInvia = new Button("Invia risposta");
        btnAnnulla = new Button("Annulla");

        Button[] buttons = { btnInvia, btnAnnulla };
        for (Button b : buttons) {
            b.setPrefWidth(180);
            b.setStyle("-fx-font-size: 16px;");
        }

        HBox bottomBox = new HBox(20, btnInvia, btnAnnulla);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(25));

        this.setBottom(bottomBox);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    public void setRecensioneText(String testo) {
        lblRecensione.setText("Recensione: " + testo);
    }

    public TextArea getTxtRisposta() { return txtRisposta; }
    public Button getBtnInvia() { return btnInvia; }
    public Button getBtnAnnulla() { return btnAnnulla; }
}
