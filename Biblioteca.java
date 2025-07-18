import java.util.HashSet;
import java.util.Set;

public class Biblioteca {

    private Set<Libro> libros = new HashSet<>();

    public Set<Libro> getLibros() {
        return libros;
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
        System.err.println("libro agregado a la lista");
    }

}
