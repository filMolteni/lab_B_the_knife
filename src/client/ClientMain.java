package client;

import client.gui.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    @Override
    public void start(Stage stage) {

        HomeView home = new HomeView();

        Scene scene = new Scene(home, 800, 600);

        stage.setTitle("TheKnife - Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

