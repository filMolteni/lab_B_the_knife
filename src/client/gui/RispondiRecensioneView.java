package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * View grafica dedicata alla risposta del gestore a una recensione.
 * Mostra:
 * - il testo della recensione originale
 * - un'area di testo per scrivere la risposta
 * - pulsanti per inviare o annullare
 *
 * La view è strutturata come un BorderPane:
 * - Top: testo della recensione
 * - Center: area di risposta
 * - Bottom: pulsanti di azione
 */
public class RispondiRecensioneView extends BorderPane {

    private Label lblRecensione;
    private TextArea txtRisposta;
    private Button btnInvia;
    private Button btnAnnulla;

    /**
     * Costruisce la schermata e inizializza il layout.
     */
    public RispondiRecensioneView() {
        creaLayout();
    }

    /**
     * Crea l’intero layout grafico:
     * - titolo della recensione
     * - area di testo espandibile
     * - pulsanti Invia e Annulla
     * - padding e stile generale
     */
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

    /**
     * Imposta il testo della recensione da mostrare in alto.
     *
     * @param testo contenuto della recensione originale
     */
    public void setRecensioneText(String testo) {
        lblRecensione.setText("Recensione: " + testo);
    }

    /** @return area di testo per scrivere la risposta */
    public TextArea getTxtRisposta() { return txtRisposta; }

    /** @return pulsante per inviare la risposta */
    public Button getBtnInvia() { return btnInvia; }

    /** @return pulsante per annullare */
    public Button getBtnAnnulla() { return btnAnnulla; }
}
