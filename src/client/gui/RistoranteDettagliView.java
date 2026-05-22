package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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

    private Label lblMediaVoti;

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

        // ============================
        // LABEL PRINCIPALI
        // ============================
        lblNome = new Label();
        lblNome.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        lblIndirizzo = new Label();
        lblCitta = new Label();
        lblNazione = new Label();
        lblTipoCucina = new Label();
        lblFasciaPrezzo = new Label();
        lblLatitudine = new Label();
        lblLongitudine = new Label();
        lblDelivery = new Label();
        lblPrenotazione = new Label();

        lblMediaVoti = new Label("Media voti: -");
        lblMediaVoti.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #444;");

        // ============================
        // CONTENITORE RECENSIONI
        // ============================
        recensioniContainer = new VBox(12);
        recensioniContainer.setPadding(new Insets(10));

        // ============================
        // SEZIONE SCRITTURA RECENSIONE
        // ============================
        txtRecensione = new TextArea();
        txtRecensione.setPromptText("Scrivi qui la tua recensione...");
        txtRecensione.setPrefRowCount(5);
        txtRecensione.setStyle("-fx-font-size: 14px;");

        votoSpinner = new Spinner<>(1, 5, 5);

        btnScriviRecensione = new Button("📝 Invia recensione");
        btnScriviRecensione.setPrefWidth(200);

        btnPreferiti = new Button("⭐ Aggiungi ai preferiti");
        btnPreferiti.setPrefWidth(200);

        btnIndietro = new Button("← Indietro");
        btnIndietro.setPrefWidth(200);

        // ============================
        // LAYOUT CENTRALE (SCROLLABILE)
        // ============================
        VBox contenuto = new VBox(
                15,
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
                lblMediaVoti,

                new Label("Recensioni:"),
                recensioniContainer,

                new Label("Scrivi una recensione:"),
                votoSpinner,
                txtRecensione,
                btnScriviRecensione,
                btnPreferiti,
                btnIndietro
        );

        contenuto.setPadding(new Insets(25));
        contenuto.setAlignment(Pos.TOP_LEFT);

        // ⭐ ScrollPane per rendere tutto responsive
        ScrollPane scroll = new ScrollPane(contenuto);
        scroll.setFitToWidth(true);
        scroll.setPadding(new Insets(10));

        this.setCenter(scroll);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    // ============================================================
    // NASCONDI FUNZIONI CLIENTE
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

    public Label getLblMediaVoti() { return lblMediaVoti; }

    public VBox getRecensioniContainer() { return recensioniContainer; }

    public Button getBtnIndietro() { return btnIndietro; }
    public Button getBtnPreferiti() { return btnPreferiti; }
    public Button getBtnScriviRecensione() { return btnScriviRecensione; }

    public TextArea getTxtRecensione() { return txtRecensione; }
    public Spinner<Integer> getVotoSpinner() { return votoSpinner; }
}
