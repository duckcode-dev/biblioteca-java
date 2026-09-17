public class Usuario {
    private int id;
    private String nombre;
    private String telefono;
    private String email;

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    // Solo la capa de dominio asigna IDs al registrar un usuario.
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
        return "Usuario: " +
                "ID='" + id + '\'' +
                " nombre='" + nombre + '\'' +
                ", teléfono='" + telefono + '\'' +
                ", email=" + email;
    }

}
