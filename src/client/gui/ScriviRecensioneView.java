package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * View grafica dedicata alla scrittura di una nuova recensione.
 * Permette all’utente di:
 * - selezionare un voto da 1 a 5
 * - scrivere un commento testuale
 * - visualizzare eventuali errori di validazione
 * - inviare o annullare la recensione
 *
 * La view è strutturata come un BorderPane:
 * - Top: titolo
 * - Center: form con voto, commento e messaggi di errore
 * - Bottom: pulsanti Invia e Annulla
 */
public class ScriviRecensioneView extends BorderPane {

    private ComboBox<Integer> cmbVoto;
    private TextArea txtCommento;
    private Label lblErrore;
    private Button btnInvia;
    private Button btnAnnulla;

    /**
     * Costruisce la schermata e inizializza il layout.
     */
    public ScriviRecensioneView() {
        creaLayout();
    }

    /**
     * Crea l’intero layout grafico:
     * - titolo
     * - combo box per il voto
     * - area di testo per il commento
     * - label per errori
     * - pulsanti Invia e Annulla
     * - scroll pane per rendere la view responsive
     */
    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Scrivi una Recensione");
        titolo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20, 0, 30, 0));
        this.setTop(topBox);

        // ============================
        // FORM CENTRALE
        // ============================
        cmbVoto = new ComboBox<>();
        cmbVoto.getItems().addAll(1, 2, 3, 4, 5);
        cmbVoto.setPromptText("Seleziona voto (1-5)");
        cmbVoto.setPrefWidth(200);

        txtCommento = new TextArea();
        txtCommento.setPromptText("Scrivi qui la tua recensione...");
        txtCommento.setWrapText(true);
        txtCommento.setStyle("-fx-font-size: 14px;");
        txtCommento.setPrefRowCount(8);

        VBox.setVgrow(txtCommento, Priority.ALWAYS);

        lblErrore = new Label();
        lblErrore.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        btnInvia = new Button("Invia");
        btnInvia.setPrefWidth(150);
        btnInvia.setStyle("-fx-font-size: 16px;");

        btnAnnulla = new Button("Annulla");
        btnAnnulla.setPrefWidth(150);
        btnAnnulla.setStyle("-fx-font-size: 16px;");

        HBox bottoni = new HBox(25, btnInvia, btnAnnulla);
        bottoni.setAlignment(Pos.CENTER);
        bottoni.setPadding(new Insets(20));

        VBox contenuto = new VBox(20, cmbVoto, txtCommento, lblErrore, bottoni);
        contenuto.setPadding(new Insets(25));
        contenuto.setAlignment(Pos.TOP_CENTER);

        ScrollPane scroll = new ScrollPane(contenuto);
        scroll.setFitToWidth(true);
        scroll.setPadding(new Insets(10));

        this.setCenter(scroll);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    /** @return ComboBox per la selezione del voto */
    public ComboBox<Integer> getCmbVoto() { return cmbVoto; }

    /** @return area di testo per il commento */
    public TextArea getTxtCommento() { return txtCommento; }

    /** @return label per mostrare errori */
    public Label getLblErrore() { return lblErrore; }

    /** @return pulsante Invia */
    public Button getBtnInvia() { return btnInvia; }

    /** @return pulsante Annulla */
    public Button getBtnAnnulla() { return btnAnnulla; }
}
