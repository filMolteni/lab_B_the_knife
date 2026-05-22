package client.gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PreferitiView extends BorderPane {

    private final TableView<PreferitoRow> tabella = new TableView<>();
    private final Button btnIndietro = new Button("← Indietro");

    public PreferitiView() {
        creaLayout();
    }

    private void creaLayout() {

        // ============================
        // TABELLA
        // ============================
        TableColumn<PreferitoRow, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));

        TableColumn<PreferitoRow, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));

        TableColumn<PreferitoRow, String> colIndirizzo = new TableColumn<>("Indirizzo");
        colIndirizzo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIndirizzo()));

        TableColumn<PreferitoRow, String> colCitta = new TableColumn<>("Città");
        colCitta.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCitta()));

        TableColumn<PreferitoRow, String> colNazione = new TableColumn<>("Nazione");
        colNazione.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNazione()));

        TableColumn<PreferitoRow, String> colCucina = new TableColumn<>("Tipo Cucina");
        colCucina.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipoCucina()));

        TableColumn<PreferitoRow, Number> colPrezzo = new TableColumn<>("Fascia Prezzo");
        colPrezzo.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFasciaPrezzo()));

        TableColumn<PreferitoRow, Boolean> colDelivery = new TableColumn<>("Delivery");
        colDelivery.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isDelivery()));

        TableColumn<PreferitoRow, Boolean> colPrenotazione = new TableColumn<>("Prenotazione");
        colPrenotazione.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isPrenotazione()));

        TableColumn<PreferitoRow, String> colFonte = new TableColumn<>("Fonte");
        colFonte.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFonte()));

        tabella.getColumns().addAll(
                colId, colNome, colIndirizzo, colCitta, colNazione,
                colCucina, colPrezzo, colDelivery, colPrenotazione, colFonte
        );

        tabella.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ⭐ La tabella ora si espande a tutto lo spazio disponibile
        BorderPane.setMargin(tabella, new Insets(10));
        this.setCenter(tabella);

        // ============================
        // BOTTONE INDIETRO
        // ============================
        btnIndietro.setPrefWidth(150);
        btnIndietro.setStyle("-fx-font-size: 16px;");

        HBox bottom = new HBox(btnIndietro);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(20));

        this.setBottom(bottom);

        // Padding generale
        this.setPadding(new Insets(10));
    }

    public TableView<PreferitoRow> getTabella() { return tabella; }
    public Button getBtnIndietro() { return btnIndietro; }
}
