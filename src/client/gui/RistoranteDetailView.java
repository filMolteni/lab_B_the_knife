package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class RistoranteDetailView extends BorderPane {

    private TextField txtNome;
    private TextField txtIndirizzo;
    private TextField txtCitta;
    private TextField txtNazione;
    private TextField txtTipoCucina;
    private TextField txtFasciaPrezzo;
    private TextField txtLatitudine;
    private TextField txtLongitudine;

    private CheckBox chkDelivery;
    private CheckBox chkPrenotazione;

    private Button btnIndietro;
    private Button btnRecensioni;
    private Button btnScriviRecensione;   // <-- AGGIUNTO

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

        txtNome = new TextField(); txtNome.setEditable(false);
        txtIndirizzo = new TextField(); txtIndirizzo.setEditable(false);
        txtCitta = new TextField(); txtCitta.setEditable(false);
        txtNazione = new TextField(); txtNazione.setEditable(false);
        txtTipoCucina = new TextField(); txtTipoCucina.setEditable(false);
        txtFasciaPrezzo = new TextField(); txtFasciaPrezzo.setEditable(false);
        txtLatitudine = new TextField(); txtLatitudine.setEditable(false);
        txtLongitudine = new TextField(); txtLongitudine.setEditable(false);

        chkDelivery = new CheckBox("Delivery disponibile");
        chkDelivery.setDisable(true);

        chkPrenotazione = new CheckBox("Prenotazione disponibile");
        chkPrenotazione.setDisable(true);

        form.add(new Label("Nome:"), 0, 0); form.add(txtNome, 1, 0);
        form.add(new Label("Indirizzo:"), 0, 1); form.add(txtIndirizzo, 1, 1);
        form.add(new Label("Città:"), 0, 2); form.add(txtCitta, 1, 2);
        form.add(new Label("Nazione:"), 0, 3); form.add(txtNazione, 1, 3);
        form.add(new Label("Tipo cucina:"), 0, 4); form.add(txtTipoCucina, 1, 4);
        form.add(new Label("Fascia prezzo:"), 0, 5); form.add(txtFasciaPrezzo, 1, 5);
        form.add(new Label("Latitudine:"), 0, 6); form.add(txtLatitudine, 1, 6);
        form.add(new Label("Longitudine:"), 0, 7); form.add(txtLongitudine, 1, 7);

        form.add(chkDelivery, 1, 8);
        form.add(chkPrenotazione, 1, 9);

        this.setCenter(form);

        // ============================
        // BOTTONI
        // ============================
        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(120);

        btnRecensioni = new Button("Mostra Recensioni");
        btnRecensioni.setPrefWidth(160);

        btnScriviRecensione = new Button("Scrivi Recensione");   // <-- AGGIUNTO
        btnScriviRecensione.setPrefWidth(160);

        HBox bottomBox = new HBox(20, btnIndietro, btnRecensioni, btnScriviRecensione);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        this.setBottom(bottomBox);

        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    // ============================
    // GETTER PER IL CONTROLLER
    // ============================

    public TextField getTxtNome() { return txtNome; }
    public TextField getTxtIndirizzo() { return txtIndirizzo; }
    public TextField getTxtCitta() { return txtCitta; }
    public TextField getTxtNazione() { return txtNazione; }
    public TextField getTxtTipoCucina() { return txtTipoCucina; }
    public TextField getTxtFasciaPrezzo() { return txtFasciaPrezzo; }
    public TextField getTxtLatitudine() { return txtLatitudine; }
    public TextField getTxtLongitudine() { return txtLongitudine; }

    public CheckBox getChkDelivery() { return chkDelivery; }
    public CheckBox getChkPrenotazione() { return chkPrenotazione; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnRecensioni() { return btnRecensioni; }
    public Button getBtnScriviRecensione() { return btnScriviRecensione; } // <-- AGGIUNTO
}
