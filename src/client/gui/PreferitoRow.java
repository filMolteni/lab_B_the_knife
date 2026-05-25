package client.gui;

import javafx.beans.property.*;

/**
 * Modello dati per rappresentare un ristorante salvato nei preferiti.
 * Contiene tutte le informazioni necessarie per popolare la tabella dei preferiti:
 * - dati anagrafici del ristorante
 * - servizi disponibili (delivery, prenotazione)
 * - fascia prezzo
 * - fonte del ristorante (THEKNIFE o UTENTE)
 *
 * Ogni campo è gestito tramite proprietà JavaFX per supportare il binding nella UI.
 */
public class PreferitoRow {

    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty indirizzo;
    private final StringProperty citta;
    private final StringProperty nazione;
    private final StringProperty tipoCucina;
    private final IntegerProperty fasciaPrezzo;
    private final BooleanProperty delivery;
    private final BooleanProperty prenotazione;

    private final StringProperty fonte; // ⭐ NUOVO

    /**
     * Costruisce un oggetto rappresentante un ristorante nei preferiti.
     *
     * @param id id del ristorante
     * @param nome nome del ristorante
     * @param indirizzo indirizzo completo
     * @param citta città del ristorante
     * @param nazione nazione del ristorante
     * @param tipoCucina tipo di cucina
     * @param fasciaPrezzo fascia di prezzo (1–5)
     * @param delivery true se il ristorante offre consegna
     * @param prenotazione true se il ristorante accetta prenotazioni
     * @param fonte indica se il ristorante proviene da THEKNIFE o UTENTE
     */
    public PreferitoRow(int id, String nome, String indirizzo, String citta, String nazione,
                        String tipoCucina, int fasciaPrezzo, boolean delivery, boolean prenotazione,
                        String fonte) {

        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.indirizzo = new SimpleStringProperty(indirizzo);
        this.citta = new SimpleStringProperty(citta);
        this.nazione = new SimpleStringProperty(nazione);
        this.tipoCucina = new SimpleStringProperty(tipoCucina);
        this.fasciaPrezzo = new SimpleIntegerProperty(fasciaPrezzo);
        this.delivery = new SimpleBooleanProperty(delivery);
        this.prenotazione = new SimpleBooleanProperty(prenotazione);

        this.fonte = new SimpleStringProperty(fonte); // ⭐ NUOVO
    }

    // ===== GETTER =====

    /** @return id del ristorante */
    public int getId() { return id.get(); }

    /** @return nome del ristorante */
    public String getNome() { return nome.get(); }

    /** @return indirizzo del ristorante */
    public String getIndirizzo() { return indirizzo.get(); }

    /** @return città del ristorante */
    public String getCitta() { return citta.get(); }

    /** @return nazione del ristorante */
    public String getNazione() { return nazione.get(); }

    /** @return tipo di cucina */
    public String getTipoCucina() { return tipoCucina.get(); }

    /** @return fascia prezzo */
    public int getFasciaPrezzo() { return fasciaPrezzo.get(); }

    /** @return true se offre delivery */
    public boolean isDelivery() { return delivery.get(); }

    /** @return true se accetta prenotazioni */
    public boolean isPrenotazione() { return prenotazione.get(); }

    /** @return fonte del ristorante (THEKNIFE o UTENTE) */
    public String getFonte() { return fonte.get(); }

    // ===== PROPERTY =====

    public IntegerProperty idProperty() { return id; }
    public StringProperty nomeProperty() { return nome; }
    public StringProperty indirizzoProperty() { return indirizzo; }
    public StringProperty cittaProperty() { return citta; }
    public StringProperty nazioneProperty() { return nazione; }
    public StringProperty tipoCucinaProperty() { return tipoCucina; }
    public IntegerProperty fasciaPrezzoProperty() { return fasciaPrezzo; }
    public BooleanProperty deliveryProperty() { return delivery; }
    public BooleanProperty prenotazioneProperty() { return prenotazione; }
    public StringProperty fonteProperty() { return fonte; }

    // ===== SETTER =====

    public void setNome(String nome) { this.nome.set(nome); }
    public void setIndirizzo(String indirizzo) { this.indirizzo.set(indirizzo); }
    public void setCitta(String citta) { this.citta.set(citta); }
    public void setNazione(String nazione) { this.nazione.set(nazione); }
    public void setTipoCucina(String tipoCucina) { this.tipoCucina.set(tipoCucina); }
    public void setFasciaPrezzo(int fasciaPrezzo) { this.fasciaPrezzo.set(fasciaPrezzo); }
    public void setDelivery(boolean delivery) { this.delivery.set(delivery); }
    public void setPrenotazione(boolean prenotazione) { this.prenotazione.set(prenotazione); }
    public void setFonte(String fonte) { this.fonte.set(fonte); }
}
