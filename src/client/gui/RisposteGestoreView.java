package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class RisposteGestoreView extends BorderPane {

    private TableView<RispostaRow> tabella;

    private Button btnIndietro;
    private Button btnRispondi;

    public RisposteGestoreView() {
        creaLayout();
    }

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

        // UTENTE
        TableColumn<RispostaRow, String> colUtente = new TableColumn<>("Utente");
        colUtente.setCellValueFactory(c -> c.getValue().utenteProperty());

        // VOTO
        TableColumn<RispostaRow, Integer> colVoto = new TableColumn<>("Voto");
        colVoto.setCellValueFactory(c -> c.getValue().votoProperty().asObject());

        // COMMENTO (wrapping)
        TableColumn<RispostaRow, String> colCommento = new TableColumn<>("Commento");
        colCommento.setCellValueFactory(c -> c.getValue().commentoProperty());
        colCommento.setCellFactory(getWrappingCellFactory());

        // RISPOSTA (wrapping)
        TableColumn<RispostaRow, String> colRisposta = new TableColumn<>("Risposta del Gestore");
        colRisposta.setCellValueFactory(c -> c.getValue().rispostaProperty());
        colRisposta.setCellFactory(getWrappingCellFactory());

        tabella.getColumns().addAll(colUtente, colVoto, colCommento, colRisposta);

        // ⭐ La tabella ora si espande a tutto lo schermo
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
     * Celle con wrapping del testo
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

    public TableView<RispostaRow> getTabella() { return tabella; }
    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnRispondi() { return btnRispondi; }
}
