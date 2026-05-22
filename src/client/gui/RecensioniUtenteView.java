package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class RecensioniUtenteView extends BorderPane {

    private TableView<RecensioneRow> tabella;

    private Button btnIndietro;
    private Button btnModifica;
    private Button btnElimina;

    public RecensioniUtenteView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Le Tue Recensioni");
        titolo.setFont(new Font(30));
        titolo.setPadding(new Insets(20, 0, 30, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        this.setTop(topBox);

        // ============================
        // TABELLA (ESPANDIBILE)
        // ============================
        tabella = new TableView<>();

        TableColumn<RecensioneRow, String> colRistorante = new TableColumn<>("Ristorante");
        colRistorante.setCellValueFactory(c -> c.getValue().nomeRistoranteProperty());

        TableColumn<RecensioneRow, Integer> colVoto = new TableColumn<>("Voto");
        colVoto.setCellValueFactory(c -> c.getValue().votoProperty().asObject());

        TableColumn<RecensioneRow, String> colCommento = new TableColumn<>("Commento");
        colCommento.setCellValueFactory(c -> c.getValue().commentoProperty());

        TableColumn<RecensioneRow, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> c.getValue().dataProperty());

        tabella.getColumns().addAll(colRistorante, colVoto, colCommento, colData);
        tabella.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ⭐ La tabella ora si espande a tutto lo spazio disponibile
        BorderPane.setMargin(tabella, new Insets(10));
        this.setCenter(tabella);

        // ============================
        // BOTTONI IN BASSO
        // ============================
        btnIndietro = new Button("Indietro");
        btnModifica = new Button("Modifica");
        btnElimina = new Button("Elimina");

        Button[] buttons = { btnIndietro, btnModifica, btnElimina };
        for (Button b : buttons) {
            b.setPrefWidth(150);
            b.setStyle("-fx-font-size: 16px;");
        }

        HBox bottomBox = new HBox(20, btnIndietro, btnModifica, btnElimina);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        this.setBottom(bottomBox);

        // Padding generale
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    public TableView<RecensioneRow> getTabella() { return tabella; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnModifica() { return btnModifica; }
    public Button getBtnElimina() { return btnElimina; }
}
