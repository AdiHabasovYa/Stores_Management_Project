package ui;

import models.Employee;
import models.Role;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Client Started ---");

        // Attempt to connect to the server on localhost port 12345
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to Server!");
            Employee currentUser = null; // Holds the currently logged-in user

            while (true) {
                // State 1: User is NOT logged in - Show Login Screen
                if (currentUser == null) {
                    System.out.println("\nPlease Login:");
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Password: ");
                    String pass = scanner.nextLine();

                    // Send LOGIN command to server
                    out.writeObject("LOGIN");
                    out.writeObject(id);
                    out.writeObject(pass);
                    out.flush();

                    // Receive response from server
                    boolean success = (boolean) in.readObject();
                    if (success) {
                        currentUser = (Employee) in.readObject(); // Server sends the user object
                        System.out.println("Login Successful! Hello " + currentUser.getFullName());
                    } else {
                        System.out.println("Invalid credentials.");
                    }

                } else {
                    // State 2: User IS logged in - Show Main Menu
                    System.out.println("\n--- Main Menu (" + currentUser.getRole() + ") ---");
                    System.out.println("1. Register New Customer (Log)");
                    System.out.println("2. Perform Sale (Log)");
                    System.out.println("3. Send Chat Message (Log)");
                    
                    // Only Salesperson sees option 4
                    if (currentUser.getRole() == Role.SALESPERSON) {
                        System.out.println("4. [Salesperson] Add New Employee");
                    }
                    System.out.println("0. Logout");
                    System.out.print("Select: ");
                    String choice = scanner.nextLine();

                    switch (choice) {
                        // --- Case 1: Register Customer (New) ---
                        case "1":
                            System.out.print("Customer Name: "); String cName = scanner.nextLine();
                            System.out.print("Customer ID: "); String cId = scanner.nextLine();
                            System.out.print("Phone: "); String cPhone = scanner.nextLine();
                            
                            out.writeObject("REGISTER_CUSTOMER");
                            out.writeObject(cName);
                            out.writeObject(cId);
                            out.writeObject(cPhone);
                            out.flush();
                            
                            System.out.println((String) in.readObject()); // Print Server Response
                            break;

                        // --- Case 2: Perform Sale (New) ---
                        case "2":
                            System.out.print("Product Name: "); String pName = scanner.nextLine();
                            try {
                                System.out.print("Quantity: "); int pQty = Integer.parseInt(scanner.nextLine());
                                System.out.print("Total Price: "); double pPrice = Double.parseDouble(scanner.nextLine());
                                
                                out.writeObject("PERFORM_SALE");
                                out.writeObject(pName);
                                out.writeObject(pQty);
                                out.writeObject(pPrice);
                                out.flush();
                                
                                System.out.println((String) in.readObject()); // Print Server Response
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Please enter valid numbers.");
                            }
                            break;

                        // --- Case 3: Chat Message (New) ---
                        case "3":
                            System.out.print("Enter Message for Log: "); 
                            String msg = scanner.nextLine();
                            
                            out.writeObject("SEND_CHAT");
                            out.writeObject(msg);
                            out.flush();
                            
                            System.out.println((String) in.readObject()); // Print Server Response
                            break;

                        // --- Case 4: Add Employee (Existing) ---
                        case "4":
                            if (currentUser.getRole() == Role.SALESPERSON) {
                                System.out.println("\n--- New Employee Form ---");
                                // Collect input from user
                                System.out.print("Full Name: "); String name = scanner.nextLine();
                                System.out.print("ID: "); String newId = scanner.nextLine();
                                System.out.print("Phone: "); String phone = scanner.nextLine();
                                System.out.print("Bank: "); String bank = scanner.nextLine();
                                
                                int branch = 0, empNum = 0;
                                try {
                                    System.out.print("Branch: "); branch = Integer.parseInt(scanner.nextLine());
                                    System.out.print("Emp Num: "); empNum = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid number input. Defaulting to 0.");
                                }
                                
                                System.out.println("Role (1-Manager, 2-Cashier, 3-Salesperson): ");
                                String roleSel = scanner.nextLine();
                                Role role = Role.CASHIER;
                                if (roleSel.equals("1")) role = Role.SHIFT_MANAGER;
                                if (roleSel.equals("3")) role = Role.SALESPERSON;

                                System.out.print("Password: "); String newPass = scanner.nextLine();

                                // Send CREATE_EMPLOYEE command to Server
                                out.writeObject("CREATE_EMPLOYEE");
                                out.writeObject(name);
                                out.writeObject(newId);
                                out.writeObject(phone);
                                out.writeObject(bank);
                                out.writeObject(branch);
                                out.writeObject(empNum);
                                out.writeObject(role);
                                out.writeObject(newPass);
                                out.flush();

                                // Read server response
                                String response = (String) in.readObject();
                                System.out.println("Server Response: " + response);
                            } else {
                                System.out.println("Access Denied.");
                            }
                            break;

                        // --- Case 0: Logout ---
                        case "0":
                            out.writeObject("EXIT");
                            out.flush();
                            currentUser = null;
                            System.out.println("Logged out.");
                            return; // Exit the client application

                        default:
                            System.out.println("Invalid selection.");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }
}