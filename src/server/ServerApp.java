package server;

import common.Employee;
import java.net.ServerSocket;
import java.net.Socket;

//main server application,
//responsible for accepting client connections.

public class ServerApp {

    public static void main(String[] args) {

        // create system manager
        AdminManager manager = new AdminManager();

        // create default admin user
        manager.addEmployee(new Employee("Admin", "1", "ADMIN", "1234"));

        try (ServerSocket serverSocket = new ServerSocket(7000)) {

            System.out.println("Server started on port 7000");

            // infinite loop to keep the server running
            while (true) {
                Socket socket = serverSocket.accept();

                // create a new thread for each client
                new ClientHandler(socket, manager).start();
            }

        } catch (Exception e) {
            System.out.println("Server error");
        }
    }
}
