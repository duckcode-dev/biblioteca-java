package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Biblioteca;
import model.Libro;
import model.Usuario;

/**
 * Clase que maneja la persistencia de datos de la biblioteca en un archivo
 * Excel.
 * Permite cargar y guardar libros y usuarios desde y hacia un archivo Excel.
 * 
 * @author Patricio Fernández
 * @author github.com/duckcode-dev
 */
public class PersistenciaExcel {
    private static final String HOJA_LIBROS = "Libros";
    private static final String HOJA_USUARIOS = "Usuarios";
    private final Path archivo;

    public PersistenciaExcel(Path archivo) {
        this.archivo = archivo;
    }

    public boolean cargar(Biblioteca biblioteca) throws IOException {
        if (Files.notExists(archivo))
            return false;
        try (InputStream entrada = Files.newInputStream(archivo);
                Workbook libroExcel = WorkbookFactory.create(entrada)) {
            cargarLibros(libroExcel.getSheet(HOJA_LIBROS), biblioteca);
            cargarUsuarios(libroExcel.getSheet(HOJA_USUARIOS), biblioteca);
        }
        return true;
    }

    public void guardar(Biblioteca biblioteca) throws IOException {
        try (Workbook libroExcel = new XSSFWorkbook(); OutputStream salida = Files.newOutputStream(archivo)) {
            escribirLibros(libroExcel.createSheet(HOJA_LIBROS), biblioteca);
            escribirUsuarios(libroExcel.createSheet(HOJA_USUARIOS), biblioteca);
            libroExcel.write(salida);
        }
    }

    private void cargarLibros(Sheet hoja, Biblioteca biblioteca) {
        if (hoja == null)
            return;
        DataFormatter formato = new DataFormatter();
        for (int fila = 1; fila <= hoja.getLastRowNum(); fila++) {
            Row datos = hoja.getRow(fila);
            if (datos == null || datos.getCell(0) == null)
                continue;
            biblioteca.cargarLibro(new Libro((int) datos.getCell(0).getNumericCellValue(),
                    formato.formatCellValue(datos.getCell(1)), formato.formatCellValue(datos.getCell(2)),
                    (int) datos.getCell(3).getNumericCellValue()));
        }
    }

    private void cargarUsuarios(Sheet hoja, Biblioteca biblioteca) {
        if (hoja == null)
            return;
        DataFormatter formato = new DataFormatter();
        for (int fila = 1; fila <= hoja.getLastRowNum(); fila++) {
            Row datos = hoja.getRow(fila);
            if (datos == null || datos.getCell(0) == null)
                continue;
            biblioteca.cargarUsuario(new Usuario((int) datos.getCell(0).getNumericCellValue(),
                    formato.formatCellValue(datos.getCell(1)), formato.formatCellValue(datos.getCell(2)),
                    formato.formatCellValue(datos.getCell(3))));
        }
    }

    private void escribirLibros(Sheet hoja, Biblioteca biblioteca) {
        escribirEncabezados(hoja, "ID", "Título", "Autor", "Año de publicación");
        int fila = 1;
        for (Libro libro : biblioteca.listarLibros()) {
            Row datos = hoja.createRow(fila++);
            datos.createCell(0).setCellValue(libro.getId());
            datos.createCell(1).setCellValue(libro.getTitulo());
            datos.createCell(2).setCellValue(libro.getAutor());
            datos.createCell(3).setCellValue(libro.getAnioPublicacion());
        }
    }

    private void escribirUsuarios(Sheet hoja, Biblioteca biblioteca) {
        escribirEncabezados(hoja, "ID", "Nombre", "Teléfono", "E-mail");
        int fila = 1;
        for (Usuario usuario : biblioteca.listarUsuarios()) {
            Row datos = hoja.createRow(fila++);
            datos.createCell(0).setCellValue(usuario.getId());
            datos.createCell(1).setCellValue(usuario.getNombre());
            datos.createCell(2).setCellValue(usuario.getTelefono());
            datos.createCell(3).setCellValue(usuario.getEmail());
        }
    }

    private void escribirEncabezados(Sheet hoja, String... encabezados) {
        Row fila = hoja.createRow(0);
        for (int columna = 0; columna < encabezados.length; columna++)
            fila.createCell(columna).setCellValue(encabezados[columna]);
    }
}
