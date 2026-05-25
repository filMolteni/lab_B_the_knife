package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Modello dati che rappresenta una risposta del gestore a una recensione.
 * Contiene:
 * - id della recensione
 * - nome dell’utente autore della recensione
 * - voto assegnato
 * - commento originale dell’utente
 * - risposta del gestore (se presente)
 *
 * Utilizzato nella tabella delle risposte del gestore.
 * Tutti i campi sono gestiti tramite proprietà JavaFX per supportare il binding nella UI.
 */
public class RispostaRow {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty utente;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty risposta;

    /**
     * Costruisce un oggetto rappresentante una risposta del gestore.
     *
     * @param id id della recensione
     * @param utente nome dell’utente autore della recensione
     * @param voto voto assegnato dall’utente
     * @param commento testo della recensione originale
     * @param risposta risposta del gestore (può essere vuota)
     */
    public RispostaRow(int id, String utente, int voto, String commento, String risposta) {
        this.id = new SimpleIntegerProperty(id);
        this.utente = new SimpleStringProperty(utente);
        this.voto = new SimpleIntegerProperty(voto);
        this.commento = new SimpleStringProperty(commento);
        this.risposta = new SimpleStringProperty(risposta);
    }

    // ===== GETTER =====

    /** @return id della recensione */
    public int getId() { return id.get(); }

    /** @return nome dell’utente autore */
    public String getUtente() { return utente.get(); }

    /** @return voto assegnato dall’utente */
    public int getVoto() { return voto.get(); }

    /** @return commento originale dell’utente */
    public String getCommento() { return commento.get(); }

    /** @return risposta del gestore */
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
