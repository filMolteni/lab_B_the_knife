package client.model;

public class UtenteDTO {

    private int id;
    private String username;
    private String email;
    private String ruolo;   // es: "CLIENTE", "GESTORE", "ADMIN"

    public UtenteDTO() {
        // necessario per Gson
    }

    public UtenteDTO(int id, String username, String email, String ruolo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.ruolo = ruolo;
    }

    // ===== GETTER =====

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRuolo() {
        return ruolo;
    }

    // ===== SETTER =====

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
