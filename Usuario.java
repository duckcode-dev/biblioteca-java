public class Usuario {
    private int Id;
    private String name;
    private String foneNumber;
    private String email;

    public Usuario() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    // función para valir cadena ingresada
    public boolean validaString(String texto) {
        return texto.matches("[a-zA-Z]+");
    }

    // función para validar correo electrónico
    public boolean validaEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Expresión regular para validar un formato de correo electrónico
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    @Override
    public String toString() {
        return "Libro : " +
                "ID='" + Id + '\'' +
                " nombre='" + name + '\'' +
                ", telefono='" + foneNumber + '\'' +
                ", email=" + email; 
    }

}
