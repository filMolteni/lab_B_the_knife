package client.gui;

import client.model.UtenteDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class HomeView extends BorderPane {

    private Button btnCerca;
    private Button btnPreferiti;
    private Button btnRecensioni;
    private Button btnGestione;
    private Button btnRecensioniRicevute;
    private Button btnLogin;
    private Button btnLogout;

    public HomeView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Benvenuto in TheKnife");
        titolo.setFont(new Font(32));
        titolo.setPadding(new Insets(30, 0, 40, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));
        this.setTop(topBox);

        // ============================
        // MENU CENTRALE
        // ============================
        btnCerca = new Button("Cerca Ristoranti");
        btnPreferiti = new Button("I miei Preferiti");
        btnRecensioni = new Button("Le mie Recensioni");
        btnGestione = new Button("Gestione Ristoranti");
        btnRecensioniRicevute = new Button("Recensioni ricevute");
        btnLogin = new Button("Login");
        btnLogout = new Button("Logout");

        // Larghezza pulsanti responsive
        Button[] buttons = {
                btnCerca, btnPreferiti, btnRecensioni,
                btnGestione, btnRecensioniRicevute,
                btnLogin, btnLogout
        };

        for (Button b : buttons) {
            b.setPrefWidth(260);
            b.setStyle("-fx-font-size: 16px;");
        }

        VBox menu = new VBox(20,
                btnCerca,
                btnPreferiti,
                btnRecensioni,
                btnGestione,
                btnRecensioniRicevute,
                btnLogin,
                btnLogout
        );

        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(40));

        // ⭐ Permette al menu di espandersi verticalmente
        VBox.setVgrow(menu, Priority.ALWAYS);

        this.setCenter(menu);
        BorderPane.setAlignment(menu, Pos.CENTER);

        // Padding generale
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    // ============================
    // VISIBILITÀ IN BASE AL LOGIN
    // ============================
    public void refreshVisibility() {

        UtenteDTO u = UtenteDTO.getUtenteLoggato();
        boolean logged = (u != null && u.getUsername() != null);

        btnLogin.setVisible(!logged);
        btnLogout.setVisible(logged);

        if (!logged) {
            btnCerca.setVisible(true);
            btnPreferiti.setVisible(false);
            btnRecensioni.setVisible(false);
            btnGestione.setVisible(false);
            btnRecensioniRicevute.setVisible(false);
            return;
        }

        if (u.isCliente()) {
            btnCerca.setVisible(true);
            btnPreferiti.setVisible(true);
            btnRecensioni.setVisible(true);
            btnGestione.setVisible(false);
            btnRecensioniRicevute.setVisible(false);
        }

        if (u.isGestore()) {
            btnCerca.setVisible(true);
            btnPreferiti.setVisible(false);
            btnRecensioni.setVisible(false);
            btnGestione.setVisible(true);
            btnRecensioniRicevute.setVisible(true);
        }
    }

    // GETTER
    public Button getBtnCerca() { return btnCerca; }
    public Button getBtnPreferiti() { return btnPreferiti; }
    public Button getBtnRecensioni() { return btnRecensioni; }
    public Button getBtnGestione() { return btnGestione; }
    public Button getBtnRecensioniRicevute() { return btnRecensioniRicevute; }
    public Button getBtnLogin() { return btnLogin; }
    public Button getBtnLogout() { return btnLogout; }
}
