package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RispostaRow {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty utente;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty risposta;

    public RispostaRow(int id, String utente, int voto, String commento, String risposta) {
        this.id = new SimpleIntegerProperty(id);
        this.utente = new SimpleStringProperty(utente);
        this.voto = new SimpleIntegerProperty(voto);
        this.commento = new SimpleStringProperty(commento);
        this.risposta = new SimpleStringProperty(risposta);
    }

    // ===== GETTER =====
    public int getId() { return id.get(); }
    public String getUtente() { return utente.get(); }
    public int getVoto() { return voto.get(); }
    public String getCommento() { return commento.get(); }
    public String getRisposta() { return risposta.get(); }

    // ===== SETTER =====
    public void setId(int value) { id.set(value); }
    public void setUtente(String value) { utente.set(value); }
    public void setVoto(int value) { voto.set(value); }
    public void setCommento(String value) { commento.set(value); }
    public void setRisposta(String value) { risposta.set(value); }

    // ===== PROPERTY =====
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty utenteProperty() { return utente; }
    public SimpleIntegerProperty votoProperty() { return voto; }
    public SimpleStringProperty commentoProperty() { return commento; }
    public SimpleStringProperty rispostaProperty() { return risposta; }
}
