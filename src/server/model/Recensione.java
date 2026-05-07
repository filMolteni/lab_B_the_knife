package server.model;

public class Recensione {

    private int id;
    private int idUtente;
    private int idRistorante;
    private int voto;
    private String testo;

    public Recensione(int id, int idUtente, int idRistorante, int voto, String testo) {
        this.id = id;
        this.idUtente = idUtente;
        this.idRistorante = idRistorante;
        this.voto = voto;
        this.testo = testo;
    }

    public int getId() { return id; }
    public int getIdUtente() { return idUtente; }
    public int getIdRistorante() { return idRistorante; }
    public int getVoto() { return voto; }
    public String getTesto() { return testo; }

    public void setVoto(int voto) { this.voto = voto; }
    public void setTesto(String testo) { this.testo = testo; }
}
