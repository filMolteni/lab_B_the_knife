package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * View grafica dedicata alla ricerca dei ristoranti.
 * Permette all’utente di filtrare i risultati tramite:
 * - testo libero
 * - tipo di cucina
 * - località
 * - fascia di prezzo minima/massima
 * - disponibilità di delivery e prenotazione
 * - numero minimo di stelle
 *
 * La view è strutturata come un BorderPane:
 * - Top: barra dei filtri (scrollabile orizzontalmente)
 * - Center: tabella dei risultati
 */
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

    /**
     * Costruisce la schermata di ricerca e inizializza il layout.
     */
    public RicercaRistorantiView() {
        creaLayout();
    }

    /**
     * Crea l’intero layout grafico:
     * - barra dei filtri (scrollabile)
     * - tabella dei risultati
     * - padding e stile generale
     */
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

        // ⭐ Scroll orizzontale se la barra è troppo lunga
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

        BorderPane.setMargin(tabella, new Insets(10));
        this.setCenter(tabella);

        // Padding generale
        this.setPadding(new Insets(10));
    }

    // ============================
    // GETTERS
    // ============================

    /** @return campo di ricerca testuale */
    public TextField getTxtRicerca() { return txtRicerca; }

    /** @return filtro tipo cucina */
    public ComboBox<String> getFiltroCategoria() { return filtroTipoCucina; }

    /** @return filtro località */
    public ComboBox<String> getFiltroLocalita() { return filtroLocalita; }

    /** @return filtro prezzo minimo */
    public ComboBox<Integer> getFiltroPrezzoMin() { return filtroPrezzoMin; }

    /** @return filtro prezzo massimo */
    public ComboBox<Integer> getFiltroPrezzoMax() { return filtroPrezzoMax; }

    /** @return checkbox delivery */
    public CheckBox getChkDelivery() { return chkDelivery; }

    /** @return checkbox prenotazione */
    public CheckBox getChkPrenotazione() { return chkPrenotazione; }

    /** @return filtro stelle minime */
    public ComboBox<Integer> getFiltroStelleMin() { return filtroStelleMin; }

    /** @return pulsante Cerca */
    public Button getBtnCerca() { return btnCerca; }

    /** @return pulsante Indietro */
    public Button getBtnIndietro() { return btnIndietro; }

    /** @return tabella dei risultati */
    public TableView<RistoranteRow> getTabella() { return tabella; }
}
