package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * View grafica dedicata alla registrazione di un nuovo utente.
 * Mostra:
 * - un form con username, email, password e conferma password
 * - un checkbox per registrarsi come gestore
 * - pulsanti per registrarsi o tornare indietro
 *
 * La view è strutturata come un BorderPane:
 * - Top: titolo
 * - Center: form centrato
 * - Bottom: pulsanti di navigazione
 */
public class RegisterView extends BorderPane {

    private TextField txtUsername;
    private TextField txtEmail;
    private PasswordField txtPassword;
    private PasswordField txtConferma;

    private CheckBox chkGestore;

    private Button btnRegistrati;
    private Button btnIndietro;

    /**
     * Costruisce la schermata di registrazione e inizializza il layout.
     */
    public RegisterView() {
        creaLayout();
    }

    /**
     * Crea l’intero layout grafico della schermata:
     * - titolo
     * - form con i campi di registrazione
     * - checkbox per ruolo gestore
     * - pulsanti di azione
     * - padding e stile generale
     */
    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Registrazione Utente");
        titolo.setFont(new Font(32));
        titolo.setPadding(new Insets(20, 0, 40, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));
        this.setTop(topBox);

        // ============================
        // FORM CENTRALE
        // ============================
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(15);
        form.setVgap(20);

        txtUsername = new TextField();
        txtUsername.setPrefWidth(300);

        txtEmail = new TextField();
        txtEmail.setPrefWidth(300);

        txtPassword = new PasswordField();
        txtPassword.setPrefWidth(300);

        txtConferma = new PasswordField();
        txtConferma.setPrefWidth(300);

        chkGestore = new CheckBox("Registrati come gestore");

        form.add(new Label("Username:"), 0, 0);
        form.add(txtUsername, 1, 0);

        form.add(new Label("Email:"), 0, 1);
        form.add(txtEmail, 1, 1);

        form.add(new Label("Password:"), 0, 2);
        form.add(txtPassword, 1, 2);

        form.add(new Label("Conferma Password:"), 0, 3);
        form.add(txtConferma, 1, 3);

        form.add(chkGestore, 1, 4);

        // ⭐ Centra il form nello schermo
        VBox centerBox = new VBox(form);
        centerBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(centerBox, Priority.ALWAYS);

        this.setCenter(centerBox);

        // ============================
        // BOTTONI IN BASSO
        // ============================
        btnIndietro = new Button("Indietro");
        btnRegistrati = new Button("Registrati");

        Button[] buttons = { btnIndietro, btnRegistrati };
        for (Button b : buttons) {
            b.setPrefWidth(150);
            b.setStyle("-fx-font-size: 16px;");
        }

        HBox bottomBox = new HBox(30, btnIndietro, btnRegistrati);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(30));

        this.setBottom(bottomBox);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    /** @return campo username */
    public TextField getTxtUsername() { return txtUsername; }

    /** @return campo email */
    public TextField getTxtEmail() { return txtEmail; }

    /** @return campo password */
    public PasswordField getTxtPassword() { return txtPassword; }

    /** @return campo conferma password */
    public PasswordField getTxtConferma() { return txtConferma; }

    /** @return checkbox per registrarsi come gestore */
    public CheckBox getChkGestore() { return chkGestore; }

    /** @return pulsante Registrati */
    public Button getBtnRegistrati() { return btnRegistrati; }

    /** @return pulsante Indietro */
    public Button getBtnIndietro() { return btnIndietro; }
}
