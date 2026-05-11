package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private Button btnLogout;

    public HomeView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TITOLO
        // ============================
        Label titolo = new Label("Benvenuto in FoodAdvisor");
        titolo.setFont(new Font(28));
        titolo.setPadding(new Insets(20, 0, 30, 0));

        VBox topBox = new VBox(titolo);
        topBox.setAlignment(Pos.CENTER);
        this.setTop(topBox);

        // ============================
        // BOTTONI PRINCIPALI
        // ============================
        btnCerca = new Button("Cerca Ristoranti");
        btnCerca.setPrefWidth(220);

        btnPreferiti = new Button("I miei Preferiti");
        btnPreferiti.setPrefWidth(220);

        btnRecensioni = new Button("Le mie Recensioni");
        btnRecensioni.setPrefWidth(220);

        btnGestione = new Button("Gestione Ristoranti");
        btnGestione.setPrefWidth(220);

        btnLogout = new Button("Logout");
        btnLogout.setPrefWidth(220);

        VBox menu = new VBox(15, btnCerca, btnPreferiti, btnRecensioni, btnGestione, btnLogout);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(40));

        this.setCenter(menu);

        // ============================
        // STILE GENERALE
        // ============================
        this.setPadding(new Insets(15));
        this.setStyle("-fx-background-color: #f8f8f8;");
    }

    // ============================
    // GETTER PER IL CONTROLLER
    // ============================

    public Button getBtnCerca() { return btnCerca; }
    public Button getBtnPreferiti() { return btnPreferiti; }
    public Button getBtnRecensioni() { return btnRecensioni; }
    public Button getBtnGestione() { return btnGestione; }
    public Button getBtnLogout() { return btnLogout; }
}
