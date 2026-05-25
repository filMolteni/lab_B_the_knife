package server.model;

/**
 * Modello che rappresenta una recensione di un ristorante.
 *
 * Una recensione contiene:
 * - id della recensione
 * - id dell'utente che l'ha scritta
 * - id del ristorante recensito
 * - voto (1–5)
 * - testo della recensione
 * - data in formato stringa
 * - fonte del ristorante (THEKNIFE o UTENTE)
 * - nome del ristorante
 * - nome dell'utente (solo per recensioni NON anonime)
 *
 * La classe fornisce due costruttori:
 * - uno per recensioni anonime (nomeUtente = null)
 * - uno per recensioni non anonime
 */
public class Recensione {

    private int id;
    private int idUtente;
    private int idRistorante;
    private int voto;
    private String testo;
    private String data;
    private String fonte;
    private String nomeRistorante;
    private String nomeUtente; // ⭐ NUOVO

    // ============================
    // COSTRUTTORE SENZA nomeUtente (recensioni anonime)
    // ============================

    /**
     * Costruisce una recensione anonima (senza nome utente).
     *
     * @param id id della recensione
     * @param idUtente autore della recensione
     * @param idRistorante ristorante recensito
     * @param voto voto assegnato (1–5)
     * @param testo testo della recensione
     * @param data data della recensione
     * @param fonte fonte del ristorante (THEKNIFE o UTENTE)
     * @param nomeRistorante nome del ristorante
     */
    public Recensione(int id, int idUtente, int idRistorante,
                      int voto, String testo, String data,
                      String fonte, String nomeRistorante) {

        this.id = id;
        this.idUtente = idUtente;
        this.idRistorante = idRistorante;
        this.voto = voto;
        this.testo = testo;
        this.data = data;
        this.fonte = fonte;
        this.nomeRistorante = nomeRistorante;
        this.nomeUtente = null; // anonimo
    }

    // ============================
    // COSTRUTTORE CON nomeUtente (recensioni NON anonime)
    // ============================

    /**
     * Costruisce una recensione non anonima (con nome utente).
     *
     * @param id id della recensione
     * @param idUtente autore della recensione
     * @param idRistorante ristorante recensito
     * @param voto voto assegnato (1–5)
     * @param testo testo della recensione
     * @param data data della recensione
     * @param fonte fonte del ristorante (THEKNIFE o UTENTE)
     * @param nomeRistorante nome del ristorante
     * @param nomeUtente nome dell'autore della recensione
     */
    public Recensione(int id, int idUtente, int idRistorante,
                      int voto, String testo, String data,
                      String fonte, String nomeRistorante,
                      String nomeUtente) {

        this.id = id;
        this.idUtente = idUtente;
        this.idRistorante = idRistorante;
        this.voto = voto;
        this.testo = testo;
        this.data = data;
        this.fonte = fonte;
        this.nomeRistorante = nomeRistorante;
        this.nomeUtente = nomeUtente;
    }

    // ===== GETTER =====

    public int getId() { return id; }
    public int getIdUtente() { return idUtente; }
    public int getIdRistorante() { return idRistorante; }
    public int getVoto() { return voto; }
    public String getTesto() { return testo; }
    public String getData() { return data; }
    public String getFonte() { return fonte; }
    public String getNomeRistorante() { return nomeRistorante; }
    public String getNomeUtente() { return nomeUtente; }

    // ===== SETTER =====

    public void setVoto(int voto) { this.voto = voto; }
    public void setTesto(String testo) { this.testo = testo; }
    public void setData(String data) { this.data = data; }
    public void setFonte(String fonte) { this.fonte = fonte; }
    public void setNomeRistorante(String nomeRistorante) { this.nomeRistorante = nomeRistorante; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }
}
