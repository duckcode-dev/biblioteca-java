package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import model.Biblioteca;
import model.Libro;
import model.Usuario;
import persistence.PersistenciaExcel;

/** Coordina las acciones de la vista sobre el modelo y la persistencia. */
public class BibliotecaController {
    private final Biblioteca biblioteca;
    private final PersistenciaExcel persistencia;
    private boolean persistenciaHabilitada = true;
    private String mensajePendiente = "";

    public BibliotecaController(Biblioteca biblioteca, PersistenciaExcel persistencia) {
        this.biblioteca = biblioteca;
        this.persistencia = persistencia;
    }

    public String cargarDatos() {
        try {
            return persistencia.cargar(biblioteca) ? "Datos cargados desde biblioteca.xlsx." : "";
        } catch (IOException | RuntimeException e) {
            persistenciaHabilitada = false;
            return "No se pudo leer biblioteca.xlsx. No se sobrescribirá el archivo existente.";
        }
    }

    /** Devuelve el último aviso de infraestructura para que la vista lo presente. */
    public String consumirMensaje() {
        String mensaje = mensajePendiente;
        mensajePendiente = "";
        return mensaje;
    }

    public boolean agregarLibro(String titulo, String autor, int anio) {
        if (biblioteca.existeLibro(titulo, autor, anio)) return false;
        biblioteca.agregarLibro(new Libro(0, titulo, autor, anio));
        guardar();
        return true;
    }

    public boolean agregarUsuario(String nombre, String telefono, String email) {
        biblioteca.agregarUsuario(new Usuario(0, nombre, telefono, email));
        guardar();
        return true;
    }

    public List<Libro> listarLibros() { return biblioteca.listarLibros(); }
    public List<Usuario> listarUsuarios() { return biblioteca.listarUsuarios(); }
    public List<Libro> buscarPorTitulo(String titulo) { return biblioteca.buscarPorTitulo(titulo); }
    public List<Libro> buscarPorAutor(String autor) { return biblioteca.buscarPorAutor(autor); }
    public List<Libro> buscarPorAnio(int anio) { return biblioteca.buscarPorAnio(anio); }
    public Optional<Libro> buscarLibroPorId(int id) { return biblioteca.buscarLibroPorId(id); }
    public Optional<Usuario> buscarUsuarioPorId(int id) { return biblioteca.buscarUsuarioPorId(id); }

    public boolean eliminarLibro(int id) { return guardarSi(biblioteca.eliminarLibro(id)); }
    public boolean eliminarUsuario(int id) { return guardarSi(biblioteca.eliminarUsuario(id)); }
    public boolean actualizarTituloLibro(int id, String titulo) { return guardarSi(biblioteca.actualizarTituloLibro(id, titulo)); }
    public boolean actualizarAutorLibro(int id, String autor) { return guardarSi(biblioteca.actualizarAutorLibro(id, autor)); }
    public boolean actualizarAnioLibro(int id, int anio) { return guardarSi(biblioteca.actualizarAnioLibro(id, anio)); }
    public boolean actualizarNombreUsuario(int id, String nombre) { return guardarSi(biblioteca.actualizarNombreUsuario(id, nombre)); }
    public boolean actualizarTelefonoUsuario(int id, String telefono) { return guardarSi(biblioteca.actualizarTelefonoUsuario(id, telefono)); }
    public boolean actualizarEmailUsuario(int id, String email) { return guardarSi(biblioteca.actualizarEmailUsuario(id, email)); }

    private boolean guardarSi(boolean cambio) {
        if (cambio) guardar();
        return cambio;
    }

    private void guardar() {
        if (!persistenciaHabilitada) return;
        try {
            persistencia.guardar(biblioteca);
        } catch (IOException e) {
            mensajePendiente = "No se pudieron guardar los datos en biblioteca.xlsx.";
        }
    }
}
