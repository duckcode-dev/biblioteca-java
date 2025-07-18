import java.util.HashSet;
import java.util.Set;

public class Biblioteca {

    private Set<Libro> libros = new HashSet<>();
    private int contadorId = 1; // contador para IDs

    public Set<Libro> getLibros() {
        return libros;
    }

    public void agregarLibro(Libro libro) {
        libro.setId(contadorId++);
        libros.add(libro);
        System.err.println("libro agregado a la lista con id: " + libro.getId());
    }

}
