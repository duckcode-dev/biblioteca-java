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

    public ResultadoOperacion agregarLibro(String titulo, String autor, int anio) {
        if (biblioteca.existeLibro(titulo, autor, anio))
            return new ResultadoOperacion(EstadoOperacion.DUPLICADO, "El libro ya está registrado.");
        biblioteca.agregarLibro(new Libro(0, titulo, autor, anio));
        return guardar("Libro ingresado.");
    }

    public ResultadoOperacion agregarUsuario(String nombre, String telefono, String email) {
        biblioteca.agregarUsuario(new Usuario(0, nombre, telefono, email));
        return guardar("Usuario ingresado.");
    }

    public List<Libro> listarLibros() { return biblioteca.listarLibros(); }
    public List<Usuario> listarUsuarios() { return biblioteca.listarUsuarios(); }
    public List<Libro> buscarPorTitulo(String titulo) { return biblioteca.buscarPorTitulo(titulo); }
    public List<Libro> buscarPorAutor(String autor) { return biblioteca.buscarPorAutor(autor); }
    public List<Libro> buscarPorAnio(int anio) { return biblioteca.buscarPorAnio(anio); }
    public Optional<Libro> buscarLibroPorId(int id) { return biblioteca.buscarLibroPorId(id); }
    public Optional<Usuario> buscarUsuarioPorId(int id) { return biblioteca.buscarUsuarioPorId(id); }

    public ResultadoOperacion eliminarLibro(int id) { return guardarSi(biblioteca.eliminarLibro(id), "Libro eliminado."); }
    public ResultadoOperacion eliminarUsuario(int id) { return guardarSi(biblioteca.eliminarUsuario(id), "Usuario eliminado."); }
    public ResultadoOperacion actualizarTituloLibro(int id, String titulo) { return guardarSi(biblioteca.actualizarTituloLibro(id, titulo), "Libro modificado."); }
    public ResultadoOperacion actualizarAutorLibro(int id, String autor) { return guardarSi(biblioteca.actualizarAutorLibro(id, autor), "Libro modificado."); }
    public ResultadoOperacion actualizarAnioLibro(int id, int anio) { return guardarSi(biblioteca.actualizarAnioLibro(id, anio), "Libro modificado."); }
    public ResultadoOperacion actualizarNombreUsuario(int id, String nombre) { return guardarSi(biblioteca.actualizarNombreUsuario(id, nombre), "Usuario modificado."); }
    public ResultadoOperacion actualizarTelefonoUsuario(int id, String telefono) { return guardarSi(biblioteca.actualizarTelefonoUsuario(id, telefono), "Usuario modificado."); }
    public ResultadoOperacion actualizarEmailUsuario(int id, String email) { return guardarSi(biblioteca.actualizarEmailUsuario(id, email), "Usuario modificado."); }

    private ResultadoOperacion guardarSi(boolean cambio, String mensajeExito) {
        if (!cambio)
            return new ResultadoOperacion(EstadoOperacion.NO_ENCONTRADO, "Registro no encontrado.");
        return guardar(mensajeExito);
    }

    private ResultadoOperacion guardar(String mensajeExito) {
        if (!persistenciaHabilitada)
            return new ResultadoOperacion(EstadoOperacion.ERROR_PERSISTENCIA,
                    "El cambio se realizó en memoria, pero no se puede guardar el archivo Excel.");
        try {
            persistencia.guardar(biblioteca);
            return new ResultadoOperacion(EstadoOperacion.EXITO, mensajeExito);
        } catch (IOException e) {
            return new ResultadoOperacion(EstadoOperacion.ERROR_PERSISTENCIA,
                    "El cambio se realizó en memoria, pero no se pudo guardar biblioteca.xlsx.");
        }
    }
}
