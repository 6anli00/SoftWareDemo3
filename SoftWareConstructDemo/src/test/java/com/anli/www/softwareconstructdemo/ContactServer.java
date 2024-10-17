package com.anli.www.softwareconstructdemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ContactServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server is running on port 8080");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String response = handleInput(inputLine);
                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String handleInput(String input) {
            // Parse the input and generate a response
            if (input.startsWith("{")) {
                try {
                    org.json.JSONObject json = new org.json.JSONObject(input);
                    String operation = json.getString("operation");
                    if ("ADD_CONTACT".equals(operation)) {
                        String name = json.getString("name");
                        String address = json.getString("address");
                        String phone = json.getString("phone");

                        ContactService service = new ContactService();
                        Contact contact = new Contact();
                        contact.setName(name);
                        contact.setAddress(address);
                        contact.setPhone(phone);
                        boolean success = service.addContact(contact);

                        return new org.json.JSONObject().put("success", success).toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "Unknown request";
        }
    }
}