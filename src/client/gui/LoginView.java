package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * View grafica dedicata alla schermata di login.
 * Mostra:
 * - titolo della schermata
 * - form per email e password
 * - pulsanti per accedere, registrarsi o tornare indietro
 *
 * La view è strutturata come un BorderPane:
 * - Top: titolo
 * - Center: form centrato
 * - Bottom: pulsanti di navigazione
 */
public class LoginView extends BorderPane {

    private TextField txtEmail;
    private PasswordField txtPassword;

    private Button btnAccedi;
    private Button btnRegistrati;
    private Button btnIndietro;

    /**
     * Costruisce la schermata di login e inizializza il layout.
     */
    public LoginView() {
        creaLayout();
    }

    /**
     * Crea l'intero layout grafico della schermata:
     * - titolo
     * - form con email e password
     * - pulsanti di azione
     * - stile e padding
     */
    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Accesso Utente");
        titolo.setFont(new Font(32));
        titolo.setPadding(new Insets(20, 0, 40, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // FORM CENTRALE
        // ============================
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(15);
        form.setVgap(20);

        txtEmail = new TextField();
        txtEmail.setPrefWidth(300);

        txtPassword = new PasswordField();
        txtPassword.setPrefWidth(300);

        form.add(new Label("Email:"), 0, 0);
        form.add(txtEmail, 1, 0);

        form.add(new Label("Password:"), 0, 1);
        form.add(txtPassword, 1, 1);

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
        btnAccedi = new Button("Accedi");

        Button[] buttons = { btnIndietro, btnRegistrati, btnAccedi };
        for (Button b : buttons) {
            b.setPrefWidth(150);
            b.setStyle("-fx-font-size: 16px;");
        }

        HBox bottomBox = new HBox(30, btnIndietro, btnRegistrati, btnAccedi);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(30));

        this.setBottom(bottomBox);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    /** @return campo email */
    public TextField getTxtEmail() { return txtEmail; }

    /** @return campo password */
    public PasswordField getTxtPassword() { return txtPassword; }

    /** @return pulsante Accedi */
    public Button getBtnAccedi() { return btnAccedi; }

    /** @return pulsante Registrati */
    public Button getBtnRegistrati() { return btnRegistrati; }

    /** @return pulsante Indietro */
    public Button getBtnIndietro() { return btnIndietro; }
}
