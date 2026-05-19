package client.gui;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RicercaRistorantiView extends VBox {

    private final TextField txtRicerca = new TextField();
    private final ComboBox<String> filtroTipoCucina = new ComboBox<>();
    private final Button btnCerca = new Button("Cerca");
    private final Button btnIndietro = new Button("← Indietro");

    private final TableView<RistoranteRow> tabella = new TableView<>();

    public RicercaRistorantiView() {

        txtRicerca.setPromptText("Cerca per nome, indirizzo...");

        filtroTipoCucina.getItems().addAll(
                "Tutte",
                "Italiano",
                "Pizza",
                "Giapponese",
                "Burger",
                "Cinese",
                "Messicano"
        );
        filtroTipoCucina.setValue("Tutte");

        // Barra superiore con bottone indietro
        HBox barra = new HBox(10, btnIndietro, txtRicerca, filtroTipoCucina, btnCerca);

        // ============================
        // COLONNE TABELLA
        // ============================

        TableColumn<RistoranteRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> c.getValue().nomeProperty());
        colNome.setPrefWidth(180);

        TableColumn<RistoranteRow, String> colIndirizzo = new TableColumn<>("Indirizzo");
        colIndirizzo.setCellValueFactory(c -> c.getValue().indirizzoProperty());
        colIndirizzo.setPrefWidth(220);

        TableColumn<RistoranteRow, String> colTipoCucina = new TableColumn<>("Tipo Cucina");
        colTipoCucina.setCellValueFactory(c -> c.getValue().categoriaProperty());
        colTipoCucina.setPrefWidth(150);

        tabella.getColumns().addAll(colNome, colIndirizzo, colTipoCucina);

        setSpacing(10);
        getChildren().addAll(barra, tabella);
    }

    public TextField getTxtRicerca() { return txtRicerca; }
    public ComboBox<String> getFiltroCategoria() { return filtroTipoCucina; }
    public Button getBtnCerca() { return btnCerca; }
    public Button getBtnIndietro() { return btnIndietro; }
    public TableView<RistoranteRow> getTabella() { return tabella; }
}
