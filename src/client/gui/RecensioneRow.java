package client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Modello dati per rappresentare una recensione scritta dall’utente.
 * Utilizzato nella tabella della schermata "Le mie Recensioni".
 *
 * Contiene:
 * - id recensione
 * - id ristorante
 * - nome del ristorante
 * - voto assegnato
 * - commento
 * - data della recensione
 * - fonte del ristorante (THEKNIFE o UTENTE)
 *
 * Ogni campo è gestito tramite proprietà JavaFX per supportare il binding nella UI.
 */
public class RecensioneRow {

    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty idRistorante;
    private final SimpleStringProperty nomeRistorante;
    private final SimpleIntegerProperty voto;
    private final SimpleStringProperty commento;
    private final SimpleStringProperty data;
    private final SimpleStringProperty fonte;

    /**
     * Costruisce un oggetto rappresentante una recensione dell’utente.
     *
     * @param id id della recensione
     * @param idRistorante id del ristorante recensito
     * @param nomeRistorante nome del ristorante
     * @param voto voto assegnato (1–5)
     * @param commento testo della recensione
     * @param data data della recensione
     * @param fonte provenienza del ristorante (THEKNIFE o UTENTE)
     */
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

    // ===== GETTER =====

    /** @return id della recensione */
    public int getId() { return id.get(); }

    /** @return id del ristorante recensito */
    public int getIdRistorante() { return idRistorante.get(); }

    /** @return nome del ristorante recensito */
    public String getNomeRistorante() { return nomeRistorante.get(); }

    /** @return voto assegnato */
    public int getVoto() { return voto.get(); }

    /** @return testo della recensione */
    public String getCommento() { return commento.get(); }

    /** @return data della recensione */
    public String getData() { return data.get(); }

    /** @return fonte del ristorante (THEKNIFE o UTENTE) */
    public String getFonte() { return fonte.get(); }

    // ===== PROPERTY =====

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleIntegerProperty idRistoranteProperty() { return idRistorante; }
    public SimpleStringProperty nomeRistoranteProperty() { return nomeRistorante; }
    public SimpleIntegerProperty votoProperty() { return voto; }
    public SimpleStringProperty commentoProperty() { return commento; }
    public SimpleStringProperty dataProperty() { return data; }
    public SimpleStringProperty fonteProperty() { return fonte; }
}
