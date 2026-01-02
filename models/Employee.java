package models;

import java.io.Serializable;

/**
 * Represents an Employee in the system.
 * Implements Serializable for file storage.
 */
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    // --- Fields defined in Section 8.1.a ---
    private String fullName;      // Full Name
    private String nationalId;    // National Identity Card (Unique)
    private String phoneNumber;   // Contact Phone
    private String bankAccount;   // Bank Account Number for salary
    private int branchId;         // ID of the branch the employee belongs to
    private int employeeNum;      // Internal Employee Number (Unique)
    private Role role;            // Job Title: Shift Manager, Cashier, Salesperson
    
    // System Login Credentials
    private String password;

    // --- Constructor ---
    public Employee(String fullName, String nationalId, String phoneNumber, 
                    String bankAccount, int branchId, int employeeNum, 
                    Role role, String password) {
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
        this.bankAccount = bankAccount;
        this.branchId = branchId;
        this.employeeNum = employeeNum;
        this.role = role;
        this.password = password;
    }

    // --- Getters ---
    public String getNationalId() { return nationalId; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public Role getRole() { return role; }
    public int getEmployeeNum() { return employeeNum; }

    @Override
    public String toString() {
        return "Emp #" + employeeNum + " | " + fullName + " (" + role + ") | Branch: " + branchId;
    }
}