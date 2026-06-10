package server.model;

/**
 * Modello che rappresenta un ristorante, proveniente da una delle due fonti:
 * - THEKNIFE → ristoranti ufficiali (solo lettura)
 * - UTENTE → ristoranti inseriti dai gestori
 *
 * Ogni ristorante contiene:
 * - informazioni anagrafiche (nome, indirizzo, città, nazione)
 * - coordinate geografiche (latitudine, longitudine)
 * - fascia di prezzo (1–5)
 * - tipo di cucina
 * - servizi disponibili (delivery, prenotazione)
 * - fonte del ristorante (THEKNIFE o UTENTE)
 *
 * Questo modello viene utilizzato sia lato server (DAO, Service)
 * sia lato client (DTO convertiti da questo modello).
 */
public class Ristorante {

    private int id;
    private String nome;
    private String indirizzo;
    private String citta;
    private String nazione;
    private double latitudine;
    private double longitudine;
    private int fasciaPrezzo;
    private String tipoCucina;
    private boolean delivery;
    private boolean prenotazione;

    /** Fonte del ristorante: "THEKNIFE" o "UTENTE". */
    private String fonte;

    // ============================
    // COSTRUTTORE COMPLETO
    // ============================

    /**
     * Costruisce un ristorante completo.
     *
     * @param id id del ristorante
     * @param nome nome del ristorante
     * @param indirizzo indirizzo completo
     * @param tipoCucina tipo di cucina
     * @param fasciaPrezzo fascia di prezzo (1–5)
     * @param latitudine coordinata geografica
     * @param longitudine coordinata geografica
     * @param citta città del ristorante
     * @param nazione nazione del ristorante
     * @param delivery true se offre consegna a domicilio
     * @param prenotazione true se accetta prenotazioni
     * @param fonte "THEKNIFE" o "UTENTE"
     */
    public Ristorante(int id, String nome, String indirizzo, String tipoCucina,
                      int fasciaPrezzo, double latitudine, double longitudine,
                      String citta, String nazione,
                      boolean delivery, boolean prenotazione,
                      String fonte) {

        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.tipoCucina = tipoCucina;
        this.fasciaPrezzo = fasciaPrezzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.citta = citta;
        this.nazione = nazione;
        this.delivery = delivery;
        this.prenotazione = prenotazione;
        this.fonte = fonte;
    }

    // ============================
    // GETTER
    // ============================

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getIndirizzo() { return indirizzo; }
    public String getCitta() { return citta; }
    public String getNazione() { return nazione; }
    public double getLatitudine() { return latitudine; }
    public double getLongitudine() { return longitudine; }
    public int getFasciaPrezzo() { return fasciaPrezzo; }
    public String getTipoCucina() { return tipoCucina; }
    public boolean isDelivery() { return delivery; }
    public boolean isPrenotazione() { return prenotazione; }

    /** @return fonte del ristorante ("THEKNIFE" o "UTENTE") */
    public String getFonte() { return fonte; }

    /**
     * @return true se il ristorante proviene dalla fonte THEKNIFE
     */
    public boolean isTheKnife() {
        return "THEKNIFE".equalsIgnoreCase(fonte);
    }

    // ============================
    // SETTER
    // ============================

    public void setNome(String nome) { this.nome = nome; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }
    public void setCitta(String citta) { this.citta = citta; }
    public void setNazione(String nazione) { this.nazione = nazione; }
    public void setLatitudine(double latitudine) { this.latitudine = latitudine; }
    public void setLongitudine(double longitudine) { this.longitudine = longitudine; }
    public void setFasciaPrezzo(int fasciaPrezzo) { this.fasciaPrezzo = fasciaPrezzo; }
    public void setTipoCucina(String tipoCucina) { this.tipoCucina = tipoCucina; }
    public void setDelivery(boolean delivery) { this.delivery = delivery; }
    public void setPrenotazione(boolean prenotazione) { this.prenotazione = prenotazione; }

    /** Imposta la fonte del ristorante ("THEKNIFE" o "UTENTE"). */
    public void setFonte(String fonte) { this.fonte = fonte; }
}
