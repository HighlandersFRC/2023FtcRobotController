package org.firstinspires.ftc.teamcode.Tools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static final int PORT = 23477; // Port number for the HTTP server
    private final Map<String, Object> logEntries; // Map to store log entries with keys
    private boolean running; // Flag to control the server state

    public Logger() {
        logEntries = new HashMap<>();
        running = false;
    }

    // Method to add or update a log entry with a key and a value of any type
    public void log(String key, Object value) {
        logEntries.put(key, value);
        System.out.println("Logged: " + key + " = " + value); // Optionally print the log to the console
    }

    // Start the HTTP server to serve logs
    public void startServer() {
        running = true;

        // Create a new thread for the server
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Logger HTTP server started on port " + PORT);

                while (running) {
                    try (Socket clientSocket = serverSocket.accept()) {
                        handleClient(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Stop the HTTP server
    public void stopServer() {
        running = false;
        System.out.println("Logger HTTP server stopped.");
    }

    // Handle the client request and send back the log entries as JSON
    private void handleClient(Socket clientSocket) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Read the client's request (we can ignore the content for this simple server)
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.isEmpty()) {
                break; // End of request headers
            }
        }

        // Send HTTP response headers
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Content-Type: application/json\r\n");
        out.write("Connection: close\r\n");
        out.write("\r\n");

        // Convert the logEntries map to JSON format and send it as the response
        out.write(toJson(logEntries));

        out.flush();
        clientSocket.close();
    }

    // Convert the log entries to a JSON string format
    private String toJson(Map<String, Object> data) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!first) {
                json.append(",");
            }
            json.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() instanceof String) {
                json.append("\"").append(entry.getValue()).append("\"");
            } else {
                json.append(entry.getValue());
            }
            first = false;
        }
        json.append("}");
        return json.toString();
    }
}
