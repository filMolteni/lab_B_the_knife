package client.model;

public class RecensioneDTO {

    private int id;
    private int idUtente;
    private int idRistorante;
    private int voto;           // da 1 a 5
    private String commento;
    private String data;        // formato ISO: yyyy-MM-dd

    public RecensioneDTO() {
        // necessario per Gson
    }

    public RecensioneDTO(int id, int idUtente, int idRistorante, int voto, String commento, String data) {
        this.id = id;
        this.idUtente = idUtente;
        this.idRistorante = idRistorante;
        this.voto = voto;
        this.commento = commento;
        this.data = data;
    }

    // ===== GETTER =====

    public int getId() {
        return id;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public int getIdRistorante() {
        return idRistorante;
    }

    public int getVoto() {
        return voto;
    }

    public String getCommento() {
        return commento;
    }

    public String getData() {
        return data;
    }

    // ===== SETTER =====

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public void setIdRistorante(int idRistorante) {
        this.idRistorante = idRistorante;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public void setData(String data) {
        this.data = data;
    }
}
