package com.anli.www.softwareconstructdemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

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
            // For simplicity, assume input is a JSON string
            // You can use a JSON library like org.json to parse and generate JSON
            // Here's a simple example:
            if (input.equals("GET_CONTACTS")) {
                ContactService service = new ContactService();
                List<Contact> contacts = service.getAllContacts();
                StringBuilder response = new StringBuilder();
                for (Contact contact : contacts) {
                    response.append("{")
                            .append("\"id\":").append(contact.getId()).append(",")
                            .append("\"name\":\"").append(contact.getName()).append("\",")
                            .append("\"address\":\"").append(contact.getAddress()).append("\",")
                            .append("\"phone\":\"").append(contact.getPhone()).append("\"")
                            .append("},");
                }
                return response.toString();
            }
            // Add more cases for other operations
            return "Unknown request";
        }
/*        private String handleInput(String input) {
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

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    try {
                        String response = handleInput(inputLine);
                        out.println(response);
                    } catch (Exception e) {
                        System.err.println("Error handling input: " + e.getMessage());
                        out.println("Error: " + e.getMessage());
                    }
                }
            } catch (SocketException e) {
                System.err.println("Client disconnected unexpectedly: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("IO Error: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            }
        }*/

    }
}