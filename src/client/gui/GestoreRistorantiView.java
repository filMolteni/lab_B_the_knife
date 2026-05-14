package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GestoreRistorantiView extends BorderPane {

    private TableView<RistoranteRow> tabella;

    private Button btnAggiungi;
    private Button btnModifica;
    private Button btnElimina;
    private Button btnPulisci;
    private Button btnIndietro;

    public GestoreRistorantiView() {
        creaLayout();
    }

    private void creaLayout() {

        // TITOLO
        Label titolo = new Label("Gestione Ristoranti");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(10, 0, 10, 0));

        btnIndietro = new Button("Indietro");

        HBox topButtons = new HBox(btnIndietro);
        topButtons.setAlignment(Pos.CENTER_LEFT);
        topButtons.setPadding(new Insets(0, 0, 10, 10));

        VBox topBox = new VBox(titolo, topButtons);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // TABELLA
        // ============================
        tabella = new TableView<>();

        TableColumn<RistoranteRow, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        colId.setPrefWidth(60);

        TableColumn<RistoranteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        colNome.setPrefWidth(180);

        TableColumn<RistoranteRow, String> colIndirizzo = new TableColumn<>("Indirizzo");
        colIndirizzo.setCellValueFactory(c -> c.getValue().indirizzoProperty());
        colIndirizzo.setPrefWidth(200);

        TableColumn<RistoranteRow, String> colTipoCucina = new TableColumn<>("Tipo Cucina");
        colTipoCucina.setCellValueFactory(c -> c.getValue().categoriaProperty());
        colTipoCucina.setPrefWidth(150);

        tabella.getColumns().addAll(colId, colNome, colIndirizzo, colTipoCucina);
        tabella.setPrefHeight(350);

        this.setCenter(tabella);

        // ============================
        // BOTTONI
        // ============================
        btnAggiungi = new Button("Aggiungi");
        btnModifica = new Button("Modifica");
        btnElimina = new Button("Elimina");
        btnPulisci = new Button("Pulisci");

        HBox bottoni = new HBox(10, btnAggiungi, btnModifica, btnElimina, btnPulisci);
        bottoni.setAlignment(Pos.CENTER);
        bottoni.setPadding(new Insets(15));

        this.setBottom(bottoni);
    }

    // GETTER
    public TableView<RistoranteRow> getTabella() { return tabella; }

    public Button getBtnAggiungi() { return btnAggiungi; }
    public Button getBtnModifica() { return btnModifica; }
    public Button getBtnElimina() { return btnElimina; }
    public Button getBtnPulisci() { return btnPulisci; }
    public Button getBtnIndietro() { return btnIndietro; }
}
