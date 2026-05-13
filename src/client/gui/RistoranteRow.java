package client.gui;

import javafx.beans.property.SimpleStringProperty;

public class RistoranteRow {

    private final int id;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty indirizzo;
    private final SimpleStringProperty categoria;

    public RistoranteRow(int id, String nome, String indirizzo, String categoria) {
        this.id = id;
        this.nome = new SimpleStringProperty(nome);
        this.indirizzo = new SimpleStringProperty(indirizzo);
        this.categoria = new SimpleStringProperty(categoria);
    }

    public int getId() { return id; }
    public SimpleStringProperty nomeProperty() { return nome; }
    public SimpleStringProperty indirizzoProperty() { return indirizzo; }
    public SimpleStringProperty categoriaProperty() { return categoria; }

    public String getNome() {
    return nome.get();
}

public String getIndirizzo() {
    return indirizzo.get();
}

public String getCategoria() {
    return categoria.get();
}

}
