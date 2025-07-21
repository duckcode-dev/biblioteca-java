
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anioPublicacion;

    // getter and seteer
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public boolean validaString(String texto) {
        return texto.matches("[a-zA-Z]+");
    }

    @Override
    public String toString() {
        return "Libro : " +
                "ID='" + id + '\'' +
                " titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", año=" + anioPublicacion;
    }

}
