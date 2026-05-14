package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class RecensioniRistoranteView extends BorderPane {

    private TableView<RecensioneRistoranteRow> tabella;
    private Button btnChiudi;

    public RecensioniRistoranteView() {
        creaLayout();
    }

    private void creaLayout() {

        Label titolo = new Label("Recensioni del Ristorante");
        titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        BorderPane.setAlignment(titolo, Pos.CENTER);
        this.setTop(titolo);
        BorderPane.setMargin(titolo, new Insets(20, 0, 20, 0));

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

        this.setCenter(tabella);

        btnChiudi = new Button("Chiudi");
        HBox box = new HBox(btnChiudi);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));

        this.setBottom(box);
    }

    public TableView<RecensioneRistoranteRow> getTabella() { return tabella; }
    public Button getBtnChiudi() { return btnChiudi; }
}
