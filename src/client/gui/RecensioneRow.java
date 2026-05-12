package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecensioneRow {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty ristorante;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty data;

    public RecensioneRow(int id, String ristorante, int voto, String commento, String data) {
        this.id = new SimpleIntegerProperty(id);
        this.ristorante = new SimpleStringProperty(ristorante);
        this.voto = new SimpleIntegerProperty(voto);
        this.commento = new SimpleStringProperty(commento);
        this.data = new SimpleStringProperty(data);
    }

    // ===== GETTER =====
    public int getId() { return id.get(); }
    public String getRistorante() { return ristorante.get(); }
    public int getVoto() { return voto.get(); }
    public String getCommento() { return commento.get(); }
    public String getData() { return data.get(); }

    // ===== PROPERTY =====
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty ristoranteProperty() { return ristorante; }
    public SimpleIntegerProperty votoProperty() { return voto; }
    public SimpleStringProperty commentoProperty() { return commento; }
    public SimpleStringProperty dataProperty() { return data; }
}
