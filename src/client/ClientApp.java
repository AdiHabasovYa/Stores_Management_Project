package client;

import common.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;


//This is a client side application.
//for user input and sending requests to the server.

public class ClientApp {

    public static void main(String[] args) {

        try {
            // connect to server on localhost and port 7000
            Socket socket = new Socket("localhost", 7000);

            // output stream, sending objects to server
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // input stream, receiving objects from server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // use to get input from the user
            Scanner scanner = new Scanner(System.in);

            // login screen, user enters ID and password
            System.out.print("ID: ");
            String id = scanner.nextLine();

            System.out.print("Password: ");
            String pass = scanner.nextLine();

            // send login request to server
            out.writeObject(new LoginRequest(id, pass));
            out.flush();

            // receive login response from server
            LoginResponse res = (LoginResponse) in.readObject();

            // if login failed, than stop client
            if (!res.isSuccess()) {
                System.out.println("Login failed");
                socket.close();
                return;
            }

            // logged in user
            Employee user = res.getEmployee();
            System.out.println("Hello " + user.getFullName());

            // if user is admin, show admin menu
            if (user.getRole().equalsIgnoreCase("Admin")) {

                boolean run = true;

                while (run) {
                    System.out.println("\n *Admin menu: select action:*");
                    System.out.println("1.Add employee");
                    System.out.println("2.Exit");
                    System.out.print("Your choice: ");

                    String choice = scanner.nextLine();

                    // add new employee
                    if (choice.equals("1")) {

                        System.out.print("Employee name: ");
                        String name = scanner.nextLine();

                        System.out.print("Employee ID: ");
                        String newId = scanner.nextLine();

                        System.out.print("Role: ");
                        String role = scanner.nextLine();

                        System.out.print("Password (minimum 4 chars): ");
                        String newPass = scanner.nextLine();

                        // create employee object and send to server
                        Employee newEmp =
                                new Employee(name, newId, role, newPass);

                        out.writeObject(newEmp);
                        out.flush();

                        // receive response from server
                        LoginResponse addRes =
                                (LoginResponse) in.readObject();

                        if (addRes.isSuccess()) {
                            System.out.println("Employee added successfully");
                        } else {
                            System.out.println("Password too short");
                        }

                    } else if (choice.equals("2")) {
                        // exit admin menu
                        run = false;
                    }
                }
            }

            // close connection
            socket.close();

        } catch (Exception e) {
            System.out.println("Client error");
        }
    }
}
