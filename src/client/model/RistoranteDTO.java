package client.model;

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

    public RistoranteDTO() {
        // necessario per Gson
    }

    public RistoranteDTO(int id, String nome, String indirizzo, String tipoCucina,
                         int fasciaPrezzo, double latitudine, double longitudine,
                         String citta, String nazione,
                         boolean delivery, boolean prenotazione) {

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
    }

    // ===== GETTER =====

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
}
