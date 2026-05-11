package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class RegisterView extends BorderPane {

    private TextField txtUsername;
    private TextField txtEmail;
    private PasswordField txtPassword;
    private PasswordField txtConferma;

    private Button btnRegistrati;
    private Button btnIndietro;

    public RegisterView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Registrazione Utente");
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(20, 0, 20, 0));

        HBox topBox = new HBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // FORM
        // ============================
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(10);
        form.setVgap(15);

        txtUsername = new TextField();
        txtEmail = new TextField();
        txtPassword = new PasswordField();
        txtConferma = new PasswordField();

        form.add(new Label("Username:"), 0, 0);
        form.add(txtUsername, 1, 0);

        form.add(new Label("Email:"), 0, 1);
        form.add(txtEmail, 1, 1);

        form.add(new Label("Password:"), 0, 2);
        form.add(txtPassword, 1, 2);

        form.add(new Label("Conferma Password:"), 0, 3);
        form.add(txtConferma, 1, 3);

        this.setCenter(form);

        // ============================
        // BOTTONI
        // ============================
        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(120);

        btnRegistrati = new Button("Registrati");
        btnRegistrati.setPrefWidth(120);

        HBox bottomBox = new HBox(20, btnIndietro, btnRegistrati);
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

    public TextField getTxtUsername() { return txtUsername; }
    public TextField getTxtEmail() { return txtEmail; }
    public PasswordField getTxtPassword() { return txtPassword; }
    public PasswordField getTxtConferma() { return txtConferma; }

    public Button getBtnRegistrati() { return btnRegistrati; }
    public Button getBtnIndietro() { return btnIndietro; }
}
