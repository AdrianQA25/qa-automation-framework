package com.qa.automation.config;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DriverManager - Gestiona la instancia del navegador y página de Playwright
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    /**
     * Inicializar el navegador
     */
    public static void initBrowser() {
        logger.info("Inicializando navegador");
        try {
            playwright = Playwright.create();

            String browserType = ConfigurationManager.getBrowser().toLowerCase();
            boolean headless = ConfigurationManager.isHeadless();

            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);

            browser = switch (browserType) {
                case "firefox" -> playwright.firefox().launch(options);
                case "webkit" -> playwright.webkit().launch(options);
                default -> playwright.chromium().launch(options);
            };

            logger.info("Navegador iniciado: " + browserType);
            createContext();
        } catch (Exception e) {
            logger.error("Error inicializando navegador", e);
            throw new RuntimeException("Error al inicializar navegador", e);
        }
    }

    /**
     * Crear contexto y página
     */
    private static void createContext() {
        logger.info("Creando contexto y página");
        context = browser.newContext();
        page = context.newPage();

        // Configurar timeout global
        int timeout = ConfigurationManager.getTimeout();
        page.setDefaultTimeout(timeout);
        page.setDefaultNavigationTimeout(timeout);

        logger.info("Contexto y página creados");
    }

    /**
     * Obtener la página actual
     */
    public static Page getPage() {
        if (page == null) {
            logger.warn("Página no inicializada, inicializando navegador");
            initBrowser();
        }
        return page;
    }

    /**
     * Obtener el contexto
     */
    public static BrowserContext getContext() {
        return context;
    }

    /**
     * Obtener el navegador
     */
    public static Browser getBrowser() {
        return browser;
    }

    /**
     * Cerrar navegador
     */
    public static void closeBrowser() {
        logger.info("Cerrando navegador");
        try {
            if (page != null) {
                page.close();
            }
            if (context != null) {
                context.close();
            }
            if (browser != null) {
                browser.close();
            }
            if (playwright != null) {
                playwright.close();
            }
            logger.info("Navegador cerrado exitosamente");
        } catch (Exception e) {
            logger.error("Error cerrando navegador", e);
        }
    }

    /**
     * Reiniciar navegador
     */
    public static void restartBrowser() {
        logger.info("Reiniciando navegador");
        closeBrowser();
        initBrowser();
    }

    /**
     * Limpiar cookies y storage local
     */
    public static void clearBrowserData() {
        logger.info("Limpiando datos del navegador");
        context.clearCookies();
        page.evaluate("() => localStorage.clear()");
        page.evaluate("() => sessionStorage.clear()");
    }
}
