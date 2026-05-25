package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * View grafica dedicata alla gestione dei ristoranti da parte del gestore.
 * Mostra:
 * - una tabella con l'elenco dei ristoranti
 * - pulsanti per aggiungere, modificare, eliminare e pulire la selezione
 * - un pulsante per tornare indietro
 *
 * La view è strutturata come un BorderPane:
 * - Top: titolo + pulsante indietro
 * - Center: tabella dei ristoranti
 * - Bottom: pulsanti di gestione
 */
public class GestoreRistorantiView extends BorderPane {

    private TableView<RistoranteRow> tabella;

    private Button btnAggiungi;
    private Button btnModifica;
    private Button btnElimina;
    private Button btnPulisci;
    private Button btnIndietro;

    /**
     * Costruisce la view e inizializza il layout.
     */
    public GestoreRistorantiView() {
        creaLayout();
    }

    /**
     * Crea l'intero layout grafico della schermata:
     * - titolo
     * - tabella dei ristoranti
     * - pulsanti di gestione
     */
    private void creaLayout() {

        // ============================
        // TITOLO + INDIETRO
        // ============================
        Label titolo = new Label("Gestione Ristoranti");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(10, 0, 10, 0));

        btnIndietro = new Button("Indietro");

        HBox topButtons = new HBox(btnIndietro);
        topButtons.setAlignment(Pos.CENTER_LEFT);
        topButtons.setPadding(new Insets(0, 0, 10, 10));

        VBox topBox = new VBox(titolo, topButtons);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));

        this.setTop(topBox);

        // ============================
        // TABELLA (ESPANDIBILE)
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

        // ⭐ Permette alla tabella di espandersi a tutto lo spazio disponibile
        VBox.setVgrow(tabella, Priority.ALWAYS);

        BorderPane.setMargin(tabella, new Insets(10));
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

        // Padding generale
        this.setPadding(new Insets(10));
    }

    // GETTER

    /**
     * @return la tabella dei ristoranti
     */
    public TableView<RistoranteRow> getTabella() { return tabella; }

    /** @return pulsante Aggiungi */
    public Button getBtnAggiungi() { return btnAggiungi; }

    /** @return pulsante Modifica */
    public Button getBtnModifica() { return btnModifica; }

    /** @return pulsante Elimina */
    public Button getBtnElimina() { return btnElimina; }

    /** @return pulsante Pulisci */
    public Button getBtnPulisci() { return btnPulisci; }

    /** @return pulsante Indietro */
    public Button getBtnIndietro() { return btnIndietro; }
}
