package client.model;

/**
 * Data Transfer Object (DTO) che rappresenta un ristorante.
 * Utilizzato per lo scambio dati tra client e server tramite JSON.
 *
 * Contiene:
 * - informazioni anagrafiche (nome, indirizzo, città, nazione)
 * - coordinate geografiche (latitudine, longitudine)
 * - fascia di prezzo (1–5)
 * - tipo di cucina
 * - servizi disponibili (delivery, prenotazione)
 * - fonte del ristorante (THEKNIFE o UTENTE)
 *
 * Include un costruttore vuoto necessario per la deserializzazione tramite Gson.
 */
public class RistoranteDTO {

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

    // NUOVO CAMPO: indica la provenienza del ristorante
    private String fonte; // "THEKNIFE" oppure "UTENTE"

    /**
     * Costruttore vuoto richiesto da Gson per la deserializzazione automatica.
     */
    public RistoranteDTO() {
        // necessario per Gson
    }

    /**
     * Costruisce un DTO completo per rappresentare un ristorante.
     *
     * @param id id del ristorante
     * @param nome nome del ristorante
     * @param indirizzo indirizzo completo
     * @param tipoCucina tipo di cucina
     * @param fasciaPrezzo fascia di prezzo (1–5)
     * @param latitudine coordinata geografica
     * @param longitudine coordinata geografica
     * @param citta città
     * @param nazione nazione
     * @param delivery true se il ristorante offre delivery
     * @param prenotazione true se il ristorante accetta prenotazioni
     * @param fonte provenienza del ristorante (THEKNIFE o UTENTE)
     */
    public RistoranteDTO(int id, String nome, String indirizzo, String tipoCucina,
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

    // ===== GETTER =====

    /** @return id del ristorante */
    public int getId() { return id; }

    /** @return nome del ristorante */
    public String getNome() { return nome; }

    /** @return indirizzo del ristorante */
    public String getIndirizzo() { return indirizzo; }

    /** @return città del ristorante */
    public String getCitta() { return citta; }

    /** @return nazione del ristorante */
    public String getNazione() { return nazione; }

    /** @return latitudine */
    public double getLatitudine() { return latitudine; }

    /** @return longitudine */
    public double getLongitudine() { return longitudine; }

    /** @return fascia di prezzo (1–5) */
    public int getFasciaPrezzo() { return fasciaPrezzo; }

    /** @return tipo di cucina */
    public String getTipoCucina() { return tipoCucina; }

    /** @return true se il ristorante offre delivery */
    public boolean isDelivery() { return delivery; }

    /** @return true se il ristorante accetta prenotazioni */
    public boolean isPrenotazione() { return prenotazione; }

    /** @return fonte del ristorante (THEKNIFE o UTENTE) */
    public String getFonte() { return fonte; }

    /** @return true se la fonte è THEKNIFE */
    public boolean isTheKnife() { return "THEKNIFE".equalsIgnoreCase(fonte); }

    /** @return true se la fonte è UTENTE */
    public boolean isUtente() { return "UTENTE".equalsIgnoreCase(fonte); }

    // ===== SETTER =====

    public void setId(int id) { this.id = id; }
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
    public void setFonte(String fonte) { this.fonte = fonte; }
}
