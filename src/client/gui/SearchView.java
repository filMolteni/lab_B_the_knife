package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SearchView extends BorderPane {

    private TextField txtSearch;
    private ComboBox<String> filtroTipoCucina;
    private Button btnCerca;
    private Button btnIndietro;

    private TableView<RistoranteRow> tabella;

    public SearchView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Cerca Ristoranti");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(10, 0, 20, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);

        // ============================
        // BARRA DI RICERCA
        // ============================
        txtSearch = new TextField();
        txtSearch.setPromptText("Inserisci nome o indirizzo...");

        filtroTipoCucina = new ComboBox<>();
        filtroTipoCucina.getItems().addAll(
                "Tutte",
                "Italiano",
                "Pizza",
                "Giapponese",
                "Burger",
                "Cinese",
                "Messicano"
        );
        filtroTipoCucina.setValue("Tutte");

        btnCerca = new Button("Cerca");
        btnCerca.setPrefWidth(100);

        HBox searchBox = new HBox(10, txtSearch, filtroTipoCucina, btnCerca);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10));

        VBox topContainer = new VBox(topBox, searchBox);
        topContainer.setAlignment(Pos.CENTER);

        this.setTop(topContainer);

        // ============================
        // TABELLA RISULTATI
        // ============================
        tabella = new TableView<>();

        TableColumn<RistoranteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        colNome.setPrefWidth(180);

        TableColumn<RistoranteRow, String> colIndirizzo = new TableColumn<>("Indirizzo");
        colIndirizzo.setCellValueFactory(c -> c.getValue().indirizzoProperty());
        colIndirizzo.setPrefWidth(200);

        TableColumn<RistoranteRow, String> colTipoCucina = new TableColumn<>("Tipo Cucina");
        colTipoCucina.setCellValueFactory(c -> c.getValue().categoriaProperty()); // alias di tipoCucina
        colTipoCucina.setPrefWidth(150);

        tabella.getColumns().addAll(colNome, colIndirizzo, colTipoCucina);

        this.setCenter(tabella);

        // ============================
        // PULSANTE INDIETRO
        // ============================
        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(120);

        HBox bottomBox = new HBox(btnIndietro);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));

        this.setBottom(bottomBox);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    // ============================
    // GETTER PER IL CONTROLLER
    // ============================

    public TextField getTxtSearch() { return txtSearch; }
    public ComboBox<String> getFiltroTipoCucina() { return filtroTipoCucina; }
    public Button getBtnCerca() { return btnCerca; }
    public Button getBtnIndietro() { return btnIndietro; }
    public TableView<RistoranteRow> getTabella() { return tabella; }
}
