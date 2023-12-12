package main.Models.Entities;




import java.io.Serializable;


public class Client implements Serializable {
    private int id;
    private String login;
    private String password;
    private String phonenumber;
    private String email;
    private String address;

    private Person person;

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Person getPerson() {
        return person;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Client() {
    }

    @Override
    public String toString() {
        return person.toString() +" ClientId: "+ id + " PhoneNumber: " + phonenumber + " email: "+ email + " address: " +address;
    }
}
