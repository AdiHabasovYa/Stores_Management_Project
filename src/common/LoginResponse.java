package common;

import java.io.Serializable;

//LoginResponse object.
//Sent from server to client as response to login or add employee.

public class LoginResponse implements Serializable {

    private boolean success;
    private String message;
    private Employee employee;

    // constructor
    public LoginResponse(boolean success, String message, Employee employee) {
        this.success = success;
        this.message = message;
        this.employee = employee;
    }

    // shows if operation was successful
    public boolean isSuccess() {
        return success;
    }

    // message from server
    public String getMessage() {
        return message;
    }

    // returns employee object (null if failed)
    public Employee getEmployee() {
        return employee;
    }
}
