package common;

import java.io.Serializable;

//Employee class
//represents a user in the system.
//this object is shared between client and server.

public class Employee implements Serializable {

    // employee basic details
    private String fullName;
    private String id;
    private String role;
    private String password;

    // restart employee data
    public Employee(String fullName, String id, String role, String password) {
        this.fullName = fullName;
        this.id = id;
        this.role = role;
        this.password = password;
    }

    // returns employee full name
    public String getFullName() {
        return fullName;
    }

    // returns employee ID
    public String getId() {
        return id;
    }

    // returns employee role (like ADMIN)
    public String getRole() {
        return role;
    }

    // returns password (used only on server side)
    public String getPassword() {
        return password;
    }

    // checks if given password matches employee password
    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }
}
