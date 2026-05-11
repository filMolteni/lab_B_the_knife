package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecensioneRow {

    private final SimpleStringProperty ristorante;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty data;

    public RecensioneRow(String ristorante, int voto, String commento, String data) {
        this.ristorante = new SimpleStringProperty(ristorante);
        this.voto = new SimpleIntegerProperty(voto);
        this.commento = new SimpleStringProperty(commento);
        this.data = new SimpleStringProperty(data);
    }

    // ===== GETTER =====
    public String getRistorante() { return ristorante.get(); }
    public int getVoto() { return voto.get(); }
    public String getCommento() { return commento.get(); }
    public String getData() { return data.get(); }

    // ===== SETTER =====
    public void setRistorante(String value) { ristorante.set(value); }
    public void setVoto(int value) { voto.set(value); }
    public void setCommento(String value) { commento.set(value); }
    public void setData(String value) { data.set(value); }

    // ===== PROPERTY =====
    public SimpleStringProperty ristoranteProperty() { return ristorante; }
    public SimpleIntegerProperty votoProperty() { return voto; }
    public SimpleStringProperty commentoProperty() { return commento; }
    public SimpleStringProperty dataProperty() { return data; }
}
