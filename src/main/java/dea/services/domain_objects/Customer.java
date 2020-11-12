package dea.services.domain_objects;

public class Customer extends DomainObject {

    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public Customer(final long id, final String username, final String password, final String firstname, final String lastname) {
        super(id);
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Customer [id=" + getId() + ", username=" + getUsername() + ", firstname=" + getFirstname() + ", lastname=" + getLastname() + "]";
    }


}
