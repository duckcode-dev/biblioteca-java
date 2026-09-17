package view;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

import controller.BibliotecaController;
import model.Validador;

/**
 * Clase que representa la interfaz de consola de la biblioteca.
 * Permite al usuario interactuar con el sistema a través de un menú de
 * opciones.
 * 
 * @author Patricio Fernández
 * @author github.com/duckcode-dev
 */
public class ConsolaBiblioteca {
    private final BibliotecaController controlador;

    public ConsolaBiblioteca(BibliotecaController controlador) {
        this.controlador = controlador;
    }

    public void iniciar() {
        String estadoCarga = controlador.cargarDatos();
        if (!estadoCarga.isBlank())
            System.out.println(estadoCarga);
        try (Scanner entrada = new Scanner(System.in)) {
            boolean ejecutando = true;
            while (ejecutando) {
                mostrarMenu();
                switch (entrada.nextLine()) {
                    case "1" -> ingresarLibro(entrada);
                    case "2" -> buscarLibro(entrada);
                    case "3" -> mostrarLista(controlador.listarLibros());
                    case "4" -> eliminarLibro(entrada);
                    case "5" -> modificarLibro(entrada);
                    case "6" -> ingresarUsuario(entrada);
                    case "7" -> mostrarLista(controlador.listarUsuarios());
                    case "8" -> eliminarUsuario(entrada);
                    case "9" -> modificarUsuario(entrada);
                    case "10" -> ejecutando = false;
                    default -> System.out.println("Error: ingrese una opción válida.");
                }
                String mensaje = controlador.consumirMensaje();
                if (!mensaje.isBlank())
                    System.out.println(mensaje);
            }
        }
        System.out.println("¡Adiós! ¡Que tengas un buen día!");
    }

    private void mostrarMenu() {
        System.out.println("*** BIBLIOTECA DE ALEJANDRÍA ***");
        System.out.println("1. Ingresar libro\n2. Buscar libro\n3. Mostrar libros\n4. Eliminar libro por ID");
        System.out.println("5. Modificar libro\n6. Ingresar usuario\n7. Mostrar usuarios");
        System.out.println("8. Eliminar usuario por ID\n9. Modificar usuario\n10. Salir");
    }

    private void ingresarLibro(Scanner entrada) {
        String titulo = leerTexto(entrada, "Ingrese título del libro");
        String autor = leerTexto(entrada, "Ingrese autor del libro");
        int anio = leerAnio(entrada);
        System.out.println(controlador.agregarLibro(titulo, autor, anio) ? "Libro ingresado."
                : "Error: el libro ya está ingresado.");
    }

    private void buscarLibro(Scanner entrada) {
        System.out.println("Buscar por: 1. Título  2. Autor  3. Año  4. Volver");
        switch (entrada.nextLine()) {
            case "1" -> mostrarLista(controlador.buscarPorTitulo(leerTexto(entrada, "Ingrese título")));
            case "2" -> mostrarLista(controlador.buscarPorAutor(leerTexto(entrada, "Ingrese autor")));
            case "3" -> mostrarLista(controlador.buscarPorAnio(leerAnio(entrada)));
            case "4" -> {
            }
            default -> System.out.println("Error: ingrese una opción válida.");
        }
    }

    private void eliminarLibro(Scanner entrada) {
        eliminarPorId(leerId(entrada, "Ingrese ID del libro"), entrada, "Libro", controlador::buscarLibroPorId,
                controlador::eliminarLibro, libro -> "¿Eliminar el libro '" + libro.getTitulo() + "'? (S/N)");
    }

    private void modificarLibro(Scanner entrada) {
        int id = leerId(entrada, "Ingrese ID del libro");
        if (controlador.buscarLibroPorId(id).isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }
        System.out.println("Modificar: 1. Título  2. Autor  3. Año  4. Volver");
        boolean modificado = switch (entrada.nextLine()) {
            case "1" -> controlador.actualizarTituloLibro(id, leerTexto(entrada, "Ingrese nuevo título"));
            case "2" -> controlador.actualizarAutorLibro(id, leerTexto(entrada, "Ingrese nuevo autor"));
            case "3" -> controlador.actualizarAnioLibro(id, leerAnio(entrada));
            case "4" -> false;
            default -> {
                System.out.println("Error: ingrese una opción válida.");
                yield false;
            }
        };
        if (modificado)
            System.out.println("Libro modificado.");
    }

