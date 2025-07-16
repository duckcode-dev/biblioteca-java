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

        System.out.println("BIBLIOTECA MUNICIPAL");
        System.out.println("1.Ingresar libro");
        System.out.println("2.Buscar libro");
        System.out.println("3.Mostrar todos los libros");
        System.out.println("4.salir");
        String opcionSeleccionada = valorIngresado.nextLine();
        System.out.println(opcionSeleccionada);

        switch (opcionSeleccionada) {
            case "1":
                System.out.println("ingresar título de libro");
                System.out.println("ingresar autor");
                System.out.println("año de publicación");
                break;
            case "2":

                break;
            case "3":

                break;
            case "4":
                System.out.println("¡Adios!.¡Que tengas un buen día!");
                break;
            default:
                break;
        }

        // cerrar objeto Scanner
        valorIngresado.close();
    }
}