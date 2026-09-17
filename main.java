import java.nio.file.Path;

import controller.BibliotecaController;
import model.Biblioteca;
import persistence.PersistenciaExcel;
import view.ConsolaBiblioteca;

/**
 * Clase principal que inicia la aplicación de la biblioteca.
 * 
 * @author Patricio Fernández
 * @author github.com/duckcode-dev
 */
public class Main {
    public static void main(String[] args) {
        Biblioteca modelo = new Biblioteca();
        PersistenciaExcel persistencia = new PersistenciaExcel(Path.of("biblioteca.xlsx"));
        BibliotecaController controlador = new BibliotecaController(modelo, persistencia);
        new ConsolaBiblioteca(controlador).iniciar();
    }
}
