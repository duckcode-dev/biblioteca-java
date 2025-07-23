import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Descripción: Clase de donde se obtienen objetos que almacenan instancias de
 * Libros.
 * Nombre del archivo: Bibliote.java
 * 
 * @author: duckcode-dev
 * @version 1.0
 */
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

    public boolean buscarLibro(int id, Set<Libro> libros) {
        boolean validador = false;
        for (Libro libroActual : libros) {
            if (libroActual.getId() == id) {
                System.out.println("libro encontrado!! :");
                System.out.println(libroActual);
                validador = true;
            }
        }
        return validador;
    }

    public boolean eliminarLibro(int id, Set<Libro> libros) {
        boolean validador = false;
        Iterator<Libro> it = libros.iterator();
        while (it.hasNext()) {
            Libro libroActual = it.next();
            if (libroActual.getId() == id) {
                it.remove();
                validador = true;
            }
        }
        return validador;
    }

}
