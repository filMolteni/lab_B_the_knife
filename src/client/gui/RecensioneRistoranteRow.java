package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecensioneRistoranteRow {

    private final SimpleStringProperty utente;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty data;

    public RecensioneRistoranteRow(String utente, int voto, String commento, String data) {
        this.utente = new SimpleStringProperty(utente);
        this.voto = new SimpleIntegerProperty(voto);
        this.commento = new SimpleStringProperty(commento);
        this.data = new SimpleStringProperty(data);
    }

    public String getUtente() { return utente.get(); }
    public int getVoto() { return voto.get(); }
    public String getCommento() { return commento.get(); }
    public String getData() { return data.get(); }

    public SimpleStringProperty utenteProperty() { return utente; }
    public SimpleIntegerProperty votoProperty() { return voto; }
    public SimpleStringProperty commentoProperty() { return commento; }
    public SimpleStringProperty dataProperty() { return data; }
}
