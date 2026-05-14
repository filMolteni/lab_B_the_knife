package client.gui;

import client.model.UtenteDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

        Label titolo = new Label("Benvenuto in TheKnife");
        titolo.setFont(new Font(28));
        titolo.setPadding(new Insets(20, 0, 30, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        btnCerca = new Button("Cerca Ristoranti");
        btnCerca.setPrefWidth(220);

        btnPreferiti = new Button("I miei Preferiti");
        btnPreferiti.setPrefWidth(220);

        btnRecensioni = new Button("Le mie Recensioni");
        btnRecensioni.setPrefWidth(220);

        btnGestione = new Button("Gestione Ristoranti");
        btnGestione.setPrefWidth(220);

        btnRecensioniRicevute = new Button("Recensioni ricevute");
        btnRecensioniRicevute.setPrefWidth(220);

        btnLogin = new Button("Login");
        btnLogin.setPrefWidth(220);

        btnLogout = new Button("Logout");
        btnLogout.setPrefWidth(220);

        VBox menu = new VBox(15,
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

        this.setCenter(menu);

        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    public void refreshVisibility() {

        UtenteDTO u = UtenteDTO.getUtenteLoggato();

        if (u == null) {
            btnCerca.setVisible(true);
            btnPreferiti.setVisible(false);
            btnRecensioni.setVisible(false);
            btnGestione.setVisible(false);
            btnRecensioniRicevute.setVisible(false);

            btnLogin.setVisible(true);
            btnLogout.setVisible(false);
            return;
        }

        btnLogin.setVisible(false);
        btnLogout.setVisible(true);

        if (u.isCliente()) {
            btnCerca.setVisible(true);
            btnPreferiti.setVisible(true);
            btnRecensioni.setVisible(true);

            btnGestione.setVisible(false);
            btnRecensioniRicevute.setVisible(false);
        }

        if (u.isGestore()) {
            btnCerca.setVisible(false);
            btnPreferiti.setVisible(false);
            btnRecensioni.setVisible(false);

            btnGestione.setVisible(true);
            btnRecensioniRicevute.setVisible(true);
        }
    }

    // === GETTER ===
    public Button getBtnCerca() { return btnCerca; }
    public Button getBtnPreferiti() { return btnPreferiti; }
    public Button getBtnRecensioni() { return btnRecensioni; }
    public Button getBtnGestione() { return btnGestione; }
    public Button getBtnRecensioniRicevute() { return btnRecensioniRicevute; }
    public Button getBtnLogin() { return btnLogin; }
    public Button getBtnLogout() { return btnLogout; }

}
