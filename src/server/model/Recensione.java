package server.model;

public class Recensione {

    private int id;
    private int idUtente;
    private int idRistorante;
    private int voto;
    private String testo;
    private String data;
    private String fonte;            
    private String nomeRistorante;   
    private String nomeUtente;       // ⭐ NUOVO

    // ============================
    // COSTRUTTORE SENZA nomeUtente (recensioni anonime)
    // ============================
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
        this.nomeUtente = nomeUtente; // ⭐ NUOVO
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
    public String getNomeUtente() { return nomeUtente; } // ⭐ NUOVO

    // ===== SETTER =====
    public void setVoto(int voto) { this.voto = voto; }
    public void setTesto(String testo) { this.testo = testo; }
    public void setData(String data) { this.data = data; }
    public void setFonte(String fonte) { this.fonte = fonte; }
    public void setNomeRistorante(String nomeRistorante) { this.nomeRistorante = nomeRistorante; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; } // ⭐ NUOVO
}
