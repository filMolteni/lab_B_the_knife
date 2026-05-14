package client;

import client.controller.HomeController;
import client.controller.RicercaRistorantiController;
import client.controller.RistoranteDettagliController;
import client.gui.HomeView;
import client.gui.RicercaRistorantiView;
import client.gui.RistoranteDettagliView;
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
            e.printStackTrace();
            return;
        }

        mostraHome();
    }

    private void mostraHome() {
        HomeView homeView = new HomeView();

        new HomeController(
                homeView,
                connection,
                this::mostraRicercaRistoranti,
                () -> System.out.println("Vai ai preferiti"),
                () -> System.out.println("Vai alle recensioni"),
                () -> System.out.println("Vai alla gestione"),
                () -> System.out.println("Vai alle recensioni ricevute"),
                () -> System.out.println("Vai al login"),
                () -> System.out.println("Logout eseguito")
        );

        primaryStage.setScene(new Scene(homeView, 600, 500));
        primaryStage.setTitle("The Knife - Home");
        primaryStage.show();
    }

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
