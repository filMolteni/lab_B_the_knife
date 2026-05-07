package server.model;

public class Risposta {

    private int id;
    private int idRecensione;
    private String testo;

    public Risposta(int id, int idRecensione, String testo) {
        this.id = id;
        this.idRecensione = idRecensione;
        this.testo = testo;
    }

    public int getId() { return id; }
    public int getIdRecensione() { return idRecensione; }
    public String getTesto() { return testo; }

    public void setTesto(String testo) { this.testo = testo; }
}
