package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class LoginView extends BorderPane {

    private TextField txtEmail;
    private PasswordField txtPassword;

    private Button btnAccedi;
    private Button btnRegistrati;
    private Button btnIndietro;

    public LoginView() {
        creaLayout();
    }

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

    public TextField getTxtEmail() { return txtEmail; }
    public PasswordField getTxtPassword() { return txtPassword; }

    public Button getBtnAccedi() { return btnAccedi; }
    public Button getBtnRegistrati() { return btnRegistrati; }
    public Button getBtnIndietro() { return btnIndietro; }
}
