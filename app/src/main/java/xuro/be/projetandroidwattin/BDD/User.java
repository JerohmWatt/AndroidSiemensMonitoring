package xuro.be.projetandroidwattin.BDD;

public class User {
    private int id;
    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private int rights;

    public User(){}

    public User(String l, String f, String e, String p, int r){
        this.lastname = l;
        this.firstname = f;
        this.email = e;
        this.password = p;
        this.rights = r;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID : "
                + Integer.toString(getId()) + "\n"
                + "Lastname : " + getLastname() + "\n"
                + "Firstname : " + getFirstname() + "\n"
                + "Password : " + getPassword() + "\n"
                + "Email : " + getEmail()
                + "Rights : " + getRights());
        return sb.toString();
    }
}