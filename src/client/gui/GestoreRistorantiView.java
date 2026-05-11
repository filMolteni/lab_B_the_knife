package client.gui;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

public class GestoreRistorantiView extends BorderPane {

    private TableView<RistoranteRow> tabella;

    private TextField txtNome;
    private TextField txtIndirizzo;
    private TextField txtCategoria;
    private TextArea txtDescrizione;

    private Button btnAggiungi;
    private Button btnModifica;
    private Button btnElimina;
    private Button btnPulisci;

    public GestoreRistorantiView() {
        creaLayout();
    }

    private void creaLayout() {

        Label titolo = new Label("Gestione Ristoranti");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(10, 0, 20, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

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

        TableColumn<RistoranteRow, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(c -> c.getValue().categoriaProperty());
        colCategoria.setPrefWidth(120);

        tabella.getColumns().add(colId);
        tabella.getColumns().add(colNome);
        tabella.getColumns().add(colIndirizzo);
        tabella.getColumns().add(colCategoria);
        tabella.setPrefHeight(300);

        this.setCenter(tabella);

        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(10);
        form.setVgap(10);

        txtNome = new TextField();
        txtIndirizzo = new TextField();
        txtCategoria = new TextField();
        txtDescrizione = new TextArea();
        txtDescrizione.setPrefRowCount(4);

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);

        form.add(new Label("Indirizzo:"), 0, 1);
        form.add(txtIndirizzo, 1, 1);

        form.add(new Label("Categoria:"), 0, 2);
        form.add(txtCategoria, 1, 2);

        form.add(new Label("Descrizione:"), 0, 3);
        form.add(txtDescrizione, 1, 3);

        btnAggiungi = new Button("Aggiungi");
        btnModifica = new Button("Modifica");
        btnElimina = new Button("Elimina");
        btnPulisci = new Button("Pulisci");

        HBox bottoni = new HBox(10, btnAggiungi, btnModifica, btnElimina, btnPulisci);
        bottoni.setAlignment(Pos.CENTER);
        bottoni.setPadding(new Insets(10, 0, 0, 0));

        VBox bottomBox = new VBox(form, bottoni);
        bottomBox.setPadding(new Insets(10));
        this.setBottom(bottomBox);
    }

    public TableView<RistoranteRow> getTabella() { return tabella; }

    public TextField getTxtNome() { return txtNome; }
    public TextField getTxtIndirizzo() { return txtIndirizzo; }
    public TextField getTxtCategoria() { return txtCategoria; }
    public TextArea getTxtDescrizione() { return txtDescrizione; }

    public Button getBtnAggiungi() { return btnAggiungi; }
    public Button getBtnModifica() { return btnModifica; }
    public Button getBtnElimina() { return btnElimina; }
    public Button getBtnPulisci() { return btnPulisci; }
}
