package client.model;

public class UtenteDTO {

    private static UtenteDTO instance;   // <--- UTENTE LOGGATO

    private int id;
    private String username;
    private String email;
    private String ruolo;   // CLIENTE, GESTORE, ADMIN

    public UtenteDTO() {
        // necessario per Gson
    }

    public UtenteDTO(int id, String username, String email, String ruolo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.ruolo = ruolo;
    }

    // ===== SINGLETON =====

    public static void creaUtenteLoggato(int id, String username, String email, String ruolo) {
        instance = new UtenteDTO(id, username, email, ruolo);
    }

    public static UtenteDTO getUtenteLoggato() {
        return instance;
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

    public boolean isGestore() {
        return ruolo != null && ruolo.equalsIgnoreCase("GESTORE");
    }

    public boolean isCliente() {
        return ruolo != null && ruolo.equalsIgnoreCase("CLIENTE");
    }

    public boolean isAdmin() {
        return ruolo != null && ruolo.equalsIgnoreCase("ADMIN");
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
