package model;

import java.time.Year;
import java.util.regex.Pattern;

/**
 * Clase que proporciona métodos de validación para datos de libros y usuarios.
 * Incluye validaciones para texto, año, teléfono y correo electrónico.
 * 
 * @author Patricio Fernández
 * @author github.com/duckcode-dev
 */

public final class Validador {
    private static final Pattern TEXTO = Pattern.compile("[\\p{L}\\p{N}][\\p{L}\\p{N} .,'’:-]*");
    private static final Pattern TELEFONO_CHILE = Pattern.compile("(?:\\+56)?9\\d{8}");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private Validador() {
    }

    public static boolean textoValido(String texto) {
        return texto != null && !texto.isBlank() && TEXTO.matcher(texto.trim()).matches();
    }

    public static boolean anioValido(int anio) {
        return anio > 0 && anio <= Year.now().getValue();
    }

    public static boolean telefonoValido(String telefono) {
        return telefono != null && TELEFONO_CHILE.matcher(telefono).matches();
    }

    public static boolean emailValido(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }
}
