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

public class RecensioniUtenteView extends BorderPane {

    private TableView<RecensioneRow> tabella;

    private Button btnIndietro;
    private Button btnModifica;
    private Button btnElimina;

    public RecensioniUtenteView() {
        creaLayout();
    }

    private void creaLayout() {

        Label titolo = new Label("Le Tue Recensioni");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(20, 0, 20, 0));

        HBox topBox = new HBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        tabella = new TableView<>();

        TableColumn<RecensioneRow, String> colRistorante = new TableColumn<>("Ristorante");
        colRistorante.setCellValueFactory(c -> c.getValue().nomeRistoranteProperty());
        colRistorante.setPrefWidth(180);

        TableColumn<RecensioneRow, Integer> colVoto = new TableColumn<>("Voto");
        colVoto.setCellValueFactory(c -> c.getValue().votoProperty().asObject());
        colVoto.setPrefWidth(60);

        TableColumn<RecensioneRow, String> colCommento = new TableColumn<>("Commento");
        colCommento.setCellValueFactory(c -> c.getValue().commentoProperty());
        colCommento.setPrefWidth(250);

        TableColumn<RecensioneRow, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> c.getValue().dataProperty());
        colData.setPrefWidth(120);

        tabella.getColumns().add(colRistorante);
        tabella.getColumns().add(colVoto);
        tabella.getColumns().add(colCommento);
        tabella.getColumns().add(colData);

        this.setCenter(tabella);

        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(120);

        btnModifica = new Button("Modifica");
        btnModifica.setPrefWidth(120);

        btnElimina = new Button("Elimina");
        btnElimina.setPrefWidth(120);

        HBox bottomBox = new HBox(20, btnIndietro, btnModifica, btnElimina);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        this.setBottom(bottomBox);

        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    public TableView<RecensioneRow> getTabella() { return tabella; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnModifica() { return btnModifica; }
    public Button getBtnElimina() { return btnElimina; }
}
