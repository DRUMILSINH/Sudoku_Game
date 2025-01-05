package utils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "sudoku_game.log";
    private static PrintWriter writer;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    static {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = String.format("[%s] %s: %s", timestamp, level, message);
        System.out.println(logMessage);
        writeToFile(logMessage);
    }
    
    public static void error(String message, Throwable e) {
        log("ERROR", message);
        if (e != null) {
            log("ERROR", e.toString());
            for (StackTraceElement element : e.getStackTrace()) {
                log("ERROR", "\tat " + element.toString());
            }
        }
    }
    
    private static synchronized void writeToFile(String message) {
        if (writer != null) {
            writer.println(message);
            writer.flush();
        }
    }
    
    public static void close() {
        if (writer != null) {
            writer.close();
        }
    }
} 