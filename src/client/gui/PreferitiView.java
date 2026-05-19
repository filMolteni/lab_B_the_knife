package client.gui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class PreferitiView extends VBox {

    private final TableView<Integer> tabella = new TableView<>();
    private final Button btnIndietro = new Button("← Indietro");

    public PreferitiView() {

        TableColumn<Integer, Number> colId = new TableColumn<>("ID Ristorante");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue()));

        tabella.getColumns().add(colId);

        setSpacing(10);
        getChildren().addAll(tabella, btnIndietro);
    }

    public TableView<Integer> getTabella() { return tabella; }
    public Button getBtnIndietro() { return btnIndietro; }
}
