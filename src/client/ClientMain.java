package client;

import client.controller.*;
import client.gui.*;
import client.net.ClientConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point dell'applicazione client.
 *
 * Questa classe:
 * - avvia la connessione con il server
 * - inizializza la finestra principale (Stage)
 * - gestisce la navigazione tra tutte le schermate dell'applicazione
 *
 * Ogni metodo {@code mostraX()} crea la relativa View, istanzia il Controller
 * e imposta la scena nello Stage principale.
 *
 * L'applicazione gira sempre a schermo intero (maximized).
 */
public class ClientMain extends Application {

    private Stage primaryStage;
    private ClientConnection connection;

    /**
     * Metodo di avvio JavaFX.
     * Stabilisce la connessione al server e mostra la Home.
     *
     * @param stage finestra principale
     */
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

    /**
     * Mostra la schermata di login.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // REGISTRAZIONE
    // ============================

    /**
     * Mostra la schermata di registrazione.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // HOME
    // ============================

    /**
     * Mostra la schermata principale dell'applicazione.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // RICERCA RISTORANTI
    // ============================

    /**
     * Mostra la schermata di ricerca ristoranti.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // DETTAGLI RISTORANTE
    // ============================

    /**
     * Mostra la schermata dei dettagli di un ristorante.
     *
     * @param id id del ristorante
     * @param fonte provenienza del ristorante (THEKNIFE / UTENTE)
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // PREFERITI
    // ============================

    /**
     * Mostra la schermata dei ristoranti preferiti.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // RECENSIONI UTENTE
    // ============================

    /**
     * Mostra la schermata con le recensioni scritte dall'utente.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // RECENSIONI RICEVUTE (GESTORE)
    // ============================

    /**
     * Mostra la schermata delle recensioni ricevute dai ristoranti del gestore.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ============================
    // GESTIONE RISTORANTI (GESTORE)
    // ============================

    /**
     * Mostra la schermata di gestione dei ristoranti del gestore.
     */
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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Metodo main: avvia l'applicazione JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
