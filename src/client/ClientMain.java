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
            System.out.println("Errore di connessione al server: " + e.getMessage());
            return;
        }

        mostraHome();
    }

    // ============================
    // LOGIN (ORA A SCHERMO INTERO)
    // ============================
    private void mostraLogin() {
        LoginView view = new LoginView();

        new LoginController(
                view,
                connection,
                this::mostraHome,
                this::mostraRegistrazione,
                this::mostraHome
        );

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    // ============================
    // REGISTRAZIONE (A SCHERMO INTERO)
    // ============================
    private void mostraRegistrazione() {
        RegisterView view = new RegisterView();

        new RegisterController(
                view,
                connection,
                this::mostraLogin,
                this::mostraLogin
        );

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Registrazione");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    // ============================
    // HOME (A SCHERMO INTERO)
    // ============================
    private void mostraHome() {
        HomeView view = new HomeView();

        new HomeController(
                view,
                connection,
                this::mostraRicercaRistoranti,
                this::mostraPreferiti,
                this::mostraRecensioniUtente,
                this::mostraGestioneRistoranti,
                this::mostraRecensioniRicevute,
                this::mostraLogin,
                () -> {
                    System.out.println("Logout eseguito");
                    mostraHome();
                }
        );

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
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
                this::mostraHome,
                (id, fonte) -> mostraDettagliRistorante(id, fonte)
        );

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cerca Ristoranti");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    // ============================
    // DETTAGLI RISTORANTE
    // ============================
    private void mostraDettagliRistorante(int id, String fonte) {
        RistoranteDettagliView view = new RistoranteDettagliView();

        new RistoranteDettagliController(
                view,
                connection,
                id,
                fonte,
                this::mostraHome
        );

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dettagli Ristorante");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    // ============================
    // PREFERITI
    // ============================
    private void mostraPreferiti() {
        PreferitiView view = new PreferitiView();

        new PreferitiController(
                view,
                connection,
                this::mostraHome,
                (id, fonte) -> mostraDettagliRistorante(id, fonte)
        );

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Preferiti");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    // ============================
    // RECENSIONI UTENTE
    // ============================
    private void mostraRecensioniUtente() {
        RecensioniUtenteView view = new RecensioniUtenteView();

        new RecensioniUtenteController(
                view,
                connection,
                this::mostraHome
        );

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Le mie recensioni");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    // ============================
    // RECENSIONI RICEVUTE (GESTORE)
    // ============================
    private void mostraRecensioniRicevute() {
        RecensioniRistoranteView view = new RecensioniRistoranteView();

        RecensioniRistoranteController controller =
                new RecensioniRistoranteController(
                        view,
                        connection,
                        this::mostraHome
                );

        controller.loadRecensioni();

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Recensioni ricevute");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    // ============================
    // GESTIONE RISTORANTI (GESTORE)
    // ============================
    private void mostraGestioneRistoranti() {
        GestoreRistorantiView view = new GestoreRistorantiView();

        GestoreRistorantiController controller = new GestoreRistorantiController(
                view,
                connection,
                this::mostraHome,
                this::mostraDettagliRistorante
        );

        controller.loadRiepilogo();

        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestione Ristoranti");
        primaryStage.setMaximized(true);   // ⭐ SCHERMO INTERO
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
