import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Descripción: Clase de donde se obtienen objetos que almacenan instancias de
 * Libros.
 * Nombre del archivo: Bibliote.java
 * 
 * @author: duckcode-dev
 * @version 1.0
 */
public class Biblioteca {

    private Set<Libro> libros = new HashSet<>();
    private Set<Usuario> usuarios = new HashSet<>();
    private int contadorId = 1; // contador para IDs libros
    private int contadorIdUser = 1;// contador para IDs usuarios

    public Set<Libro> getLibros() {
        return libros;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void agregarLibro(Libro libro) {
        libro.setId(contadorId++);
        libros.add(libro);
        System.err.println("libro agregado a la lista con id: " + libro.getId());
    }

    public void agregarUsuario(Usuario usuario) {
        usuario.setId(contadorIdUser++);
        usuarios.add(usuario);
        System.err.println("Usuario agregado a la lista con id: " + usuario.getId());
    }

    public boolean buscarLibro(int id, Set<Libro> libros) {
        boolean validador = false;
        for (Libro libroActual : libros) {
            if (libroActual.getId() == id) {
                System.out.println("libro encontrado!! :");
                System.out.println(libroActual);
                validador = true;
            }
        }
        return validador;
    }

    public boolean eliminarLibro(int id, Set<Libro> libros) {
        boolean validador = false;
        Iterator<Libro> it = libros.iterator();
        while (it.hasNext()) {
            Libro libroActual = it.next();
            if (libroActual.getId() == id) {
                it.remove();
                validador = true;
            }
        }
        return validador;
    }

    public boolean eliminarUsuario(int idUsuario, Set<Usuario> usuarios) {
        boolean validador = false;
        Iterator<Usuario> it = usuarios.iterator();
        while (it.hasNext()) {
            Usuario usuarioActual = it.next();
            if (usuarioActual.getId() == idUsuario) {
                it.remove();
                validador = true;
            }
        }
        return validador;
    }

    public boolean modificarUsuario(int idUsuario, Set<Usuario> usuarios, Scanner valorIngresado) {
        String name;
        String fonoUser;
        String emailUser;
        String seleccionModificar;

        boolean validador = false;
        boolean validaNameUser = false;
        boolean validaFoneUser = false;
        boolean validaEmailUser = false;
        boolean validaSelecModificar = false;

        Iterator<Usuario> it = usuarios.iterator();
        do {
            try {
                System.out.println("Seleccione que Modificar:");
                System.out.println("1. Nombre");
                System.out.println("2. Teléfono");
                System.out.println("3. E-mail");
                System.out.println("4. Volver a menú principal");
                seleccionModificar = valorIngresado.nextLine();
                if (!seleccionModificar.equals("1") && !seleccionModificar.equals("2")
                        && !seleccionModificar.equals("3")
                        && !seleccionModificar.equals("4")) {
                    System.out.println("error!, ingresar valor válidoo");
                    validaSelecModificar = false;
                } else {
                    validaSelecModificar = true;
                    while (it.hasNext()) {
                        Usuario usuarioActual = it.next();
                        if (usuarioActual.getId() == idUsuario) {
                            switch (seleccionModificar) {
                                case "1":
                                    do {
                                        System.out.println("ingresar nuevo nombre de usuario");
                                        name = valorIngresado.nextLine();
                                        validaNameUser = usuarioActual.validaString(name);
                                        if (validaNameUser == false) {
                                            System.out.println("error! ingresar usuario válido!");
                                        } else {
                                            usuarioActual.setName(name);
                                            validador = true;
                                        }
                                    } while (validaNameUser == false);
                                    break;
                                case "2":
                                    do {
                                        try {
                                            System.out.println("ingresar nuevo número de teléfono: ");
                                            fonoUser = valorIngresado.nextLine();
                                            int fonoIngresado = Integer.parseInt(fonoUser);
                                            if (fonoIngresado <= 900000000 || fonoIngresado > 999999999) {
                                                System.out.println("error!, ingrese celular mayor que 0");
                                                validaFoneUser = false;
                                            } else {
                                                validaFoneUser = true;
                                                usuarioActual.setFoneNumber(fonoUser);
                                                validador = true;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("error! ingresar valor válido!");
                                            validaFoneUser = false;
                                        }
                                    } while (validaFoneUser == false);
                                    break;
                                case "3":
                                    do {
                                        System.out.println("Ingresar nuevo E-mail :");
                                        emailUser = valorIngresado.nextLine();
                                        validaEmailUser = usuarioActual.validaEmail(emailUser);
                                        if (validaEmailUser == false) {
                                            System.out.println("¡error! ¡ingrese E-mail válido!");
                                        } else {
                                            usuarioActual.setEmail(emailUser);
                                            validador = true;
                                        }
                                    } while (validaEmailUser == false);
                                    break;
                                case "4":
                                    validador = false;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                }

            } catch (Exception e) {
                System.out.println("error! ingresar valor válido!");
                validaSelecModificar = false;
            }
        } while (validaSelecModificar == false);

        return validador;
    }

}
