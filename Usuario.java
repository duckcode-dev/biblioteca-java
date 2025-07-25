public class Usuario {
    private int Id;
    private String name;
    private String foneNumber;

    public Usuario(String name, String foneNumber) {
        this.name = name;
        this.foneNumber = foneNumber;
    }

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

}
