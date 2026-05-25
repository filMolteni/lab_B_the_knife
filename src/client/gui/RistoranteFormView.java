package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * View grafica dedicata alla creazione o modifica di un ristorante.
 * Mostra:
 * - un form completo con nome, indirizzo, tipo cucina, fascia prezzo
 * - informazioni geografiche (città, nazione, latitudine, longitudine)
 * - servizi disponibili (delivery, prenotazione)
 * - pulsanti Salva e Annulla
 *
 * La view è strutturata come un BorderPane:
 * - Top: titolo
 * - Center: form dei campi
 * - Bottom: pulsanti di azione
 */
public class RistoranteFormView extends BorderPane {

    private TextField txtNome;
    private TextField txtIndirizzo;
    private TextField txtTipoCucina;
    private TextField txtCitta;
    private TextField txtNazione;
    private TextField txtLatitudine;
    private TextField txtLongitudine;
    private Spinner<Integer> spFasciaPrezzo;

    private CheckBox chkDelivery;
    private CheckBox chkPrenotazione;

    private Button btnSalva;
    private Button btnAnnulla;

    /**
     * Costruisce la schermata e inizializza il layout.
     */
    public RistoranteFormView() {
        creaLayout();
    }

    /**
     * Crea l’intero layout grafico:
     * - titolo
     * - form con tutti i campi del ristorante
     * - pulsanti Salva e Annulla
     * - padding e stile generale
     */
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
        txtTipoCucina = new TextField();
        txtCitta = new TextField();
        txtNazione = new TextField();
        txtLatitudine = new TextField();
        txtLongitudine = new TextField();

        spFasciaPrezzo = new Spinner<>(1, 5, 1);

        chkDelivery = new CheckBox("Delivery disponibile");
        chkPrenotazione = new CheckBox("Prenotazione disponibile");

        form.add(new Label("Nome:"), 0, 0);
        form.add(txtNome, 1, 0);

        form.add(new Label("Indirizzo:"), 0, 1);
        form.add(txtIndirizzo, 1, 1);

        form.add(new Label("Tipo cucina:"), 0, 2);
        form.add(txtTipoCucina, 1, 2);

        form.add(new Label("Fascia prezzo (1-5):"), 0, 3);
        form.add(spFasciaPrezzo, 1, 3);

        form.add(new Label("Città:"), 0, 4);
        form.add(txtCitta, 1, 4);

        form.add(new Label("Nazione:"), 0, 5);
        form.add(txtNazione, 1, 5);

        form.add(new Label("Latitudine:"), 0, 6);
        form.add(txtLatitudine, 1, 6);

        form.add(new Label("Longitudine:"), 0, 7);
        form.add(txtLongitudine, 1, 7);

        form.add(chkDelivery, 1, 8);
        form.add(chkPrenotazione, 1, 9);

        this.setCenter(form);

        btnSalva = new Button("Salva");
        btnAnnulla = new Button("Annulla");

        HBox buttons = new HBox(15, btnSalva, btnAnnulla);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20));

        this.setBottom(buttons);
    }

    // ============================
    // GETTER
    // ============================

    /** @return campo nome */
    public TextField getTxtNome() { return txtNome; }

    /** @return campo indirizzo */
    public TextField getTxtIndirizzo() { return txtIndirizzo; }

    /** @return campo tipo cucina */
    public TextField getTxtTipoCucina() { return txtTipoCucina; }

    /** @return spinner fascia prezzo */
    public Spinner<Integer> getSpFasciaPrezzo() { return spFasciaPrezzo; }

    /** @return campo città */
    public TextField getTxtCitta() { return txtCitta; }

    /** @return campo nazione */
    public TextField getTxtNazione() { return txtNazione; }

    /** @return campo latitudine */
    public TextField getTxtLatitudine() { return txtLatitudine; }

    /** @return campo longitudine */
    public TextField getTxtLongitudine() { return txtLongitudine; }

    /** @return true se delivery è selezionato */
    public boolean isDelivery() { return chkDelivery.isSelected(); }

    /** @return true se prenotazione è selezionato */
    public boolean isPrenotazione() { return chkPrenotazione.isSelected(); }

    /** @return pulsante Salva */
    public Button getBtnSalva() { return btnSalva; }

    /** @return pulsante Annulla */
    public Button getBtnAnnulla() { return btnAnnulla; }

    /**
     * Precompila i campi del form quando si modifica un ristorante esistente.
     *
     * @param nome nome del ristorante
     * @param indirizzo indirizzo completo
     * @param tipoCucina tipo di cucina
     * @param fasciaPrezzo fascia prezzo (1–5)
     * @param citta città
     * @param nazione nazione
     * @param lat latitudine
     * @param lon longitudine
     * @param delivery disponibilità delivery
     * @param prenotazione disponibilità prenotazione
     */
    public void setValues(String nome, String indirizzo, String tipoCucina,
                          int fasciaPrezzo, String citta, String nazione,
                          double lat, double lon, boolean delivery, boolean prenotazione) {

        txtNome.setText(nome);
        txtIndirizzo.setText(indirizzo);
        txtTipoCucina.setText(tipoCucina);
        spFasciaPrezzo.getValueFactory().setValue(fasciaPrezzo);
        txtCitta.setText(citta);
        txtNazione.setText(nazione);
        txtLatitudine.setText(String.valueOf(lat));
        txtLongitudine.setText(String.valueOf(lon));
        chkDelivery.setSelected(delivery);
        chkPrenotazione.setSelected(prenotazione);
    }

    // ============================
    // METODI HELPER
    // ============================

    /** @return fascia prezzo selezionata */
    public int getFasciaPrezzo() { return spFasciaPrezzo.getValue(); }

    /** @return latitudine convertita in double */
    public double getLatitudine() {
        return Double.parseDouble(txtLatitudine.getText().trim());
    }

    /** @return longitudine convertita in double */
    public double getLongitudine() {
        return Double.parseDouble(txtLongitudine.getText().trim());
    }

}
