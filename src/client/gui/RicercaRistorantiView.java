package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RicercaRistorantiView extends BorderPane {

    // ============================
    // CAMPI DI RICERCA
    // ============================

    private final TextField txtRicerca = new TextField();

    private final ComboBox<String> filtroTipoCucina = new ComboBox<>();
    private final ComboBox<String> filtroLocalita = new ComboBox<>();

    private final ComboBox<Integer> filtroPrezzoMin = new ComboBox<>();
    private final ComboBox<Integer> filtroPrezzoMax = new ComboBox<>();

    private final CheckBox chkDelivery = new CheckBox("Delivery");
    private final CheckBox chkPrenotazione = new CheckBox("Prenotazione");

    private final ComboBox<Integer> filtroStelleMin = new ComboBox<>();

    private final Button btnCerca = new Button("Cerca");
    private final Button btnIndietro = new Button("← Indietro");

    private final TableView<RistoranteRow> tabella = new TableView<>();


    public RicercaRistorantiView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // CAMPO TESTO
        // ============================
        txtRicerca.setPromptText("Cerca per nome, indirizzo...");
        txtRicerca.setPrefWidth(200);

        // ============================
        // FILTRO TIPO CUCINA
        // ============================
        filtroTipoCucina.getItems().addAll(
                "Tutte",
                "Creative, Contemporary",
                "Modern Cuisine",
                "Italian Contemporary",
                "Japanese Contemporary",
                "Seafood",
                "Traditional Cuisine",
                "French",
                "Mediterranean Cuisine",
                "Regional Cuisine",
                "Asian Contemporary",
                "Fusion",
                "Steakhouse",
                "Vegetarian",
                "European Contemporary",
                "Modern French",
                "Classic Cuisine"
        );
        filtroTipoCucina.setValue("Tutte");

        // ============================
        // FILTRO LOCALITÀ
        // ============================
        filtroLocalita.getItems().addAll(
                "Tutte",
                "Italia",
                "Francia",
                "Austria",
                "Giappone",
                "Spagna",
                "Germania",
                "Regno Unito"
        );
        filtroLocalita.setValue("Tutte");

        // ============================
        // FILTRO PREZZO
        // ============================
        filtroPrezzoMin.getItems().addAll(1, 2, 3, 4, 5);
        filtroPrezzoMax.getItems().addAll(1, 2, 3, 4, 5);

        filtroPrezzoMin.setValue(1);
        filtroPrezzoMax.setValue(5);

        // ============================
        // FILTRO STELLE
        // ============================
        filtroStelleMin.getItems().addAll(0, 1, 2, 3, 4, 5);
        filtroStelleMin.setValue(0);

        // ============================
        // BARRA SUPERIORE (RESPONSIVE)
        // ============================
        HBox barra = new HBox(
                10,
                btnIndietro,
                txtRicerca,
                filtroTipoCucina,
                filtroLocalita,
                new Label("€ Min:"), filtroPrezzoMin,
                new Label("€ Max:"), filtroPrezzoMax,
                chkDelivery,
                chkPrenotazione,
                new Label("Stelle ≥"), filtroStelleMin,
                btnCerca
        );

        barra.setPadding(new Insets(10));
        barra.setAlignment(Pos.CENTER_LEFT);

        // ⭐ Se la barra è troppo lunga → scroll orizzontale
        ScrollPane scrollBarra = new ScrollPane(barra);
        scrollBarra.setFitToHeight(true);
        scrollBarra.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollBarra.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.setTop(scrollBarra);

        // ============================
        // TABELLA (ESPANDIBILE)
        // ============================
        TableColumn<RistoranteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());

        TableColumn<RistoranteRow, String> colIndirizzo = new TableColumn<>("Indirizzo");
        colIndirizzo.setCellValueFactory(c -> c.getValue().indirizzoProperty());

        TableColumn<RistoranteRow, String> colTipoCucina = new TableColumn<>("Tipo Cucina");
        colTipoCucina.setCellValueFactory(c -> c.getValue().categoriaProperty());

        tabella.getColumns().addAll(colNome, colIndirizzo, colTipoCucina);
        tabella.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ⭐ La tabella ora si espande a tutto lo schermo
        BorderPane.setMargin(tabella, new Insets(10));
        this.setCenter(tabella);

        // Padding generale
        this.setPadding(new Insets(10));
    }

    // ============================
    // GETTERS
    // ============================

    public TextField getTxtRicerca() { return txtRicerca; }
    public ComboBox<String> getFiltroCategoria() { return filtroTipoCucina; }
    public ComboBox<String> getFiltroLocalita() { return filtroLocalita; }
    public ComboBox<Integer> getFiltroPrezzoMin() { return filtroPrezzoMin; }
    public ComboBox<Integer> getFiltroPrezzoMax() { return filtroPrezzoMax; }
    public CheckBox getChkDelivery() { return chkDelivery; }
    public CheckBox getChkPrenotazione() { return chkPrenotazione; }
    public ComboBox<Integer> getFiltroStelleMin() { return filtroStelleMin; }
    public Button getBtnCerca() { return btnCerca; }
    public Button getBtnIndietro() { return btnIndietro; }
    public TableView<RistoranteRow> getTabella() { return tabella; }
}
