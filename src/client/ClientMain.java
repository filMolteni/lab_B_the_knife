package client;

import client.controller.HomeController;
import client.controller.LoginController;
import client.controller.RicercaRistorantiController;
import client.gui.HomeView;
import client.gui.LoginView;
import client.gui.RicercaRistorantiView;
import client.net.ClientConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    private ClientConnection connection;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {

        this.primaryStage = stage;

        // Connessione al server
        connection = new ClientConnection("localhost", 5555);

        // Mostra la Home all'avvio
        mostraHome();
    }

    // ============================
    // HOME
    // ============================
    private void mostraHome() {
        HomeView homeView = new HomeView();

        HomeController homeController = new HomeController(
                homeView,
                connection,
                this::vaiACerca,
                this::vaiAPreferiti,
                this::vaiARecensioni,
                this::vaiAGestione,
                this::vaiALogin,   // <-- AGGIUNTO
                this::logout
        );

        primaryStage.setScene(new Scene(homeView, 800, 600));
        primaryStage.setTitle("TheKnife - Home");
        primaryStage.show();
    }

    // ============================
    // LOGIN
    // ============================
    private void vaiALogin() {
        LoginView loginView = new LoginView();

        LoginController loginController = new LoginController(
                loginView,
                connection,
                this::mostraHome,      // dopo login → torna alla Home
                () -> System.out.println("TODO: Registrazione"), // registrazione
                this::mostraHome       // indietro → torna alla Home
        );

        primaryStage.setScene(new Scene(loginView, 600, 400));
        primaryStage.setTitle("TheKnife - Login");
    }

    // ============================
    // CERCA RISTORANTI
    // ============================
    private void vaiACerca() {
        RicercaRistorantiView view = new RicercaRistorantiView();
        new RicercaRistorantiController(view, connection);

        Stage stage = new Stage();
        stage.setScene(new Scene(view, 800, 600));
        stage.setTitle("Ricerca Ristoranti");
        stage.show();
    }

    // ============================
    // PREFERITI
    // ============================
    private void vaiAPreferiti() {
        System.out.println("Apro la schermata dei preferiti");
    }

    // ============================
    // RECENSIONI
    // ============================
    private void vaiARecensioni() {
        System.out.println("Apro la schermata delle recensioni");
    }

    // ============================
    // GESTIONE RISTORANTI
    // ============================
    private void vaiAGestione() {
        System.out.println("Apro la gestione ristoranti");
    }

    // ============================
    // LOGOUT
    // ============================
    private void logout() {
        System.out.println("Logout effettuato");
        mostraHome();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
