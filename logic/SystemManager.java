package logic;

import models.Employee;
import models.Role;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private List<Employee> employees;
    private final String FILE_NAME = "data.ser"; // The file where data is persisted
    private Employee loggedInUser;

    public SystemManager() {
        this.employees = new ArrayList<>();
        loadFromFile(); // Attempt to load existing data

        // If list is empty (first run), seed initial dummy data
        if (employees.isEmpty()) {
            seedData();
        }
    }

    // Creates default users for testing purposes
    private void seedData() {
        employees.add(new Employee("1", "Dani Seller", "1234", Role.SALESPERSON));
        employees.add(new Employee("2", "Ronit Manager", "1234", Role.SHIFT_MANAGER));
        employees.add(new Employee("3", "Yossi Cashier", "1234", Role.CASHIER));
        saveToFile();
    }

    // --- Authentication Logic ---
    
    public boolean login(String id, String password) {
        for (Employee e : employees) {
            if (e.getId().equals(id) && e.getPassword().equals(password)) {
                loggedInUser = e;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    public Employee getLoggedInUser() {
        return loggedInUser;
    }

    // --- Business Logic: Add Employee ---
    // Note: Only a SALESPERSON is allowed to perform this action.
    public String addEmployee(String id, String name, String pass, Role role) {
        // 1. Permission Check
        if (loggedInUser.getRole() != Role.SALESPERSON) {
            return "Error: Permission denied. Only a Salesperson can add employees.";
        }

        // 2. Duplicate Check (ID must be unique)
        for (Employee e : employees) {
            if (e.getId().equals(id)) return "Error: Employee with this ID already exists.";
        }

        // 3. Create and Save
        employees.add(new Employee(id, name, pass, role));
        saveToFile();
        return "Success: Employee added successfully!";
    }

    // --- File Persistence (Save/Load) ---
    
    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File f = new File(FILE_NAME);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                employees = (List<Employee>) in.readObject();
            } catch (Exception e) {
                System.out.println("Error loading data: " + e.getMessage());
            }
        }
    }
}