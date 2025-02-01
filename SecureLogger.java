/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loadpso;

/**
 *
 * @author sanja
 */
import java.util.logging.*;

public class SecureLogger {
    private static final Logger logger = Logger.getLogger(SecureLogger.class.getName());
    
    static {
        try {
            FileHandler fileHandler = new FileHandler("secure_log.log", true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to log general information
    public static void logInfo(String message) {
        logger.info(message);
    }

    // Method to log warnings
    public static void logWarning(String message) {
        logger.warning(message);
    }

    // Method to log errors
    public static void logError(String message, Throwable throwable) {
        logger.severe(message + ": " + throwable.getMessage());
    }
}
