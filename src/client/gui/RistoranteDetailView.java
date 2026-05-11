package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class RistoranteDetailView extends BorderPane {

    private TextField txtNome;
    private TextField txtIndirizzo;
    private TextField txtCategoria;
    private TextArea txtDescrizione;

    private Button btnIndietro;
    private Button btnRecensioni;

    public RistoranteDetailView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Dettagli Ristorante");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(20, 0, 20, 0));

        HBox topBox = new HBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // FORM DETTAGLI
        // ============================
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(10);
        form.setVgap(15);

        txtNome = new TextField();
        txtNome.setEditable(false);

        txtIndirizzo = new TextField();
        txtIndirizzo.setEditable(false);

        txtCategoria = new TextField();
        txtCategoria.setEditable(false);

        txtDescrizione = new TextArea();
        txtDescrizione.setEditable(false);
        txtDescrizione.setWrapText(true);
        txtDescrizione.setPrefRowCount(5);

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);

        form.add(new Label("Indirizzo:"), 0, 1);
        form.add(txtIndirizzo, 1, 1);

        form.add(new Label("Categoria:"), 0, 2);
        form.add(txtCategoria, 1, 2);

        form.add(new Label("Descrizione:"), 0, 3);
        form.add(txtDescrizione, 1, 3);

        this.setCenter(form);

        // ============================
        // BOTTONI
        // ============================
        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(120);

        btnRecensioni = new Button("Mostra Recensioni");
        btnRecensioni.setPrefWidth(160);

        HBox bottomBox = new HBox(20, btnIndietro, btnRecensioni);
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

    public TextField getTxtNome() { return txtNome; }
    public TextField getTxtIndirizzo() { return txtIndirizzo; }
    public TextField getTxtCategoria() { return txtCategoria; }
    public TextArea getTxtDescrizione() { return txtDescrizione; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnRecensioni() { return btnRecensioni; }
}
