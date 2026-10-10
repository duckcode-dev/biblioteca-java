package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import model.Biblioteca;
import model.Libro;
import model.Usuario;
import model.Validador;

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
            List<Libro> libros = leerLibros(libroExcel.getSheet(HOJA_LIBROS));
            List<Usuario> usuarios = leerUsuarios(libroExcel.getSheet(HOJA_USUARIOS));
            validarIdsUnicos(libros.stream().map(Libro::getId).toList(), HOJA_LIBROS);
            validarIdsUnicos(usuarios.stream().map(Usuario::getId).toList(), HOJA_USUARIOS);

            for (Libro libro : libros)
                biblioteca.cargarLibro(libro);
            for (Usuario usuario : usuarios)
                biblioteca.cargarUsuario(usuario);
        }
        return true;
    }

    public void guardar(Biblioteca biblioteca) throws IOException {
        Path destino = archivo.toAbsolutePath();
        Path temporal = Files.createTempFile(destino.getParent(), destino.getFileName().toString(), ".tmp");
        try {
            try (Workbook libroExcel = new XSSFWorkbook(); OutputStream salida = Files.newOutputStream(temporal)) {
                escribirLibros(libroExcel.createSheet(HOJA_LIBROS), biblioteca);
                escribirUsuarios(libroExcel.createSheet(HOJA_USUARIOS), biblioteca);
                libroExcel.write(salida);
            }

            try {
                Files.move(temporal, destino, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (java.nio.file.AtomicMoveNotSupportedException e) {
                Files.move(temporal, destino, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporal);
        }
    }

    private List<Libro> leerLibros(Sheet hoja) throws IOException {
        validarHoja(hoja, HOJA_LIBROS);
        List<Libro> libros = new ArrayList<>();
        for (int fila = 1; fila <= hoja.getLastRowNum(); fila++) {
            Row datos = hoja.getRow(fila);
            if (datos == null)
                throw new IOException("Fila vacía en " + HOJA_LIBROS + ": " + (fila + 1));
            int id = leerEntero(datos.getCell(0), HOJA_LIBROS, fila, "ID");
            String titulo = leerTexto(datos.getCell(1), HOJA_LIBROS, fila, "Título");
            String autor = leerTexto(datos.getCell(2), HOJA_LIBROS, fila, "Autor");
            int anio = leerEntero(datos.getCell(3), HOJA_LIBROS, fila, "Año de publicación");
            if (!Validador.textoValido(titulo) || !Validador.textoValido(autor) || !Validador.anioValido(anio))
                throw new IOException("Datos inválidos en " + HOJA_LIBROS + ", fila " + (fila + 1));
            libros.add(new Libro(id, titulo, autor, anio));
        }
        return libros;
    }

    private List<Usuario> leerUsuarios(Sheet hoja) throws IOException {
        validarHoja(hoja, HOJA_USUARIOS);
        List<Usuario> usuarios = new ArrayList<>();
        for (int fila = 1; fila <= hoja.getLastRowNum(); fila++) {
            Row datos = hoja.getRow(fila);
            if (datos == null)
                throw new IOException("Fila vacía en " + HOJA_USUARIOS + ": " + (fila + 1));
            int id = leerEntero(datos.getCell(0), HOJA_USUARIOS, fila, "ID");
            String nombre = leerTexto(datos.getCell(1), HOJA_USUARIOS, fila, "Nombre");
            String telefono = leerTexto(datos.getCell(2), HOJA_USUARIOS, fila, "Teléfono");
            String email = leerTexto(datos.getCell(3), HOJA_USUARIOS, fila, "E-mail");
            if (!Validador.textoValido(nombre) || !Validador.telefonoValido(telefono) || !Validador.emailValido(email))
                throw new IOException("Datos inválidos en " + HOJA_USUARIOS + ", fila " + (fila + 1));
            usuarios.add(new Usuario(id, nombre, telefono, email));
        }
        return usuarios;
    }

    private void validarHoja(Sheet hoja, String nombre) throws IOException {
        if (hoja == null)
            throw new IOException("Falta la hoja requerida: " + nombre);
    }

    private int leerEntero(Cell celda, String hoja, int fila, String campo) throws IOException {
        if (celda == null || celda.getCellType() != CellType.NUMERIC || celda.getNumericCellValue() <= 0
                || celda.getNumericCellValue() > Integer.MAX_VALUE
                || celda.getNumericCellValue() != Math.rint(celda.getNumericCellValue()))
            throw new IOException(campo + " inválido en " + hoja + ", fila " + (fila + 1));
        return (int) celda.getNumericCellValue();
    }

    private String leerTexto(Cell celda, String hoja, int fila, String campo) throws IOException {
        if (celda == null || celda.getCellType() != CellType.STRING || celda.getStringCellValue().isBlank())
            throw new IOException(campo + " inválido en " + hoja + ", fila " + (fila + 1));
        return celda.getStringCellValue().trim();
    }

    private void validarIdsUnicos(List<Integer> ids, String hoja) throws IOException {
        if (new HashSet<>(ids).size() != ids.size())
            throw new IOException("Hay IDs duplicados en la hoja " + hoja);
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
