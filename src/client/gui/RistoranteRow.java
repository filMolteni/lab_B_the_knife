package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RistoranteRow {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty indirizzo;
    private final SimpleStringProperty categoria;

    public RistoranteRow(int id, String nome, String indirizzo, String categoria) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.indirizzo = new SimpleStringProperty(indirizzo);
        this.categoria = new SimpleStringProperty(categoria);
    }

    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public SimpleIntegerProperty idProperty() { return id; }

    public String getNome() { return nome.get(); }
    public void setNome(String value) { nome.set(value); }
    public SimpleStringProperty nomeProperty() { return nome; }

    public String getIndirizzo() { return indirizzo.get(); }
    public void setIndirizzo(String value) { indirizzo.set(value); }
    public SimpleStringProperty indirizzoProperty() { return indirizzo; }

    public String getCategoria() { return categoria.get(); }
    public void setCategoria(String value) { categoria.set(value); }
    public SimpleStringProperty categoriaProperty() { return categoria; }
}

