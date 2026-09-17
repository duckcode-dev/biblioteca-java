import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Gestiona el catálogo de libros y usuarios sin depender de la interfaz. */
public class Biblioteca {
    private final Map<Integer, Libro> libros = new HashMap<>();
    private final Map<Integer, Usuario> usuarios = new HashMap<>();
    private int contadorId = 1;
    private int contadorIdUser = 1;

    public void agregarLibro(Libro libro) {
        libro.setId(contadorId++);
        libros.put(libro.getId(), libro);
    }

    public void agregarUsuario(Usuario usuario) {
        usuario.setId(contadorIdUser++);
        usuarios.put(usuario.getId(), usuario);
    }

    /** Incorpora un libro existente conservando su ID al cargar persistencia. */
    public void cargarLibro(Libro libro) {
        libros.put(libro.getId(), libro);
        contadorId = Math.max(contadorId, libro.getId() + 1);
    }

    /** Incorpora un usuario existente conservando su ID al cargar persistencia. */
    public void cargarUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
        contadorIdUser = Math.max(contadorIdUser, usuario.getId() + 1);
    }

    /** Devuelve una vista de consulta que no permite alterar el catálogo. */
    public List<Libro> listarLibros() { return List.copyOf(libros.values()); }

    /** Devuelve una vista de consulta que no permite alterar los usuarios. */
    public List<Usuario> listarUsuarios() { return List.copyOf(usuarios.values()); }

    public boolean existeLibro(String titulo, String autor, int anio) {
        return libros.values().stream().anyMatch(libro -> libro.getTitulo().equalsIgnoreCase(titulo)
                && libro.getAutor().equalsIgnoreCase(autor) && libro.getAnioPublicacion() == anio);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libros.values().stream().filter(libro -> libro.getTitulo().equalsIgnoreCase(titulo)).toList();
    }

    public List<Libro> buscarPorAutor(String autor) {
        return libros.values().stream().filter(libro -> libro.getAutor().equalsIgnoreCase(autor)).toList();
    }

    public List<Libro> buscarPorAnio(int anio) {
        return libros.values().stream().filter(libro -> libro.getAnioPublicacion() == anio).toList();
    }

    public Optional<Libro> buscarLibroPorId(int id) {
        return Optional.ofNullable(libros.get(id));
    }

    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return Optional.ofNullable(usuarios.get(id));
    }

    public boolean eliminarLibro(int id) { return libros.remove(id) != null; }
    public boolean eliminarUsuario(int id) { return usuarios.remove(id) != null; }

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
        return buscarUsuarioPorId(id).map(usuario -> { usuario.setNombre(nombre); return true; }).orElse(false);
    }

    public boolean actualizarTelefonoUsuario(int id, String telefono) {
        return buscarUsuarioPorId(id).map(usuario -> { usuario.setTelefono(telefono); return true; }).orElse(false);
    }

    public boolean actualizarEmailUsuario(int id, String email) {
        return buscarUsuarioPorId(id).map(usuario -> { usuario.setEmail(email); return true; }).orElse(false);
    }
}
