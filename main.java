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
        int idUsuario = 0;
        boolean validaTitulo = false;
        boolean validaAnio = false;
        boolean validaAutor = false;
        boolean validaLibro = false;

        boolean validaNameUser = false;
        boolean validaFoneUser = false;
        boolean validaEmailUser = false;
        boolean validaUsuario = false;
        boolean validaId = false;

        // objetos
        // Libro libro = new Libro();
        Biblioteca biblioteca = new Biblioteca();

        while (menuValida == false) {

            System.out.println("***BIBLIOTECA DE ALEJANDRÍA***");
            System.out.println("1.  Ingresar Libro");
            System.out.println("2.  Buscar Libro por ID");
            System.out.println("3.  Mostrar todos los Libros");
            System.out.println("4.  Eliminar Libro por ID");
            System.out.println("5.  Modificar Libro");
            System.out.println("6.  Ingresar Usuario");
            System.out.println("7.  Mostrar Todos los Usuario");
            System.out.println("8.  Eliminar Usuario por ID");
            System.out.println("9.  Modificar usuario");
            System.out.println("10.  salir");
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
                    do {
                        try {
                            System.out.println("Ingrese Id");
                            String entrada = valorIngresado.nextLine();
                            idLibro = Integer.parseInt(entrada);
                            validaId = true;
                        } catch (Exception e) {
                            System.out.println("error! ingresar valor ID válido!");
                            validaId = false;
                        }
                    } while (validaId == false);
                    validaLibro = biblioteca.buscarLibro(idLibro, biblioteca.getLibros());
                    if (validaLibro == true) {
                        // System.out.println("libro encontrado!! :");
                    } else {
                        System.out.println("libro no encontrado!! :");
                    }
                    ///
                    /*
                     * System.out.println("ingresar id de Libro:");
                     * idLibro = valorIngresado.nextInt();
                     * biblioteca.buscarLibro(idLibro, biblioteca.getLibros());
                     * valorIngresado.nextLine(); // limpiar el buffer
                     * break;
                     */
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
                            validaId = true;
                        } catch (Exception e) {
                            System.out.println("error! ingresar valor ID válido!");
                            validaId = false;
                        }
                    } while (validaId == false);
                    validaLibro = biblioteca.eliminarLibro(idLibro, biblioteca.getLibros());
                    if (validaLibro == true) {
                        System.out.println("libro eliminado!! :");
                    } else {
                        System.out.println("libro no encontrado!! :");
                    }
                    break;
                case "5":
                    break;
                case "6":
                    Usuario usuario = new Usuario();
                    do {
                        System.out.println("ingresar nombre de usuario");
                        String nameUser = valorIngresado.nextLine();
                        validaNameUser = usuario.validaString(nameUser);
                        if (validaNameUser == false) {
                            System.out.println("error! ingresar usuario válido!");
                        } else {
                            usuario.setName(nameUser);
                        }
                    } while (validaNameUser == false);
                    do {
                        try {
                            System.out.println("ingresar número de teléfono: ");
                            String fonoUser = valorIngresado.nextLine();
                            int fonoIngresado = Integer.parseInt(fonoUser);
                            if (fonoIngresado <= 900000000 || fonoIngresado > 999999999) {
                                System.out.println("error!, ingrese celular mayor que 0");
                                validaFoneUser = false;
                            } else {
                                validaFoneUser = true;
                                usuario.setFoneNumber(fonoUser);
                            }
                        } catch (Exception e) {
                            System.out.println("error! ingresar valor válido!");
                            validaFoneUser = false;
                        }
                    } while (validaFoneUser == false);
                    do {
                        System.out.println("Ingresar E-mail :");
                        String emailUser = valorIngresado.nextLine();
                        validaEmailUser = usuario.validaEmail(emailUser);
                        if (validaEmailUser == false) {
                            System.out.println("¡error! ¡ingrese E-mail válido!");
                        } else {
                            usuario.setEmail(emailUser);
                        }
                    } while (validaEmailUser == false);
                    biblioteca.agregarUsuario(usuario);
                    break;

                case "7":
                    System.out.println("Usuarios Ingresados:");
                    for (Usuario usuarioActual : biblioteca.getUsuarios()) {
                        System.out.println(usuarioActual);
                    }
                    break;
                case "8":
                    do {
                        try {
                            System.out.println("Ingrese Id usuario:");
                            String entrada = valorIngresado.nextLine();
                            idUsuario = Integer.parseInt(entrada);
                            validaAnio = true;
                        } catch (Exception e) {
                            System.out.println("error! ingresar valor ID válido!");
                            validaAnio = false;
                        }
                    } while (validaAnio == false);
                    validaUsuario = biblioteca.eliminarUsuario(idUsuario, biblioteca.getUsuarios());
                    if (validaUsuario == true) {
                        System.out.println("Usuario eliminado!! :");
                    } else {
                        System.out.println("Usuario no encontrado!! :");
                    }
                    break;
                case "9":
                    do {
                        try {
                            System.out.println("Ingrese Id usuario:");
                            String entrada = valorIngresado.nextLine();
                            idUsuario = Integer.parseInt(entrada);
                            validaId = true;
                        } catch (Exception e) {
                            System.out.println("error! ingresar valor ID válido!");
                            validaId = false;
                        }
                    } while (validaId == false);
                    validaUsuario = biblioteca.modificarUsuario(idUsuario, biblioteca.getUsuarios(), valorIngresado);
                    if (validaUsuario == true) {
                        System.out.println("Usuario Modificado!! :");
                    } else {
                        System.out.println("Usuario no modificado!! :");
                    }
                    break;
                case "10":
                    System.out.println("¡Adios!.¡Que tengas un buen día!");
                    menuValida = true;
                    break;
                default:
                    System.out.println("Error!! Ingrese valor válido!!");
                    break;
            }
        }

        // cerrar objeto Scanner
        valorIngresado.close();
    }

}