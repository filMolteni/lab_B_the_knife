package client.model;

/**
 * Data Transfer Object (DTO) che rappresenta una recensione.
 * Utilizzato per lo scambio dati tra client e server tramite JSON.
 *
 * Contiene:
 * - id della recensione
 * - id dell'utente autore
 * - id del ristorante recensito
 * - voto (1–5)
 * - commento testuale
 * - data in formato ISO (yyyy-MM-dd)
 *
 * Include un costruttore vuoto necessario per la deserializzazione tramite Gson.
 */
public class RecensioneDTO {

    private int id;
    private int idUtente;
    private int idRistorante;
    private int voto;           // da 1 a 5
    private String commento;
    private String data;        // formato ISO: yyyy-MM-dd

    /**
     * Costruttore vuoto richiesto da Gson per la deserializzazione automatica.
     */
    public RecensioneDTO() {
        // necessario per Gson
    }

    /**
     * Costruisce un DTO completo per rappresentare una recensione.
     *
     * @param id id della recensione
     * @param idUtente id dell'utente autore
     * @param idRistorante id del ristorante recensito
     * @param voto voto assegnato (1–5)
     * @param commento testo della recensione
     * @param data data della recensione in formato ISO (yyyy-MM-dd)
     */
    public RecensioneDTO(int id, int idUtente, int idRistorante, int voto, String commento, String data) {
        this.id = id;
        this.idUtente = idUtente;
        this.idRistorante = idRistorante;
        this.voto = voto;
        this.commento = commento;
        this.data = data;
    }

    // ===== GETTER =====

    /** @return id della recensione */
    public int getId() {
        return id;
    }

    /** @return id dell'utente autore */
    public int getIdUtente() {
        return idUtente;
    }

    /** @return id del ristorante recensito */
    public int getIdRistorante() {
        return idRistorante;
    }

    /** @return voto assegnato (1–5) */
    public int getVoto() {
        return voto;
    }

    /** @return testo della recensione */
    public String getCommento() {
        return commento;
    }

    /** @return data della recensione in formato ISO */
    public String getData() {
        return data;
    }

    // ===== SETTER =====

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public void setIdRistorante(int idRistorante) {
        this.idRistorante = idRistorante;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public void setData(String data) {
        this.data = data;
    }
}
