package client;

import client.controller.*;
import client.gui.*;
import client.model.UtenteDTO;
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
        this::vaiARecensioniUtente,
        this::vaiAGestioneRistoranti,
        this::vaiARisposteGestore,   // <-- AGGIUNTO
        this::vaiALogin,
        this::logout
);


        primaryStage.setScene(new Scene(homeView, 800, 600));
        primaryStage.setTitle("TheKnife - Home");
        primaryStage.show();

        homeView.refreshVisibility();
    }

    // ============================
    // LOGIN
    // ============================
    private void vaiALogin() {

        LoginView loginView = new LoginView();

        new LoginController(
                loginView,
                connection,
                this::mostraHome,      // dopo login
                () -> System.out.println("TODO: Registrazione"),
                this::mostraHome       // indietro
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

        PreferitiView view = new PreferitiView();
        PreferitiController controller =
                new PreferitiController(view, connection, this::mostraHome);

        controller.loadPreferiti();

        primaryStage.setScene(new Scene(view, 800, 600));
        primaryStage.setTitle("TheKnife - Preferiti");
    }

    // ============================
    // RECENSIONI UTENTE
    // ============================
    private void vaiARecensioniUtente() {

        RecensioniUtenteView view = new RecensioniUtenteView();
        RecensioniUtenteController controller =
                new RecensioniUtenteController(view, connection, this::mostraHome);

        controller.loadRecensioni();

        primaryStage.setScene(new Scene(view, 800, 600));
        primaryStage.setTitle("TheKnife - Le mie recensioni");
    }

    // ============================
    // GESTIONE RISTORANTI (GESTORE)
    // ============================
    private void vaiAGestioneRistoranti() {

        GestoreRistorantiView view = new GestoreRistorantiView();
        GestoreRistorantiController controller =
                new GestoreRistorantiController(view, connection, this::mostraHome);

        controller.loadRiepilogo();

        primaryStage.setScene(new Scene(view, 900, 650));
        primaryStage.setTitle("TheKnife - Gestione Ristoranti");
    }

    // ============================
    // RISPOSTE ALLE RECENSIONI (GESTORE)
    // ============================
    private void vaiARisposteGestore() {

        RisposteGestoreView view = new RisposteGestoreView();
        RisposteGestoreController controller =
                new RisposteGestoreController(view, connection, this::mostraHome);

        controller.loadRecensioni();

        primaryStage.setScene(new Scene(view, 900, 650));
        primaryStage.setTitle("TheKnife - Recensioni ricevute");
    }

    // ============================
    // LOGOUT
    // ============================
    private void logout() {

        System.out.println("Logout effettuato");

        UtenteDTO.creaUtenteLoggato(0, null, null, null);

        mostraHome();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
