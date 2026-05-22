package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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

        // ⭐ Permette alla TextArea di espandersi
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

        // ⭐ ScrollPane per rendere tutto responsive
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

    public ComboBox<Integer> getCmbVoto() { return cmbVoto; }
    public TextArea getTxtCommento() { return txtCommento; }
    public Label getLblErrore() { return lblErrore; }
    public Button getBtnInvia() { return btnInvia; }
    public Button getBtnAnnulla() { return btnAnnulla; }
}
