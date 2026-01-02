package logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "system_logs.txt";

    /**
     * Writes a log entry to the file.
     * @param category The type of action (e.g., "EMPLOYEE_REGISTRATION", "SALE")
     * @param message The details of the action
     */
    public void log(String category, String message) {
        // Get current timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Format: [DATE] [CATEGORY] Message
        String logEntry = String.format("[%s] [%s] %s", timestamp, category, message);

        // Write to file (append mode = true)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logEntry);
            writer.newLine(); // Move to next line
        } catch (IOException e) {
            System.err.println("Error writing log: " + e.getMessage());
        }
    }
}