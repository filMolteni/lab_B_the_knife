package client.gui;

/**
 * Modello dati per rappresentare un utente dell'applicazione.
 * Può essere un cliente o un gestore di ristoranti.
 */
public class Utente {

    private int id;
    private String nome;
    private String email;
    private String password;
    private String ruolo; // "cliente" o "gestore"

    // ============================
    // COSTRUTTORE
    // ============================
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
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }

    // ============================
    // SETTER (facoltativi)
    // ============================
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
