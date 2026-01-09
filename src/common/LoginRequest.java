package common;

import java.io.Serializable;

// login request object.
//sent from client to server during login.

public class LoginRequest implements Serializable {

    private String id;
    private String password;

    // constructor
    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // returns ID entered by user
    public String getId() {
        return id;
    }

    // returns password entered by user
    public String getPassword() {
        return password;
    }
}
