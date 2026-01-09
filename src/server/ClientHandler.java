package server;

import common.*;
import java.io.*;
import java.net.Socket;


//Handles communication with a single client.
//each client runs in its own thread.

public class ClientHandler extends Thread {

    private Socket socket;
    private AdminManager manager;

    public ClientHandler(Socket socket, AdminManager manager) {
        this.socket = socket;
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            // create streams (output first to avoid deadlock)
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // loop allows client to send multiple requests
            while (true) {

                Object obj = in.readObject();

                // handle login request
                if (obj instanceof LoginRequest) {
                    LoginRequest req = (LoginRequest) obj;
                    Employee emp = manager.login(req.getId(), req.getPassword());

                    if (emp != null) {
                        out.writeObject(new LoginResponse(true, "Login success", emp));
                    } else {
                        out.writeObject(new LoginResponse(false, "Login failed", null));
                    }
                }

                // to add employee request
                else if (obj instanceof Employee) {
                    Employee newEmp = (Employee) obj;

                    boolean ok = manager.addEmployee(newEmp);

                    if (ok) {
                        out.writeObject(new LoginResponse(true, "Employee added", null));
                    } else {
                        out.writeObject(new LoginResponse(false, "Password too short", null));
                    }
                }

                out.flush();
            }

        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }
}