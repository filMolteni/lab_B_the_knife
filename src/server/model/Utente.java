package server.model;

public class Utente {

    private int id;
    private String nome;
    private String email;
    private String password;
    private String ruolo;

    public Utente(int id, String nome, String email, String password, String ruolo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRuolo() { return ruolo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
}
