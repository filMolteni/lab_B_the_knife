package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RecensioniRistoranteView extends BorderPane {

    private TableView<RecensioneRistoranteRow> tabella;
    private Button btnChiudi;

    public RecensioniRistoranteView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Recensioni del Ristorante");
        titolo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20, 0, 20, 0));

        this.setTop(topBox);

        // ============================
        // TABELLA (ESPANDIBILE)
        // ============================
        tabella = new TableView<>();

        TableColumn<RecensioneRistoranteRow, String> colUtente = new TableColumn<>("Utente");
        colUtente.setCellValueFactory(c -> c.getValue().utenteProperty());

        TableColumn<RecensioneRistoranteRow, Integer> colVoto = new TableColumn<>("Voto");
        colVoto.setCellValueFactory(c -> c.getValue().votoProperty().asObject());

        TableColumn<RecensioneRistoranteRow, String> colCommento = new TableColumn<>("Commento");
        colCommento.setCellValueFactory(c -> c.getValue().commentoProperty());

        TableColumn<RecensioneRistoranteRow, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> c.getValue().dataProperty());

        tabella.getColumns().addAll(colUtente, colVoto, colCommento, colData);
        tabella.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ⭐ La tabella ora si espande a tutto lo spazio disponibile
        BorderPane.setMargin(tabella, new Insets(10));
        this.setCenter(tabella);

        // ============================
        // BOTTONE CHIUDI
        // ============================
        btnChiudi = new Button("Chiudi");
        btnChiudi.setPrefWidth(150);
        btnChiudi.setStyle("-fx-font-size: 16px;");

        HBox bottomBox = new HBox(btnChiudi);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        this.setBottom(bottomBox);

        // Padding generale
        this.setPadding(new Insets(10));
    }

    public TableView<RecensioneRistoranteRow> getTabella() { return tabella; }
    public Button getBtnChiudi() { return btnChiudi; }
}
