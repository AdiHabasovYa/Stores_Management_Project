package server;

import common.Employee;
import java.util.ArrayList;


// handles employees logic on the server side.

public class AdminManager {

    // list of all employees in the system
    private ArrayList<Employee> employees = new ArrayList<>();


     // adds a new employee after checking password.
     // return true if employee added,if no- false

    public boolean addEmployee(Employee emp) {

        // simple password policy= minimum length
        if (emp.getPassword().length() < 4) {
            return false;
        }

        employees.add(emp);
        return true;
    }


     // checks login details.
     // return Employee if found, otherwise null

    public Employee login(String id, String password) {

        for (Employee emp : employees) {
            if (emp.getId().equals(id) &&
                    emp.checkPassword(password)) {
                return emp;
            }
        }
        return null;
    }
}
