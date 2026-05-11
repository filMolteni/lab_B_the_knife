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

public class PreferitiView extends BorderPane {

    private TableView<RistoranteRow> tabella;

    private Button btnIndietro;
    private Button btnRimuovi;

    public PreferitiView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("I Tuoi Ristoranti Preferiti");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(20, 0, 20, 0));

        HBox topBox = new HBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // TABELLA
        // ============================
        tabella = new TableView<>();

        TableColumn<RistoranteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        colNome.setPrefWidth(180);

        TableColumn<RistoranteRow, String> colIndirizzo = new TableColumn<>("Indirizzo");
        colIndirizzo.setCellValueFactory(c -> c.getValue().indirizzoProperty());
        colIndirizzo.setPrefWidth(220);

        TableColumn<RistoranteRow, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(c -> c.getValue().categoriaProperty());
        colCategoria.setPrefWidth(120);

        tabella.getColumns().add(colNome);
        tabella.getColumns().add(colIndirizzo);
        tabella.getColumns().add(colCategoria);

        this.setCenter(tabella);

        // ============================
        // BOTTONI
        // ============================
        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(140);

        btnRimuovi = new Button("Rimuovi dai Preferiti");
        btnRimuovi.setPrefWidth(180);

        HBox bottomBox = new HBox(20, btnIndietro, btnRimuovi);
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

    public TableView<RistoranteRow> getTabella() { return tabella; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnRimuovi() { return btnRimuovi; }
}
