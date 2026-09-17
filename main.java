import java.util.List;
import java.util.Scanner;

/** Interfaz de consola de la aplicación. */
public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        try (Scanner entrada = new Scanner(System.in)) {
            boolean ejecutando = true;
            while (ejecutando) {
                mostrarMenu();
                switch (entrada.nextLine()) {
                    case "1" -> ingresarLibro(biblioteca, entrada);
                    case "2" -> buscarLibro(biblioteca, entrada);
                    case "3" -> mostrarResultados(biblioteca.listarLibros());
                    case "4" -> eliminarLibro(biblioteca, entrada);
                    case "5" -> modificarLibro(biblioteca, entrada);
                    case "6" -> ingresarUsuario(biblioteca, entrada);
                    case "7" -> mostrarUsuarios(biblioteca.listarUsuarios());
                    case "8" -> eliminarUsuario(biblioteca, entrada);
                    case "9" -> modificarUsuario(biblioteca, entrada);
                    case "10" -> ejecutando = false;
                    default -> System.out.println("Error: ingrese una opción válida.");
                }
            }
        }
        System.out.println("¡Adiós! ¡Que tengas un buen día!");
    }

    private static void mostrarMenu() {
        System.out.println("*** BIBLIOTECA DE ALEJANDRÍA ***");
        System.out.println("1. Ingresar libro\n2. Buscar libro\n3. Mostrar libros\n4. Eliminar libro por ID");
        System.out.println("5. Modificar libro\n6. Ingresar usuario\n7. Mostrar usuarios");
        System.out.println("8. Eliminar usuario por ID\n9. Modificar usuario\n10. Salir");
    }

    private static void ingresarLibro(Biblioteca biblioteca, Scanner entrada) {
        String titulo = leerTexto(entrada, "Ingrese título del libro");
        String autor = leerTexto(entrada, "Ingrese autor del libro");
        int anio = leerAnio(entrada);
        if (biblioteca.existeLibro(titulo, autor, anio)) {
            System.out.println("Error: el libro ya está ingresado.");
            return;
        }
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setAnioPublicacion(anio);
        biblioteca.agregarLibro(libro);
        System.out.println("Libro ingresado con ID: " + libro.getId());
    }

    private static void buscarLibro(Biblioteca biblioteca, Scanner entrada) {
        System.out.println("Buscar por: 1. Título  2. Autor  3. Año  4. Volver");
        switch (entrada.nextLine()) {
            case "1" -> mostrarResultados(biblioteca.buscarPorTitulo(leerTexto(entrada, "Ingrese título")));
            case "2" -> mostrarResultados(biblioteca.buscarPorAutor(leerTexto(entrada, "Ingrese autor")));
            case "3" -> mostrarResultados(biblioteca.buscarPorAnio(leerAnio(entrada)));
            case "4" -> { }
            default -> System.out.println("Error: ingrese una opción válida.");
        }
    }

    private static void eliminarLibro(Biblioteca biblioteca, Scanner entrada) {
        int id = leerId(entrada, "Ingrese ID del libro");
        var libro = biblioteca.buscarLibroPorId(id);
        if (libro.isEmpty()) {
            System.out.println("Libro no encontrado.");
        } else if (confirmar(entrada, "¿Eliminar '" + libro.get().getTitulo() + "'? (S/N)")) {
            biblioteca.eliminarLibro(id);
            System.out.println("Libro eliminado.");
        }
    }

    private static void modificarLibro(Biblioteca biblioteca, Scanner entrada) {
        int id = leerId(entrada, "Ingrese ID del libro");
        if (biblioteca.buscarLibroPorId(id).isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }
        System.out.println("Modificar: 1. Título  2. Autor  3. Año  4. Volver");
        boolean modificado = switch (entrada.nextLine()) {
            case "1" -> biblioteca.actualizarTituloLibro(id, leerTexto(entrada, "Ingrese nuevo título"));
            case "2" -> biblioteca.actualizarAutorLibro(id, leerTexto(entrada, "Ingrese nuevo autor"));
            case "3" -> biblioteca.actualizarAnioLibro(id, leerAnio(entrada));
            case "4" -> false;
            default -> { System.out.println("Error: ingrese una opción válida."); yield false; }
        };
        if (modificado) System.out.println("Libro modificado.");
    }

    private static void ingresarUsuario(Biblioteca biblioteca, Scanner entrada) {
        Usuario usuario = new Usuario();
        usuario.setNombre(leerTexto(entrada, "Ingrese nombre de usuario"));
        usuario.setTelefono(leerTelefono(entrada));
        usuario.setEmail(leerEmail(entrada));
        biblioteca.agregarUsuario(usuario);
        System.out.println("Usuario ingresado con ID: " + usuario.getId());
    }

    private static void eliminarUsuario(Biblioteca biblioteca, Scanner entrada) {
        int id = leerId(entrada, "Ingrese ID del usuario");
        var usuario = biblioteca.buscarUsuarioPorId(id);
        if (usuario.isEmpty()) {
            System.out.println("Usuario no encontrado.");
        } else if (confirmar(entrada, "¿Eliminar al usuario '" + usuario.get().getNombre() + "'? (S/N)")) {
            biblioteca.eliminarUsuario(id);
            System.out.println("Usuario eliminado.");
        }
    }

    private static void modificarUsuario(Biblioteca biblioteca, Scanner entrada) {
        int id = leerId(entrada, "Ingrese ID del usuario");
        if (biblioteca.buscarUsuarioPorId(id).isEmpty()) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        System.out.println("Modificar: 1. Nombre  2. Teléfono  3. E-mail  4. Volver");
        boolean modificado = switch (entrada.nextLine()) {
            case "1" -> biblioteca.actualizarNombreUsuario(id, leerTexto(entrada, "Ingrese nuevo nombre"));
            case "2" -> biblioteca.actualizarTelefonoUsuario(id, leerTelefono(entrada));
            case "3" -> biblioteca.actualizarEmailUsuario(id, leerEmail(entrada));
            case "4" -> false;
            default -> { System.out.println("Error: ingrese una opción válida."); yield false; }
        };
        if (modificado) System.out.println("Usuario modificado.");
    }

    private static String leerTexto(Scanner entrada, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String texto = entrada.nextLine().trim();
            if (Validador.textoValido(texto)) return texto;
            System.out.println("Error: ingrese texto válido.");
        }
    }

    private static int leerAnio(Scanner entrada) {
        while (true) {
            try {
                System.out.println("Ingrese año de publicación");
                int anio = Integer.parseInt(entrada.nextLine());
                if (Validador.anioValido(anio)) return anio;
            } catch (NumberFormatException ignored) { }
            System.out.println("Error: ingrese un año válido.");
        }
    }

    private static int leerId(Scanner entrada, String mensaje) {
        while (true) {
            try {
                System.out.println(mensaje);
                int id = Integer.parseInt(entrada.nextLine());
                if (id > 0) return id;
            } catch (NumberFormatException ignored) { }
            System.out.println("Error: ingrese un ID válido.");
        }
    }

    private static String leerTelefono(Scanner entrada) {
        while (true) {
            System.out.println("Ingrese teléfono chileno (9 dígitos; +56 opcional)");
            String telefono = entrada.nextLine();
            if (Validador.telefonoValido(telefono)) return telefono;
            System.out.println("Error: ingrese un teléfono válido.");
        }
    }

    private static String leerEmail(Scanner entrada) {
        while (true) {
            System.out.println("Ingrese e-mail");
            String email = entrada.nextLine();
            if (Validador.emailValido(email)) return email;
            System.out.println("Error: ingrese un e-mail válido.");
        }
    }

    private static boolean confirmar(Scanner entrada, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String respuesta = entrada.nextLine().trim();
            if (respuesta.equalsIgnoreCase("S")) return true;
            if (respuesta.equalsIgnoreCase("N")) return false;
            System.out.println("Responda S o N.");
        }
    }

    private static void mostrarResultados(List<Libro> resultados) {
        if (resultados.isEmpty()) System.out.println("Sin resultados.");
        else resultados.forEach(System.out::println);
    }

    private static void mostrarUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) System.out.println("Sin resultados.");
        else usuarios.forEach(System.out::println);
    }
}
