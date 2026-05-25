package client.gui;

import javafx.beans.property.*;

/**
 * Modello dati che rappresenta un ristorante mostrato nelle tabelle
 * (ricerca ristoranti, preferiti, ecc.).
 *
 * Contiene:
 * - id del ristorante
 * - nome
 * - tipo di cucina
 * - indirizzo
 * - fonte del ristorante (THEKNIFE o UTENTE)
 *
 * Ogni campo è gestito tramite proprietà JavaFX per supportare il binding nella UI.
 */
public class RistoranteRow {

    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty tipoCucina;
    private final StringProperty indirizzo;

    // ⭐ NUOVO: fonte del ristorante (THEKNIFE / UTENTE)
    private final StringProperty fonte;

    /**
     * Costruisce un oggetto rappresentante un ristorante.
     *
     * @param id id del ristorante
     * @param nome nome del ristorante
     * @param tipoCucina tipo di cucina
     * @param indirizzo indirizzo completo
     * @param fonte provenienza del ristorante (THEKNIFE o UTENTE)
     */
    public RistoranteRow(int id, String nome, String tipoCucina, String indirizzo, String fonte) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.tipoCucina = new SimpleStringProperty(tipoCucina);
        this.indirizzo = new SimpleStringProperty(indirizzo);
        this.fonte = new SimpleStringProperty(fonte); // ⭐ NUOVO
    }

    // ===== GETTER =====

    /** @return id del ristorante */
    public int getId() { return id.get(); }

    /** @return nome del ristorante */
    public String getNome() { return nome.get(); }

    /** @return tipo di cucina */
    public String getTipoCucina() { return tipoCucina.get(); }

    /** @return indirizzo del ristorante */
    public String getIndirizzo() { return indirizzo.get(); }

    /** @return fonte del ristorante (THEKNIFE o UTENTE) */
    public String getFonte() { return fonte.get(); }

    // ===== PROPERTY =====

    public IntegerProperty idProperty() { return id; }
    public StringProperty nomeProperty() { return nome; }
    public StringProperty categoriaProperty() { return tipoCucina; }
    public StringProperty indirizzoProperty() { return indirizzo; }
    public StringProperty fonteProperty() { return fonte; }

    // ===== SETTER =====

    public void setNome(String nome) { this.nome.set(nome); }
    public void setTipoCucina(String tipoCucina) { this.tipoCucina.set(tipoCucina); }
    public void setIndirizzo(String indirizzo) { this.indirizzo.set(indirizzo); }
    public void setFonte(String fonte) { this.fonte.set(fonte); }
}
