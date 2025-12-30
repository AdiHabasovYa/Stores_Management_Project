package ui;

import logic.SystemManager;
import models.Employee;
import models.Role;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SystemManager system = new SystemManager();

        System.out.println("--- Store Management System ---");

        // Main Application Loop
        while (true) {
            
            // State 1: User is NOT logged in
            if (system.getLoggedInUser() == null) {
                System.out.println("\nPlease Login:");
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("Password: ");
                String pass = scanner.nextLine();

                if (system.login(id, pass)) {
                    System.out.println("Login Successful! Hello " + system.getLoggedInUser().getFullName());
                } else {
                    System.out.println("Invalid credentials.");
                }

            } else {
                // State 2: User IS logged in -> Show Role-Based Menu
                Employee user = system.getLoggedInUser();
                
                System.out.println("\nMain Menu (" + user.getRole() + "):");
                System.out.println("1. Sales (All)");
                System.out.println("2. Reports (All)");

                // Dynamic Menu Options based on Role
                if (user.getRole() == Role.SALESPERSON) {
                    System.out.println("3. [Salesperson Only] Add New Employee");
                }
                
                if (user.getRole() == Role.SHIFT_MANAGER) {
                    System.out.println("4. [Manager Only] View Active Chats");
                }

                System.out.println("0. Logout");
                System.out.print("Select: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.println(">> Processing Sale...");
                        break;
                    case "2":
                        System.out.println(">> Generating Reports...");
                        break;
                    case "3":
                        // Double verification in case logic is bypassed
                        if (user.getRole() == Role.SALESPERSON) {
                            System.out.print("Enter New Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter New ID: ");
                            String newId = scanner.nextLine();
                            
                            // Default password/role for example purposes
                            String res = system.addEmployee(newId, name, "1234", Role.CASHIER);
                            System.out.println(res);
                        } else {
                            System.out.println("Access Denied!");
                        }
                        break;
                    case "4":
                        if (user.getRole() == Role.SHIFT_MANAGER) {
                            System.out.println(">> Connecting to Chat Server...");
                        } else {
                            System.out.println("Access Denied!");
                        }
                        break;
                    case "0":
                        system.logout();
                        break;
                    default:
                        System.out.println("Invalid selection.");
                }
            }
        }
    }
}