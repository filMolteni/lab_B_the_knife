package server.model;

/**
 * Modello che rappresenta la risposta del gestore a una recensione.
 *
 * Una risposta contiene:
 * - id della risposta
 * - id della recensione a cui si riferisce
 * - testo della risposta
 *
 * Questo modello viene utilizzato per:
 * - salvare la risposta nel database
 * - restituirla al client quando richiesto
 * - mostrarla nelle schermate delle recensioni ricevute
 */
public class Risposta {

    private int id;
    private int idRecensione;
    private String testo;

    /**
     * Costruisce una risposta completa.
     *
     * @param id id della risposta
     * @param idRecensione id della recensione associata
     * @param testo contenuto della risposta del gestore
     */
    public Risposta(int id, int idRecensione, String testo) {
        this.id = id;
        this.idRecensione = idRecensione;
        this.testo = testo;
    }

    // ===== GETTER =====

    /** @return id della risposta */
    public int getId() { return id; }

    /** @return id della recensione a cui la risposta appartiene */
    public int getIdRecensione() { return idRecensione; }

    /** @return testo della risposta del gestore */
    public String getTesto() { return testo; }

    // ===== SETTER =====

    /**
     * Modifica il testo della risposta.
     *
     * @param testo nuovo contenuto della risposta
     */
    public void setTesto(String testo) { this.testo = testo; }
}
