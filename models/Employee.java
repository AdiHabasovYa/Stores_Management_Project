package models;

import java.io.Serializable;

// Implements Serializable to allow saving objects to a file
public class Employee implements Serializable {
    
    // Version ID for serialization compatibility
    private static final long serialVersionUID = 1L;

    private String id;          // National ID
    private String fullName;    // Full Name
    private String password;    // Login Password
    private Role role;          // Employee Role (Enum)

    public Employee(String id, String fullName, String password, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }

    @Override
    public String toString() {
        return "Name: " + fullName + " | Role: " + role;
    }
}