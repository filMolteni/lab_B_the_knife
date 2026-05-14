package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class RistoranteDettagliView extends BorderPane {

    private Label lblNome;
    private Label lblIndirizzo;
    private Label lblTipoCucina;
    private TextArea areaRecensioni;
    private Button btnIndietro;

    public RistoranteDettagliView() {
        creaLayout();
    }

    private void creaLayout() {
        lblNome = new Label();
        lblNome.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        lblIndirizzo = new Label();
        lblTipoCucina = new Label();

        areaRecensioni = new TextArea();
        areaRecensioni.setEditable(false);
        areaRecensioni.setPrefHeight(250);

        btnIndietro = new Button("← Torna alla ricerca");

        VBox box = new VBox(10, lblNome, lblIndirizzo, lblTipoCucina, areaRecensioni, btnIndietro);
        box.setAlignment(Pos.TOP_LEFT);
        box.setPadding(new Insets(20));

        this.setCenter(box);
    }

    public Label getLblNome() { return lblNome; }
    public Label getLblIndirizzo() { return lblIndirizzo; }
    public Label getLblTipoCucina() { return lblTipoCucina; }
    public TextArea getAreaRecensioni() { return areaRecensioni; }
    public Button getBtnIndietro() { return btnIndietro; }
}
