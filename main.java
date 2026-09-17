import java.nio.file.Path;

import controller.BibliotecaController;
import model.Biblioteca;
import persistence.PersistenciaExcel;
import view.ConsolaBiblioteca;

/** Punto de arranque de la aplicación. */
public class Main {
    public static void main(String[] args) {
        Biblioteca modelo = new Biblioteca();
        PersistenciaExcel persistencia = new PersistenciaExcel(Path.of("biblioteca.xlsx"));
        BibliotecaController controlador = new BibliotecaController(modelo, persistencia);
        new ConsolaBiblioteca(controlador).iniciar();
    }
}
