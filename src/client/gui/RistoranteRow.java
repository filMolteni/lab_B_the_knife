package client.gui;

import javafx.beans.property.*;

public class RistoranteRow {

    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty tipoCucina;
    private final StringProperty indirizzo;

    public RistoranteRow(int id, String nome, String tipoCucina, String indirizzo) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.tipoCucina = new SimpleStringProperty(tipoCucina);
        this.indirizzo = new SimpleStringProperty(indirizzo);
    }

    // ===== GETTER =====
    public int getId() { return id.get(); }
    public String getNome() { return nome.get(); }
    public String getTipoCucina() { return tipoCucina.get(); }
    public String getIndirizzo() { return indirizzo.get(); }

    // ===== PROPERTY =====
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomeProperty() { return nome; }
    public StringProperty categoriaProperty() { return tipoCucina; } // compatibile con la view
    public StringProperty indirizzoProperty() { return indirizzo; }

    // ===== SETTER =====
    public void setNome(String nome) { this.nome.set(nome); }
    public void setTipoCucina(String tipoCucina) { this.tipoCucina.set(tipoCucina); }
    public void setIndirizzo(String indirizzo) { this.indirizzo.set(indirizzo); }
}
