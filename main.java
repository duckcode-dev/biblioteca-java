import java.util.Scanner;

/**
 * Nombre del archivo: main.java
 * Autor: duckcode-dev
 * Fecha de creación: 13/07/2025
 * Descripción: Punto de entrada principal para la aplicación de biblioteca.
 * Licencia: MIT
 */
public class Main {
    public static void main(String[] args) {

        Scanner valorIngresado = new Scanner(System.in);

        boolean menuValida = false;
        String opcionSeleccionada = "";
        int idLibro = 0;

        // objetos
        // Libro libro = new Libro();
        Biblioteca biblioteca = new Biblioteca();

        while (menuValida == false) {

            System.out.println("BIBLIOTECA MUNICIPAL");
            System.out.println("1.Ingresar libro");
            System.out.println("2.Buscar libro");
            System.out.println("3.Mostrar todos los libros");
            System.out.println("4.salir");
            opcionSeleccionada = valorIngresado.nextLine();

            System.out.println(opcionSeleccionada);
            switch (opcionSeleccionada) {
                case "1":
                    Libro libro = new Libro();
                    System.out.println("ingresar título de libro");
                    libro.setTitulo(valorIngresado.nextLine());
                    System.out.println("ingresar autor");
                    libro.setAutor(valorIngresado.nextLine());
                    System.out.println("año de publicación");
                    libro.setAnioPublicacion(valorIngresado.nextInt());
                    valorIngresado.nextLine(); // limpiar el buffer
                    biblioteca.agregarLibro(libro);
                    break;
                case "2":
                    System.out.println("ingresar id de Libro:");
                    idLibro = valorIngresado.nextInt();
                    biblioteca.BuscarLibro(idLibro, biblioteca.getLibros());
                    valorIngresado.nextLine(); // limpiar el buffer
                    break;
                case "3":
                    System.out.println("Libros Ingresados:");
                    for (Libro libroActual : biblioteca.getLibros()) {
                        System.out.println(libroActual);
                    }
                    break;
                case "4":
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