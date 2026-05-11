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

public class LoginView extends BorderPane {

    private TextField txtUsername;
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
        titolo.setFont(new Font(26));
        titolo.setPadding(new Insets(20, 0, 20, 0));

        HBox topBox = new HBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // FORM LOGIN
        // ============================
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(10);
        form.setVgap(15);

        txtUsername = new TextField();
        txtPassword = new PasswordField();

        form.add(new Label("Username:"), 0, 0);
        form.add(txtUsername, 1, 0);

        form.add(new Label("Password:"), 0, 1);
        form.add(txtPassword, 1, 1);

        this.setCenter(form);

        // ============================
        // BOTTONI
        // ============================
        btnIndietro = new Button("Indietro");
        btnIndietro.setPrefWidth(120);

        btnRegistrati = new Button("Registrati");
        btnRegistrati.setPrefWidth(120);

        btnAccedi = new Button("Accedi");
        btnAccedi.setPrefWidth(120);

        HBox bottomBox = new HBox(20, btnIndietro, btnRegistrati, btnAccedi);
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
    public PasswordField getTxtPassword() { return txtPassword; }

    public Button getBtnAccedi() { return btnAccedi; }
    public Button getBtnRegistrati() { return btnRegistrati; }
    public Button getBtnIndietro() { return btnIndietro; }
}

