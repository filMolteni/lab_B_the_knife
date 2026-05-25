package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * View grafica dedicata alla visualizzazione delle recensioni scritte dall’utente.
 * Mostra:
 * - un titolo centrale
 * - una tabella con ristorante, voto, commento e data
 * - pulsanti per tornare indietro, modificare o eliminare una recensione
 *
 * La view è strutturata come un BorderPane:
 * - Top: titolo
 * - Center: tabella delle recensioni
 * - Bottom: pulsanti di gestione
 */
public class RecensioniUtenteView extends BorderPane {

    private TableView<RecensioneRow> tabella;

    private Button btnIndietro;
    private Button btnModifica;
    private Button btnElimina;

    /**
     * Costruisce la schermata e inizializza il layout.
     */
    public RecensioniUtenteView() {
        creaLayout();
    }

    /**
     * Crea l’intero layout grafico:
     * - titolo
     * - tabella con colonne dinamiche
     * - pulsanti di gestione (Indietro, Modifica, Elimina)
     * - padding e stile generale
     */
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

    /** @return la tabella delle recensioni dell’utente */
    public TableView<RecensioneRow> getTabella() { return tabella; }

    /** @return pulsante Indietro */
    public Button getBtnIndietro() { return btnIndietro; }

    /** @return pulsante Modifica */
    public Button getBtnModifica() { return btnModifica; }

    /** @return pulsante Elimina */
    public Button getBtnElimina() { return btnElimina; }
}
