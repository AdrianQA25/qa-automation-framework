package com.qa.automation.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

/**
 * ConfigurationManager - Gestiona la configuración del framework
 */
public class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
    private static JsonObject config;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try {
            String configPath = "src/test/resources/config.json";
            Gson gson = new Gson();
            config = gson.fromJson(new FileReader(configPath), JsonObject.class);
            logger.info("Configuración cargada exitosamente");
        } catch (IOException e) {
            logger.error("Error cargando configuración", e);
            throw new RuntimeException("No se pudo cargar config.json", e);
        }
    }

    public static String getBaseUrl() {
        return config.get("baseUrl").getAsString();
    }

    public static String getBrowser() {
        return config.get("browser").getAsString();
    }

    public static boolean isHeadless() {
        return config.get("headless").getAsBoolean();
    }

    public static int getTimeout() {
        return config.get("timeout").getAsInt();
    }

    public static String getScreenshotPath() {
        return config.get("screenshotPath").getAsString();
    }

    public static boolean isTakeScreenshots() {
        return config.get("takeScreenshots").getAsBoolean();
    }

    public static String getReportPath() {
        return config.get("reportPath").getAsString();
    }
}
