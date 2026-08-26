package com.qa.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utils - Clase de utilidades con métodos auxiliares
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * Generar timestamp único
     */
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    /**
     * Generar nombre de archivo único
     */
    public static String generateUniqueFileName(String prefix) {
        return prefix + "_" + getTimestamp();
    }

    /**
     * Crear directorio si no existe
     */
    public static void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Directorio creado: " + path);
            } else {
                logger.warn("No se pudo crear el directorio: " + path);
            }
        }
    }

    /**
     * Verificar si un archivo existe
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Eliminar archivo
     */
    public static void deleteFile(String filePath) {
        try {
            if (Files.exists(Paths.get(filePath))) {
                Files.delete(Paths.get(filePath));
                logger.info("Archivo eliminado: " + filePath);
            }
        } catch (Exception e) {
            logger.error("Error eliminando archivo: " + filePath, e);
        }
    }

    /**
     * Obtener extensión del archivo
     */
    public static String getFileExtension(String filePath) {
        int lastDot = filePath.lastIndexOf('.');
        return lastDot > 0 ? filePath.substring(lastDot + 1) : "";
    }

    /**
     * Convertir segundos a milisegundos
     */
    public static int secondsToMillis(int seconds) {
        return seconds * 1000;
    }

    /**
     * Espera genérica
     */
    public static void sleep(long millis) {
        try {
            logger.info("Esperando " + millis + "ms");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("Interrupción durante sleep", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Validar si un string es nulo o vacío
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Validar formato de email
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    /**
     * Capitalizar primera letra
     */
    public static String capitalize(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Generar número aleatorio
     */
    public static int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    /**
     * Generar string aleatorio
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(generateRandomNumber(0, chars.length() - 1)));
        }
        return result.toString();
    }

    /**
     * Generar email aleatorio
     */
    public static String generateRandomEmail() {
        return "user_" + generateRandomString(8) + "@example.com";
    }

    /**
     * Retomar último elemento de array
     */
    public static <T> T getLastElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[array.length - 1];
    }

    /**
     * Contar ocurrencias de substring
     */
    public static int countOccurrences(String text, String substring) {
        if (text == null || substring == null) {
            return 0;
        }
        return text.split(substring, -1).length - 1;
    }

    /**
     * Reemplazar múltiples espacios por uno
     */
    public static String removeExtraSpaces(String text) {
        if (text == null) {
            return "";
        }
        return text.trim().replaceAll("\\s+", " ");
    }

    /**
     * Esperar hasta que una condición sea verdadera
     */
    public static boolean waitUntil(java.util.function.Supplier<Boolean> condition, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);
        while (System.currentTimeMillis() < endTime) {
            try {
                if (condition.get()) {
                    return true;
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    /**
     * Obtener información del sistema
     */
    public static void printSystemInfo() {
        logger.info("=== INFORMACIÓN DEL SISTEMA ===");
        logger.info("OS: " + System.getProperty("os.name"));
        logger.info("Versión OS: " + System.getProperty("os.version"));
        logger.info("Versión Java: " + System.getProperty("java.version"));
        logger.info("Usuario: " + System.getProperty("user.name"));
        logger.info("Directorio de trabajo: " + System.getProperty("user.dir"));
    }
}
