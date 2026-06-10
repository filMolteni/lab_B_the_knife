package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 * View grafica dedicata alla visualizzazione delle recensioni ricevute dal gestore
 * e delle eventuali risposte già fornite.
 *
 * Mostra:
 * - una tabella con utente, voto, commento e risposta del gestore
 * - celle con wrapping per commenti e risposte lunghe
 * - pulsanti per tornare indietro o rispondere alla recensione selezionata
 *
 * La view è strutturata come un BorderPane:
 * - Top: titolo
 * - Center: tabella espandibile
 * - Bottom: pulsanti di azione
 */
public class RisposteGestoreView extends BorderPane {

    private TableView<RispostaRow> tabella;

    private Button btnIndietro;
    private Button btnRispondi;

    /**
     * Costruisce la schermata e inizializza il layout.
     */
    public RisposteGestoreView() {
        creaLayout();
    }

    /**
     * Crea l’intero layout grafico:
     * - titolo
     * - tabella con colonne dinamiche e celle a testo avvolto
     * - pulsanti Indietro e Rispondi
     * - padding e stile generale
     */
    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Recensioni ricevute");
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
        tabella.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<RispostaRow, String> colUtente = new TableColumn<>("Utente");
        colUtente.setCellValueFactory(c -> c.getValue().utenteProperty());

        TableColumn<RispostaRow, Integer> colVoto = new TableColumn<>("Voto");
        colVoto.setCellValueFactory(c -> c.getValue().votoProperty().asObject());

        TableColumn<RispostaRow, String> colCommento = new TableColumn<>("Commento");
        colCommento.setCellValueFactory(c -> c.getValue().commentoProperty());
        colCommento.setCellFactory(getWrappingCellFactory());

        TableColumn<RispostaRow, String> colRisposta = new TableColumn<>("Risposta del Gestore");
        colRisposta.setCellValueFactory(c -> c.getValue().rispostaProperty());
        colRisposta.setCellFactory(getWrappingCellFactory());

        tabella.getColumns().addAll(colUtente, colVoto, colCommento, colRisposta);

        BorderPane.setMargin(tabella, new Insets(10));
        this.setCenter(tabella);

        // ============================
        // BOTTONI IN BASSO
        // ============================
        btnIndietro = new Button("Indietro");
        btnRispondi = new Button("Rispondi");

        Button[] buttons = { btnIndietro, btnRispondi };
        for (Button b : buttons) {
            b.setPrefWidth(150);
            b.setStyle("-fx-font-size: 16px;");
        }

        HBox bottomBox = new HBox(20, btnIndietro, btnRispondi);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(25));

        this.setBottom(bottomBox);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    /**
     * Restituisce una cell factory che crea celle con testo a capo automatico.
     *
     * @return una Callback che genera celle con wrapping
     */
    private Callback<TableColumn<RispostaRow, String>, TableCell<RispostaRow, String>> getWrappingCellFactory() {
        return col -> new TableCell<>() {
            private final Label label = new Label();

            {
                label.setWrapText(true);
                label.setPadding(new Insets(5));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(label);
                }
            }
        };
    }

    // ============================
    // GETTER PER IL CONTROLLER
    // ============================

    /** @return la tabella delle recensioni ricevute */
    public TableView<RispostaRow> getTabella() { return tabella; }

    /** @return pulsante Indietro */
    public Button getBtnIndietro() { return btnIndietro; }

    /** @return pulsante Rispondi */
    public Button getBtnRispondi() { return btnRispondi; }
}
