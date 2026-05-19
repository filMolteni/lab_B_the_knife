package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecensioneRow {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty idRistorante;
    private final SimpleStringProperty nomeRistorante;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty data;
    private final SimpleStringProperty fonte;

    public RecensioneRow(int id, int idRistorante, String nomeRistorante,
                         int voto, String commento, String data, String fonte) {

        this.id = new SimpleIntegerProperty(id);
        this.idRistorante = new SimpleIntegerProperty(idRistorante);
        this.nomeRistorante = new SimpleStringProperty(nomeRistorante);
        this.voto = new SimpleIntegerProperty(voto);
        this.commento = new SimpleStringProperty(commento);
        this.data = new SimpleStringProperty(data);
        this.fonte = new SimpleStringProperty(fonte);
    }

    public int getId() { return id.get(); }
    public int getIdRistorante() { return idRistorante.get(); }
    public String getNomeRistorante() { return nomeRistorante.get(); }
    public int getVoto() { return voto.get(); }
    public String getCommento() { return commento.get(); }
    public String getData() { return data.get(); }
    public String getFonte() { return fonte.get(); }

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleIntegerProperty idRistoranteProperty() { return idRistorante; }
    public SimpleStringProperty nomeRistoranteProperty() { return nomeRistorante; }
    public SimpleIntegerProperty votoProperty() { return voto; }
    public SimpleStringProperty commentoProperty() { return commento; }
    public SimpleStringProperty dataProperty() { return data; }
    public SimpleStringProperty fonteProperty() { return fonte; }
}
