package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecensioneRistoranteRow {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty idRistorante;
    private final SimpleStringProperty utente;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty data;
    private final SimpleStringProperty fonte; 

    public RecensioneRistoranteRow(int id, int idRistorante, String utente,
                                   int voto, String commento, String data, String fonte) {

        this.id = new SimpleIntegerProperty(id);
        this.idRistorante = new SimpleIntegerProperty(idRistorante);
        this.utente = new SimpleStringProperty(utente);
        this.voto = new SimpleIntegerProperty(voto);
        this.commento = new SimpleStringProperty(commento);
        this.data = new SimpleStringProperty(data);
        this.fonte = new SimpleStringProperty(fonte);
    }

    // ===== GETTER =====
    public int getId() { return id.get(); }
    public int getIdRistorante() { return idRistorante.get(); }
    public String getUtente() { return utente.get(); }
    public int getVoto() { return voto.get(); }
    public String getCommento() { return commento.get(); }
    public String getData() { return data.get(); }
    public String getFonte() { return fonte.get(); }

    // ===== PROPERTY =====
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleIntegerProperty idRistoranteProperty() { return idRistorante; }
    public SimpleStringProperty utenteProperty() { return utente; }
    public SimpleIntegerProperty votoProperty() { return voto; }
    public SimpleStringProperty commentoProperty() { return commento; }
    public SimpleStringProperty dataProperty() { return data; }
    public SimpleStringProperty fonteProperty() { return fonte; }
}
