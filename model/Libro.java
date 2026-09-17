package model;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anioPublicacion;

    public Libro() { }

    public Libro(int id, String titulo, String autor, int anioPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
    }

    public int getId() { return id; }
    void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    @Override
    public String toString() {
        return "Libro: ID='" + id + '\'' + " titulo='" + titulo + '\''
                + ", autor='" + autor + '\'' + ", año=" + anioPublicacion;
    }
}
