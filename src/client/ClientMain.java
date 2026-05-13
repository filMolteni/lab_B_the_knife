package client;

import client.controller.HomeController;
import client.controller.RicercaRistorantiController;
import client.gui.HomeView;
import client.gui.RicercaRistorantiView;
import client.net.ClientConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    private ClientConnection connection;

    @Override
    public void start(Stage stage) {

        // Connessione al server
        connection = new ClientConnection("localhost", 5555);

        // VIEW
        HomeView homeView = new HomeView();

        // CONTROLLER con callback
        HomeController homeController = new HomeController(
                homeView,
                connection,
                this::vaiACerca,
                this::vaiAPreferiti,
                this::vaiARecensioni,
                this::vaiAGestione,
                this::logout
        );

        // SCENA PRINCIPALE
        Scene scene = new Scene(homeView, 800, 600);
        stage.setTitle("TheKnife - Client");
        stage.setScene(scene);
        stage.show();
    }

    // 🔹 Schermata di ricerca ristoranti
    private void vaiACerca() {
        System.out.println("Apro la schermata di ricerca ristoranti");

        RicercaRistorantiView view = new RicercaRistorantiView();
        RicercaRistorantiController controller = new RicercaRistorantiController(view, connection);

        Scene scene = new Scene(view, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("Ricerca Ristoranti");
        stage.setScene(scene);
        stage.show();
    }

    // 🔹 Schermata preferiti
    private void vaiAPreferiti() {
        System.out.println("Apro la schermata dei preferiti");
    }

    // 🔹 Schermata recensioni
    private void vaiARecensioni() {
        System.out.println("Apro la schermata delle recensioni");
    }

    // 🔹 Schermata gestione ristoranti
    private void vaiAGestione() {
        System.out.println("Apro la gestione ristoranti");
    }

    // 🔹 Logout
    private void logout() {
        System.out.println("Logout effettuato");
        System.exit(0);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
