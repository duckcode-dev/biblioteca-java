import java.util.Scanner;

/**
 * Descripción: Punto de entrada principal para la aplicación de biblioteca.
 * Nombre del archivo: Main.java
 * 
 * @author: duckcode-dev
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {

        Scanner valorIngresado = new Scanner(System.in);

        boolean menuValida = false;
        String opcionSeleccionada = "";
        int idLibro = 0;
        boolean validaTitulo = false;
        boolean validaAnio = false;
        boolean validaAutor = false;
        boolean validaLibro = false;

        // objetos
        // Libro libro = new Libro();
        Biblioteca biblioteca = new Biblioteca();

        while (menuValida == false) {

            System.out.println("BIBLIOTECA MUNICIPAL");
            System.out.println("1.Ingresar libro");
            System.out.println("2.Buscar libro");
            System.out.println("3.Mostrar todos los libros");
            System.out.println("4.Eliminar Libro");
            System.out.println("5.salir");
            opcionSeleccionada = valorIngresado.nextLine();
            switch (opcionSeleccionada) {
                case "1":
                    Libro libro = new Libro();
                    do {
                        System.out.println("ingresar título de libro");
                        String tituloIngresado = valorIngresado.nextLine();
                        validaTitulo = libro.validaString(tituloIngresado);
                        if (validaTitulo == false) {
                            System.out.println("error! ingresar valor válido!");
                        } else {
                            libro.setTitulo(tituloIngresado);
                        }
                    } while (validaTitulo == false);
                    do {
                        System.out.println("ingresar autor de libro");
                        String autorIngresado = valorIngresado.nextLine();
                        validaAutor = libro.validaString(autorIngresado);
                        if (validaAutor == false) {
                            System.out.println("error! ingresar valor válido!");
                        } else {
                            libro.setAutor(autorIngresado);
                        }
                    } while (validaAutor == false);
                    do {
                        try {
                            System.out.println("año de publicación");
                            String entrada = valorIngresado.nextLine();
                            int anioIngresado = Integer.parseInt(entrada);
                            if (anioIngresado <= 0 || anioIngresado > 2025) {
                                System.out.println("error!, ingrese año mayor que 0");
                                validaAnio = false;
                            } else {
                                validaAnio = true;
                                libro.setAnioPublicacion(anioIngresado);
                            }
                        } catch (Exception e) {
                            System.out.println("error! ingresar valor válido!");
                            validaAnio = false;
                        }
                    } while (validaAnio == false);
                    biblioteca.agregarLibro(libro);
                    break;
                case "2":
                    System.out.println("ingresar id de Libro:");
                    idLibro = valorIngresado.nextInt();
                    biblioteca.buscarLibro(idLibro, biblioteca.getLibros());
                    valorIngresado.nextLine(); // limpiar el buffer
                    break;
                case "3":
                    System.out.println("Libros Ingresados:");
                    for (Libro libroActual : biblioteca.getLibros()) {
                        System.out.println(libroActual);
                    }
                    break;
                case "4":
                    do {
                        try {
                            System.out.println("Ingrese Id");
                            String entrada = valorIngresado.nextLine();
                            idLibro = Integer.parseInt(entrada);
                            validaAnio = true;
                        } catch (Exception e) {
                            System.out.println("error! ingresar valor ID válido!");
                            validaAnio = false;
                        }
                    } while (validaAnio == false);
                    validaLibro = biblioteca.eliminarLibro(idLibro, biblioteca.getLibros());
                    if (validaLibro == true) {
                        System.out.println("libro eliminado!! :");
                    } else {
                        System.out.println("libro no encontrado!! :");
                    }
                    break;
                case "5":
                    System.out.println("¡Adios!.¡Que tengas un buen día!");
                    menuValida = true;
                    break;
                default:
                    break;
            }
        }

        // cerrar objeto Scanner
        valorIngresado.close();
    }

}