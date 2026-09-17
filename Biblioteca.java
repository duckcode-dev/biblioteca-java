import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Gestiona el catálogo de libros y usuarios sin depender de la interfaz. */
public class Biblioteca {
    private final Set<Libro> libros = new HashSet<>();
    private final Set<Usuario> usuarios = new HashSet<>();
    private int contadorId = 1;
    private int contadorIdUser = 1;

    public void agregarLibro(Libro libro) {
        libro.setId(contadorId++);
        libros.add(libro);
    }

    public void agregarUsuario(Usuario usuario) {
        usuario.setId(contadorIdUser++);
        usuarios.add(usuario);
    }

    public List<Libro> listarLibros() { return libros.stream().toList(); }
    public List<Usuario> listarUsuarios() { return usuarios.stream().toList(); }

    public boolean existeLibro(String titulo, String autor, int anio) {
        return libros.stream().anyMatch(libro -> libro.getTitulo().equalsIgnoreCase(titulo)
                && libro.getAutor().equalsIgnoreCase(autor) && libro.getAnioPublicacion() == anio);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libros.stream().filter(libro -> libro.getTitulo().equalsIgnoreCase(titulo)).toList();
    }

    public List<Libro> buscarPorAutor(String autor) {
        return libros.stream().filter(libro -> libro.getAutor().equalsIgnoreCase(autor)).toList();
    }

    public List<Libro> buscarPorAnio(int anio) {
        return libros.stream().filter(libro -> libro.getAnioPublicacion() == anio).toList();
    }

    public Optional<Libro> buscarLibroPorId(int id) {
        return libros.stream().filter(libro -> libro.getId() == id).findFirst();
    }

    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return usuarios.stream().filter(usuario -> usuario.getId() == id).findFirst();
    }

    public boolean eliminarLibro(int id) { return libros.removeIf(libro -> libro.getId() == id); }
    public boolean eliminarUsuario(int id) { return usuarios.removeIf(usuario -> usuario.getId() == id); }

    public boolean actualizarTituloLibro(int id, String titulo) {
        return buscarLibroPorId(id).map(libro -> { libro.setTitulo(titulo); return true; }).orElse(false);
    }

    public boolean actualizarAutorLibro(int id, String autor) {
        return buscarLibroPorId(id).map(libro -> { libro.setAutor(autor); return true; }).orElse(false);
    }

    public boolean actualizarAnioLibro(int id, int anio) {
        return buscarLibroPorId(id).map(libro -> { libro.setAnioPublicacion(anio); return true; }).orElse(false);
    }

    public boolean actualizarNombreUsuario(int id, String nombre) {
        return buscarUsuarioPorId(id).map(usuario -> { usuario.setName(nombre); return true; }).orElse(false);
    }

    public boolean actualizarTelefonoUsuario(int id, String telefono) {
        return buscarUsuarioPorId(id).map(usuario -> { usuario.setFoneNumber(telefono); return true; }).orElse(false);
    }

    public boolean actualizarEmailUsuario(int id, String email) {
        return buscarUsuarioPorId(id).map(usuario -> { usuario.setEmail(email); return true; }).orElse(false);
    }
}
