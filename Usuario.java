public class Usuario {
    private int id;
    private String name;
    private String foneNumber;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoneNumber() {
        return foneNumber;
    }

    public void setFoneNumber(String foneNumber) {
        this.foneNumber = foneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Libro : " +
                "ID='" + id + '\'' +
                " nombre='" + name + '\'' +
                ", telefono='" + foneNumber + '\'' +
                ", email=" + email;
    }

}
