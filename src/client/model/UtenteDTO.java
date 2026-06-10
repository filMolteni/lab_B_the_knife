package client.model;

/**
 * Data Transfer Object (DTO) che rappresenta l'utente loggato nell'applicazione.
 *
 * Questa classe funge anche da **singleton**, mantenendo in memoria
 * l'utente attualmente autenticato. Viene utilizzata per:
 * - controllare i permessi (CLIENTE, GESTORE, ADMIN)
 * - mostrare/nascondere funzionalità nella UI
 * - inviare al server le informazioni dell'utente loggato
 *
 * Contiene:
 * - id dell'utente
 * - username
 * - email
 * - ruolo (CLIENTE, GESTORE, ADMIN)
 *
 * Include un costruttore vuoto necessario per la deserializzazione tramite Gson.
 */
public class UtenteDTO {

    private static UtenteDTO instance;   // <--- UTENTE LOGGATO

    private int id;
    private String username;
    private String email;
    private String ruolo;   // CLIENTE, GESTORE, ADMIN

    /**
     * Costruttore vuoto richiesto da Gson per la deserializzazione automatica.
     */
    public UtenteDTO() {
        // necessario per Gson
    }

    /**
     * Costruisce un DTO completo per rappresentare un utente.
     *
     * @param id id dell'utente
     * @param username nome utente
     * @param email email dell'utente
     * @param ruolo ruolo dell'utente (CLIENTE, GESTORE, ADMIN)
     */
    public UtenteDTO(int id, String username, String email, String ruolo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.ruolo = ruolo;
    }

    // ===== SINGLETON =====

    /**
     * Crea l'istanza dell'utente loggato.
     * Sovrascrive eventuali dati precedenti.
     *
     * @param id id dell'utente
     * @param username username dell'utente
     * @param email email dell'utente
     * @param ruolo ruolo dell'utente
     */
    public static void creaUtenteLoggato(int id, String username, String email, String ruolo) {
        instance = new UtenteDTO(id, username, email, ruolo);
    }

    /**
     * Restituisce l'utente attualmente loggato.
     *
     * @return istanza singleton dell'utente loggato, oppure null se non autenticato
     */
    public static UtenteDTO getUtenteLoggato() {
        return instance;
    }

    // ===== GETTER =====

    /** @return id dell'utente */
    public int getId() {
        return id;
    }

    /** @return username dell'utente */
    public String getUsername() {
        return username;
    }

    /** @return email dell'utente */
    public String getEmail() {
        return email;
    }

    /** @return ruolo dell'utente (CLIENTE, GESTORE, ADMIN) */
    public String getRuolo() {
        return ruolo;
    }

    /** @return true se l'utente è un gestore */
    public boolean isGestore() {
        return ruolo != null && ruolo.equalsIgnoreCase("GESTORE");
    }

    /** @return true se l'utente è un cliente */
    public boolean isCliente() {
        return ruolo != null && ruolo.equalsIgnoreCase("CLIENTE");
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
