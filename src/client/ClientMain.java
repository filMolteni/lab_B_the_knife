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
    // LOGIN
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
                this::mostraLogin,
                this::mostraLogin
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
                this::mostraRicercaRistoranti,   // CERCA
                this::mostraPreferiti,           // PREFERITI
                this::mostraRecensioniUtente,    // RECENSIONI UTENTE
                this::mostraGestioneRistoranti,  // GESTIONE (GESTORE)
                this::mostraRecensioniRicevute,  // RECENSIONI RICEVUTE (GESTORE)
                this::mostraLogin,               // LOGIN
                () -> {                          // LOGOUT
                    System.out.println("Logout eseguito");
                    mostraHome();
                }
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
                    this::mostraHome,
                    (id, fonte) -> mostraDettagliRistorante(id, fonte)   // ⭐ AGGIORNATO
            );

            primaryStage.setScene(new Scene(view, 800, 600));
            primaryStage.setTitle("Cerca Ristoranti");
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
                fonte, // ⭐ PASSIAMO LA FONTE
                this::mostraRicercaRistoranti
        );

        primaryStage.setScene(new Scene(view, 700, 500));
        primaryStage.setTitle("Dettagli Ristorante");
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
                (id, fonte) -> mostraDettagliRistorante(id, fonte)   // ⭐ AGGIUNTO
        );

        primaryStage.setScene(new Scene(view, 600, 500));
        primaryStage.setTitle("Preferiti");
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

        primaryStage.setScene(new Scene(view, 700, 500));
        primaryStage.setTitle("Le mie recensioni");
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

        primaryStage.setScene(new Scene(view, 700, 500));
        primaryStage.setTitle("Recensioni ricevute");
        primaryStage.show();
    }




    // ============================
    // GESTIONE RISTORANTI (GESTORE)
    // ============================
    private void mostraGestioneRistoranti() {

        GestoreRistorantiView view = new GestoreRistorantiView();

        // ⭐ CREA IL CONTROLLER E SALVALO IN UNA VARIABILE
        GestoreRistorantiController controller = new GestoreRistorantiController(
                view,
                connection,
                this::mostraHome,
                this::mostraDettagliRistorante
        );

        // ⭐ CARICA SUBITO I RISTORANTI DEL GESTORE
        controller.loadRiepilogo();

        primaryStage.setScene(new Scene(view, 800, 600));
        primaryStage.setTitle("Gestione Ristoranti");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
