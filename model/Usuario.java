package model;

/**
 * Clase que representa un usuario en la biblioteca.
 * Contiene información sobre el nombre, teléfono y correo electrónico del
 * usuario.
 * 
 * @author Patricio Fernández
 * @author github.com/duckcode-dev
 */

public class Usuario {
    private int id;
    private String nombre;
    private String telefono;
    private String email;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario: ID='" + id + '\'' + " nombre='" + nombre + '\''
                + ", teléfono='" + telefono + '\'' + ", email=" + email;
    }
}
