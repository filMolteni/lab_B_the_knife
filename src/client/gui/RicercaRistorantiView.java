package client.gui;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RicercaRistorantiView extends VBox {

    private final TextField txtRicerca = new TextField();
    private final ComboBox<String> filtroCategoria = new ComboBox<>();
    private final Button btnCerca = new Button("Cerca");

    private final TableView<RistoranteRow> tabella = new TableView<>();

    public RicercaRistorantiView() {

        txtRicerca.setPromptText("Cerca per nome, indirizzo...");

        filtroCategoria.getItems().addAll("Tutte", "Pizza", "Giapponese", "Italiano", "Burger");
        filtroCategoria.setValue("Tutte");

        HBox barra = new HBox(10, txtRicerca, filtroCategoria, btnCerca);

        TableColumn<RistoranteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());

        TableColumn<RistoranteRow, String> colIndirizzo = new TableColumn<>("Indirizzo");
        colIndirizzo.setCellValueFactory(c -> c.getValue().indirizzoProperty());

        TableColumn<RistoranteRow, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(c -> c.getValue().categoriaProperty());

        tabella.getColumns().addAll(colNome, colIndirizzo, colCategoria);

        setSpacing(10);
        getChildren().addAll(barra, tabella);
    }

    public TextField getTxtRicerca() { return txtRicerca; }
    public ComboBox<String> getFiltroCategoria() { return filtroCategoria; }
    public Button getBtnCerca() { return btnCerca; }
    public TableView<RistoranteRow> getTabella() { return tabella; }
}
