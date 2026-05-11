package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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
        Label titolo = new Label("Risposte alle Recensioni");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(20, 0, 20, 0));

        HBox topBox = new HBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // TABELLA
        // ============================
        tabella = new TableView<>();

        TableColumn<RispostaRow, String> colUtente = new TableColumn<>("Utente");
        colUtente.setCellValueFactory(c -> c.getValue().utenteProperty());
        colUtente.setPrefWidth(150);

        TableColumn<RispostaRow, Integer> colVoto = new TableColumn<>("Voto");
        colVoto.setCellValueFactory(c -> c.getValue().votoProperty().asObject());
        colVoto.setPrefWidth(60);

        TableColumn<RispostaRow, String> colCommento = new TableColumn<>("Commento");
        colCommento.setCellValueFactory(c -> c.getValue().commentoProperty());
        colCommento.setPrefWidth(250);

        TableColumn<RispostaRow, String> colRisposta = new TableColumn<>("Risposta Gestore");
        colRisposta.setCellValueFactory(c -> c.getValue().rispostaProperty());
        colRisposta.setPrefWidth(250);

        tabella.getColumns().add(colUtente);
        tabella.getColumns().add(colVoto);
        tabella.getColumns().add(colCommento);
        tabella.getColumns().add(colRisposta);

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

    // ============================
    // GETTER PER IL CONTROLLER
    // ============================

    public TableView<RispostaRow> getTabella() { return tabella; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnRispondi() { return btnRispondi; }
}
