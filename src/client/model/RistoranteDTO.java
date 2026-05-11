package client.model;

public class RistoranteDTO {

    private int id;
    private String nome;
    private String indirizzo;
    private String categoria;
    private String descrizione;

    public RistoranteDTO() {
        // necessario per Gson
    }

    public RistoranteDTO(int id, String nome, String indirizzo, String categoria, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.categoria = categoria;
        this.descrizione = descrizione;
    }

    // ===== GETTER =====

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    // ===== SETTER =====

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}

