package client.gui;

/**
 * Modello dati che rappresenta un utente dell'applicazione.
 * Un utente può avere uno dei due ruoli:
 * - "cliente"
 * - "gestore"
 *
 * Contiene informazioni essenziali per autenticazione, autorizzazione
 * e visualizzazione dei dati nelle varie schermate.
 */
public class Utente {

    private int id;
    private String nome;
    private String email;
    private String password;
    private String ruolo; // "cliente" o "gestore"

    /**
     * Costruisce un oggetto Utente con tutti i campi necessari.
     *
     * @param id identificativo univoco dell'utente
     * @param nome nome dell'utente
     * @param email email dell'utente
     * @param password password dell'utente
     * @param ruolo ruolo dell'utente ("cliente" o "gestore")
     */
    public Utente(int id, String nome, String email, String password, String ruolo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    // ============================
    // GETTER
    // ============================

    /** @return id dell'utente */
    public int getId() {
        return id;
    }

    /** @return nome dell'utente */
    public String getNome() {
        return nome;
    }

    /** @return email dell'utente */
    public String getEmail() {
        return email;
    }

    /** @return password dell'utente */
    public String getPassword() {
        return password;
    }

    /** @return ruolo dell'utente ("cliente" o "gestore") */
    public String getRuolo() {
        return ruolo;
    }

    // ============================
    // SETTER (facoltativi)
    // ============================

    /** Imposta un nuovo nome */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Imposta una nuova email */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Imposta una nuova password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Imposta un nuovo ruolo ("cliente" o "gestore") */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    // ============================
    // TO STRING (utile per debug)
    // ============================

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}
