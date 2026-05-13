package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RistoranteFormView extends BorderPane {

    private TextField txtNome;
    private TextField txtIndirizzo;
    private TextField txtCategoria;
    private TextArea txtDescrizione;

    private Button btnSalva;
    private Button btnAnnulla;

    public RistoranteFormView() {
        creaLayout();
    }

    private void creaLayout() {

        Label titolo = new Label("Gestione Ristorante");
        titolo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        BorderPane.setAlignment(titolo, Pos.CENTER);
        this.setTop(titolo);
        BorderPane.setMargin(titolo, new Insets(20, 0, 20, 0));

        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setVgap(15);
        form.setHgap(10);

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

        this.setCenter(form);

        btnSalva = new Button("Salva");
        btnAnnulla = new Button("Annulla");

        HBox buttons = new HBox(15, btnSalva, btnAnnulla);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        this.setBottom(buttons);
    }

    // GETTER
    public TextField getTxtNome() { return txtNome; }
    public TextField getTxtIndirizzo() { return txtIndirizzo; }
    public TextField getTxtCategoria() { return txtCategoria; }
    public TextArea getTxtDescrizione() { return txtDescrizione; }

    public Button getBtnSalva() { return btnSalva; }
    public Button getBtnAnnulla() { return btnAnnulla; }

    // Precompila i campi in caso di modifica
    public void setValues(String nome, String indirizzo, String categoria, String descrizione) {
        txtNome.setText(nome);
        txtIndirizzo.setText(indirizzo);
        txtCategoria.setText(categoria);
        txtDescrizione.setText(descrizione);
    }
}
