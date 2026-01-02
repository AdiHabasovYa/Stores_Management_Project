package logic;

import models.Employee;
import models.Role;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private List<Employee> employees;
    private final String FILE_NAME = "employees.ser";
    private Employee loggedInUser;
    
    // --- New Logger Instance ---
    private Logger logger; 

    public SystemManager() {
        this.employees = new ArrayList<>();
        this.logger = new Logger(); // Initialize Logger
        loadFromFile();
        
        // If no employees exist, create default ones
        if (employees.isEmpty()) {
            seedData();
        }
    }

    // This is the missing method!
    private void seedData() {
        // Create default users so we can login
        employees.add(new Employee("Manager One", "111", "050-0000000", "00-000", 1, 100, Role.SHIFT_MANAGER, "1234"));
        employees.add(new Employee("Sales Person", "222", "050-9999999", "11-111", 1, 200, Role.SALESPERSON, "1234"));
        saveToFile();
    }

    public boolean login(String nationalId, String password) {
        for (Employee e : employees) {
            if (e.getNationalId().equals(nationalId) && e.getPassword().equals(password)) {
                loggedInUser = e;
                logger.log("LOGIN", "User logged in: " + e.getFullName()); // Log Login
                return true;
            }
        }
        logger.log("LOGIN_FAIL", "Failed login attempt for ID: " + nationalId); // Log Failure
        return false;
    }
    
    public Employee getLoggedInUser() { return loggedInUser; }

    // --- A. Employee Registration Log ---
    public String createEmployee(String name, String id, String phone, String bank, 
                                 int branch, int empNum, Role role, String pass) {
        
        if (loggedInUser.getRole() != Role.SALESPERSON) {
            return "Error: Permission denied.";
        }

        // Duplicate checks
        for (Employee e : employees) {
            if (e.getNationalId().equals(id)) return "Error: ID already exists.";
            if (e.getEmployeeNum() == empNum) return "Error: Employee Number already exists.";
        }

        Employee newEmp = new Employee(name, id, phone, bank, branch, empNum, role, pass);
        employees.add(newEmp);
        saveToFile();
        
        // ** LOG THE ACTION **
        logger.log("EMPLOYEE_REGISTRATION", "New Employee created: " + name + " (ID: " + id + ")");
        
        return "Success: Employee " + name + " added!";
    }

    // --- B. Customer Registration Log (New) ---
    public String registerCustomer(String customerName, String customerId, String phone) {
        // Here you would normally save to a Customers List. 
        // For now, we just log it as requested.
        logger.log("CUSTOMER_REGISTRATION", "New Customer: " + customerName + ", ID: " + customerId);
        return "Customer " + customerName + " registered successfully.";
    }

    // --- C. Sales/Purchases Log (New) ---
    public String performSale(String productName, int quantity, double totalPrice) {
        // Logic for inventory update would go here
        logger.log("SALE", "Sold " + quantity + " units of " + productName + ". Total: " + totalPrice);
        return "Sale completed for " + productName;
    }

    // --- D. Chat Logging (New) ---
    public void logChatMessage(String sender, String content) {
        // Just saves the chat content to the log file
        logger.log("CHAT_MESSAGE", "From " + sender + ": " + content);
    }

    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(employees);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File f = new File(FILE_NAME);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                employees = (List<Employee>) in.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}