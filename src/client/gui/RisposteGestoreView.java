package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(20, 0, 10, 0));

        HBox topBox = new HBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // TABELLA
        // ============================
        tabella = new TableView<>();
        tabella.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // UTENTE
        TableColumn<RispostaRow, String> colUtente = new TableColumn<>("Utente");
        colUtente.setCellValueFactory(c -> c.getValue().utenteProperty());
        colUtente.setMinWidth(100);

        // VOTO
        TableColumn<RispostaRow, Integer> colVoto = new TableColumn<>("Voto");
        colVoto.setCellValueFactory(c -> c.getValue().votoProperty().asObject());
        colVoto.setMinWidth(60);

        // COMMENTO (con wrapping)
        TableColumn<RispostaRow, String> colCommento = new TableColumn<>("Commento");
        colCommento.setCellValueFactory(c -> c.getValue().commentoProperty());
        colCommento.setMinWidth(250);
        colCommento.setCellFactory(getWrappingCellFactory());

        // RISPOSTA (con wrapping)
        TableColumn<RispostaRow, String> colRisposta = new TableColumn<>("Risposta del Gestore");
        colRisposta.setCellValueFactory(c -> c.getValue().rispostaProperty());
        colRisposta.setMinWidth(250);
        colRisposta.setCellFactory(getWrappingCellFactory());

        tabella.getColumns().addAll(colUtente, colVoto, colCommento, colRisposta);

        this.setCenter(tabella);

        // ============================
        // BOTTONI
        // ============================
        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(120);

        btnRispondi = new Button("Rispondi");
        btnRispondi.setPrefWidth(120);

        HBox bottomBox = new HBox(20, btnIndietro, btnRispondi);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        this.setBottom(bottomBox);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    /**
     * Crea celle che fanno wrapping del testo
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
