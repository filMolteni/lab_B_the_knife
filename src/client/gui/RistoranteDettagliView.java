package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class RistoranteDettagliView extends BorderPane {

    private Label lblNome;
    private Label lblIndirizzo;
    private Label lblCitta;
    private Label lblNazione;
    private Label lblTipoCucina;
    private Label lblFasciaPrezzo;
    private Label lblLatitudine;
    private Label lblLongitudine;
    private Label lblDelivery;
    private Label lblPrenotazione;

    // ⭐ NUOVO: contenitore dinamico recensioni
    private VBox recensioniContainer;

    private Button btnIndietro;
    private Button btnPreferiti;
    private Button btnScriviRecensione;

    private TextArea txtRecensione;
    private Spinner<Integer> votoSpinner;

    public RistoranteDettagliView() {
        creaLayout();
    }

    private void creaLayout() {

        lblNome = new Label();
        lblNome.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        lblIndirizzo = new Label();
        lblCitta = new Label();
        lblNazione = new Label();
        lblTipoCucina = new Label();
        lblFasciaPrezzo = new Label();
        lblLatitudine = new Label();
        lblLongitudine = new Label();
        lblDelivery = new Label();
        lblPrenotazione = new Label();

        // ⭐ NUOVO: contenitore recensioni
        recensioniContainer = new VBox(10);
        recensioniContainer.setPadding(new Insets(10));

        btnIndietro = new Button("← indietro ");
        btnPreferiti = new Button("⭐ Aggiungi ai preferiti");
        btnScriviRecensione = new Button("📝 Invia recensione");

        txtRecensione = new TextArea();
        txtRecensione.setPromptText("Scrivi qui la tua recensione...");
        txtRecensione.setPrefHeight(80);

        votoSpinner = new Spinner<>(1, 5, 5);

        VBox box = new VBox(
                10,
                lblNome,
                lblIndirizzo,
                lblCitta,
                lblNazione,
                lblTipoCucina,
                lblFasciaPrezzo,
                lblLatitudine,
                lblLongitudine,
                lblDelivery,
                lblPrenotazione,

                new Label("Recensioni:"),
                recensioniContainer,   // ⭐ sostituisce areaRecensioni

                new Label("Scrivi una recensione:"),
                votoSpinner,
                txtRecensione,
                btnScriviRecensione,
                btnPreferiti,
                btnIndietro
        );

        box.setAlignment(Pos.TOP_LEFT);
        box.setPadding(new Insets(20));

        this.setCenter(box);
    }

    // ============================================================
    // ⭐ NASCONDI FUNZIONI CLIENTE
    // ============================================================
    public void nascondiFunzioniCliente() {
        txtRecensione.setManaged(false);
        txtRecensione.setVisible(false);

        votoSpinner.setManaged(false);
        votoSpinner.setVisible(false);

        btnScriviRecensione.setManaged(false);
        btnScriviRecensione.setVisible(false);

        btnPreferiti.setManaged(false);
        btnPreferiti.setVisible(false);
    }

    
    // ============================================================
    // GETTERS
    // ============================================================
    public Label getLblNome() { return lblNome; }
    public Label getLblIndirizzo() { return lblIndirizzo; }
    public Label getLblCitta() { return lblCitta; }
    public Label getLblNazione() { return lblNazione; }
    public Label getLblTipoCucina() { return lblTipoCucina; }
    public Label getLblFasciaPrezzo() { return lblFasciaPrezzo; }
    public Label getLblLatitudine() { return lblLatitudine; }
    public Label getLblLongitudine() { return lblLongitudine; }
    public Label getLblDelivery() { return lblDelivery; }
    public Label getLblPrenotazione() { return lblPrenotazione; }

    public VBox getRecensioniContainer() { return recensioniContainer; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnPreferiti() { return btnPreferiti; }
    public Button getBtnScriviRecensione() { return btnScriviRecensione; }

    public TextArea getTxtRecensione() { return txtRecensione; }
    public Spinner<Integer> getVotoSpinner() { return votoSpinner; }
}