    private void ingresarUsuario(Scanner entrada) {
        controlador.agregarUsuario(leerTexto(entrada, "Ingrese nombre de usuario"), leerTelefono(entrada),
                leerEmail(entrada));
        System.out.println("Usuario ingresado.");
    }

    private void eliminarUsuario(Scanner entrada) {
        eliminarPorId(leerId(entrada, "Ingrese ID del usuario"), entrada, "Usuario", controlador::buscarUsuarioPorId,
                controlador::eliminarUsuario, usuario -> "¿Eliminar al usuario '" + usuario.getNombre() + "'? (S/N)");
    }

    private void modificarUsuario(Scanner entrada) {
        int id = leerId(entrada, "Ingrese ID del usuario");
        if (controlador.buscarUsuarioPorId(id).isEmpty()) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        System.out.println("Modificar: 1. Nombre  2. Teléfono  3. E-mail  4. Volver");
        boolean modificado = switch (entrada.nextLine()) {
            case "1" -> controlador.actualizarNombreUsuario(id, leerTexto(entrada, "Ingrese nuevo nombre"));
            case "2" -> controlador.actualizarTelefonoUsuario(id, leerTelefono(entrada));
            case "3" -> controlador.actualizarEmailUsuario(id, leerEmail(entrada));
            case "4" -> false;
            default -> {
                System.out.println("Error: ingrese una opción válida.");
                yield false;
            }
        };
        if (modificado)
            System.out.println("Usuario modificado.");
    }

    private String leerTexto(Scanner entrada, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String valor = entrada.nextLine().trim();
            if (Validador.textoValido(valor))
                return valor;
            System.out.println("Error: ingrese texto válido.");
        }
    }

    private int leerAnio(Scanner entrada) {
        while (true)
            try {
                System.out.println("Ingrese año de publicación");
                int valor = Integer.parseInt(entrada.nextLine());
                if (Validador.anioValido(valor))
                    return valor;
                System.out.println("Error: ingrese un año válido.");
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un año válido.");
            }
    }

    private int leerId(Scanner entrada, String mensaje) {
        while (true)
            try {
                System.out.println(mensaje);
                int valor = Integer.parseInt(entrada.nextLine());
                if (valor > 0)
                    return valor;
                System.out.println("Error: ingrese un ID válido.");
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un ID válido.");
            }
    }

    private String leerTelefono(Scanner entrada) {
        while (true) {
            System.out.println("Ingrese teléfono chileno (9 dígitos; +56 opcional)");
            String valor = entrada.nextLine();
            if (Validador.telefonoValido(valor))
                return valor;
            System.out.println("Error: ingrese un teléfono válido.");
        }
    }

    private String leerEmail(Scanner entrada) {
        while (true) {
            System.out.println("Ingrese e-mail");
            String valor = entrada.nextLine();
            if (Validador.emailValido(valor))
                return valor;
            System.out.println("Error: ingrese un e-mail válido.");
        }
    }

    private boolean confirmar(Scanner entrada, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String respuesta = entrada.nextLine().trim();
            if (respuesta.equalsIgnoreCase("S"))
                return true;
            if (respuesta.equalsIgnoreCase("N"))
                return false;
            System.out.println("Responda S o N.");
        }
    }

    private <T> void eliminarPorId(int id, Scanner entrada, String tipo, Function<Integer, Optional<T>> buscar,
            Function<Integer, Boolean> eliminar, Function<T, String> mensaje) {
        Optional<T> entidad = buscar.apply(id);
        if (entidad.isEmpty())
            System.out.println(tipo + " no encontrado.");
        else if (confirmar(entrada, mensaje.apply(entidad.get())) && eliminar.apply(id))
            System.out.println(tipo + " eliminado.");
    }

    private void mostrarLista(List<?> elementos) {
        if (elementos.isEmpty())
            System.out.println("Sin resultados.");
        else
            elementos.forEach(System.out::println);
    }
}
