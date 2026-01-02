package server;

import logic.SystemManager;
import models.Role;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 12345;
    private static SystemManager system = new SystemManager(); // The core logic instance

    public static void main(String[] args) {
        System.out.println("Server is running on port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Wait for a client to connect (Blocking call)
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Client Connected!");
                
                // Handle client in a separate thread to support multiple users simultaneously
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            boolean running = true;
            while (running) {
                // 1. Read the command sent by the client
                String command = (String) in.readObject();

                // 2. Execute logic based on the command
                switch (command) {
                    case "LOGIN":
                        String id = (String) in.readObject();
                        String pass = (String) in.readObject();
                        boolean success = system.login(id, pass);
                        out.writeObject(success); // Send success status back to client
                        if (success) {
                            // If login successful, send the User Object so client knows the Role
                            out.writeObject(system.getLoggedInUser());
                        }
                        break;

                    case "CREATE_EMPLOYEE":
                        // Read all employee details sent by the client
                        String name = (String) in.readObject();
                        String empId = (String) in.readObject();
                        String phone = (String) in.readObject();
                        String bank = (String) in.readObject();
                        int branch = (int) in.readObject();
                        int empNum = (int) in.readObject();
                        Role role = (Role) in.readObject();
                        String empPass = (String) in.readObject();

                        // Perform the creation logic in SystemManager
                        String result = system.createEmployee(name, empId, phone, bank, branch, empNum, role, empPass);
                        out.writeObject(result); // Send result message back to client
                        break;
					case "REGISTER_CUSTOMER":
					String custName = (String) in.readObject();
					String custId = (String) in.readObject();
					String custPhone = (String) in.readObject();
					String custRes = system.registerCustomer(custName, custId, custPhone);
					out.writeObject(custRes);
						break;

				case "PERFORM_SALE":
					String prodName = (String) in.readObject();
					int qty = (int) in.readObject();
					double total = (double) in.readObject();
					String saleRes = system.performSale(prodName, qty, total);
					out.writeObject(saleRes);
						break;

				case "SEND_CHAT":
					String msgContent = (String) in.readObject();
					// We use the currently logged-in user's name for the log
					if (system.getLoggedInUser() != null) {
						system.logChatMessage(system.getLoggedInUser().getFullName(), msgContent);
						out.writeObject("Message logged successfully.");
					} else {
						out.writeObject("Error: User not identified.");
					}
						break;
                    case "EXIT":
                        running = false;
                        break;
                }
                out.flush(); // Ensure data is sent immediately
            }
        } catch (Exception e) {
            System.out.println("Client Disconnected.");
        }
    }
}