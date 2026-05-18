package client;

import client.controller.*;
import client.gui.*;
import client.net.ClientConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    private Stage primaryStage;
    private ClientConnection connection;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        try {
            connection = new ClientConnection("localhost", 5555);
            System.out.println("Connessione stabilita con il server");
        } catch (Exception e) {
            System.out.println("❌ Errore di connessione al server: " + e.getMessage());
            return;
        }

        mostraLogin();   // LOGIN
    }

    // ============================
    // LOGIN
    // ============================
    private void mostraLogin() {
        LoginView view = new LoginView();

        new LoginController(
                view,
                connection,
                this::mostraHome,          // dopo login OK
                this::mostraRegistrazione, // vai a registrazione
                this::mostraHome           // torna indietro
        );

        primaryStage.setScene(new Scene(view, 500, 400));
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    // ============================
    // REGISTRAZIONE
    // ============================
    private void mostraRegistrazione() {
        RegisterView view = new RegisterView();

        new RegisterController(
                view,
                connection,
                this::mostraLogin,   // dopo registrazione OK → torna al login
                this::mostraLogin    // indietro → login
        );

        primaryStage.setScene(new Scene(view, 500, 450));
        primaryStage.setTitle("Registrazione");
        primaryStage.show();
    }

    // ============================
    // HOME
    // ============================
    private void mostraHome() {
        HomeView view = new HomeView();

        new HomeController(
                view,
                connection,
                this::mostraRicercaRistoranti,
                () -> System.out.println("Vai ai preferiti"),
                () -> System.out.println("Vai alle recensioni"),
                () -> System.out.println("Vai alla gestione"),
                () -> System.out.println("Vai alle recensioni ricevute"),
                this::mostraLogin,     // LOGIN 
                () -> System.out.println("Logout eseguito")
        );

        primaryStage.setScene(new Scene(view, 600, 500));
        primaryStage.setTitle("Home");
        primaryStage.show();
    }

    // ============================
    // RICERCA RISTORANTI
    // ============================
    private void mostraRicercaRistoranti() {
        RicercaRistorantiView view = new RicercaRistorantiView();

        new RicercaRistorantiController(
                view,
                connection,
                this::mostraDettagliRistorante
        );

        primaryStage.setScene(new Scene(view, 800, 600));
        primaryStage.setTitle("Cerca Ristoranti");
        primaryStage.show();
    }

    // ============================
    // DETTAGLI RISTORANTE
    // ============================
    private void mostraDettagliRistorante(int id) {
        RistoranteDettagliView view = new RistoranteDettagliView();

        new RistoranteDettagliController(
                view,
                connection,
                id,
                this::mostraRicercaRistoranti
        );

        primaryStage.setScene(new Scene(view, 700, 500));
        primaryStage.setTitle("Dettagli Ristorante");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
