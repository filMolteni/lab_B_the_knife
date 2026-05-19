package client.gui;

import javafx.beans.property.*;

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
    public int getId() { return id.get(); }
    public String getNome() { return nome.get(); }
    public String getIndirizzo() { return indirizzo.get(); }
    public String getCitta() { return citta.get(); }
    public String getNazione() { return nazione.get(); }
    public String getTipoCucina() { return tipoCucina.get(); }
    public int getFasciaPrezzo() { return fasciaPrezzo.get(); }
    public boolean isDelivery() { return delivery.get(); }
    public boolean isPrenotazione() { return prenotazione.get(); }
    public String getFonte() { return fonte.get(); } // ⭐ NUOVO

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
    public StringProperty fonteProperty() { return fonte; } // ⭐ NUOVO

    // ===== SETTER =====
    public void setNome(String nome) { this.nome.set(nome); }
    public void setIndirizzo(String indirizzo) { this.indirizzo.set(indirizzo); }
    public void setCitta(String citta) { this.citta.set(citta); }
    public void setNazione(String nazione) { this.nazione.set(nazione); }
    public void setTipoCucina(String tipoCucina) { this.tipoCucina.set(tipoCucina); }
    public void setFasciaPrezzo(int fasciaPrezzo) { this.fasciaPrezzo.set(fasciaPrezzo); }
    public void setDelivery(boolean delivery) { this.delivery.set(delivery); }
    public void setPrenotazione(boolean prenotazione) { this.prenotazione.set(prenotazione); }
    public void setFonte(String fonte) { this.fonte.set(fonte); } // ⭐ NUOVO
}
