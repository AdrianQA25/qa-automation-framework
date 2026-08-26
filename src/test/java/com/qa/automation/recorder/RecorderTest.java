package com.qa.automation.recorder;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qa.automation.config.ConfigurationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RecorderTest - Clase para grabar tests con Playwright Inspector
 * 
 * Uso:
 * PWDEBUG=1 gradle recordTest
 * 
 * Esto abrirá el navegador y el Inspector de Playwright simultáneamente.
 * Realiza las acciones manualmente y el Inspector grabará el código Java.
 */
public class RecorderTest {
    
    private static final Logger logger = LoggerFactory.getLogger(RecorderTest.class);
    private static Playwright playwright;
    private static Browser browser;
    private Page page;
    private BrowserContext context;
    
    @BeforeAll
    static void setupPlaywright() {
        logger.info("Inicializando Playwright...");
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new Browser.LaunchOptions().setHeadless(false));
        logger.info("Playwright inicializado correctamente");
    }
    
    @AfterAll
    static void closePlaywright() {
        logger.info("Cerrando Playwright...");
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        logger.info("Playwright cerrado correctamente");
    }
    
    /**
     * Test simple para grabar con Playwright Inspector
     * Reemplaza la URL con la de tu aplicación
     * 
     * Ejecutar con: PWDEBUG=1 gradle recordTest
     */
    @Test
    void recordMyFirstTest() {
        logger.info("=== Iniciando grabación manual ===");
        
        // Reemplaza con tu URL
        String url = "https://example.com";
        logger.info("Navegando a: " + url);
        
        // Crear contexto
        context = browser.newContext();
        page = context.newPage();
        
        // Navegar a la URL
        page.navigate(url);
        logger.info("Página cargada. El Inspector está grabando tus acciones...");
        
        // Pausa para que realices acciones manuales (60 segundos)
        logger.info("Tiempo disponible: 60 segundos para realizar acciones");
        logger.info("Haz clicks, escribe texto, navega por la página...");
        logger.info("El Inspector capturará todo automáticamente");
        
        try {
            Thread.sleep(60000); // 60 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Grabación interrumpida: " + e.getMessage());
        }
        
        logger.info("=== Grabación finalizada ===");
        logger.info("Copia el código generado en el Inspector");
        
        // Cerrar
        if (context != null) {
            context.close();
        }
    }
    
    /**
     * Test con configuración desde config.json
     * Usa la URL base configurada en el proyecto
     */
    @Test
    void recordWithConfiguration() {
        logger.info("=== Iniciando grabación con configuración ===");
        
        String baseUrl = ConfigurationManager.getInstance().getBaseUrl();
        logger.info("URL base: " + baseUrl);
        
        // Crear contexto
        context = browser.newContext();
        page = context.newPage();
        
        // Navegar
        page.navigate(baseUrl);
        logger.info("Página cargada. Inspector grabando...");
        
        try {
            Thread.sleep(60000); // 60 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error en grabación: " + e.getMessage());
        }
        
        logger.info("=== Grabación con configuración finalizada ===");
        
        if (context != null) {
            context.close();
        }
    }
    
    /**
     * Test con tracing habilitado (graba screenshots y snapshots)
     * Ejecutar con: gradle recordTestWithTrace
     */
    @Test
    void recordWithTracing() {
        logger.info("=== Iniciando grabación CON TRACING ===");
        logger.info("Se grabarán: screenshots, snapshots y eventos");
        
        String url = "https://example.com";
        
        // Crear contexto con tracing
        context = browser.newContext();
        page = context.newPage();
        
        // Iniciar tracing
        context.tracing().start(new BrowserContext.TracingStartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        
        logger.info("Tracing iniciado");
        
        // Navegar
        page.navigate(url);
        logger.info("Página cargada. Tracing activo...");
        
        try {
            Thread.sleep(60000); // 60 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error en tracing: " + e.getMessage());
        }
        
        // Detener tracing y guardar
        context.tracing().stop(new BrowserContext.TracingStopOptions()
                .setPath(java.nio.file.Paths.get("target/trace.zip")));
        
        logger.info("=== Tracing guardado en: target/trace.zip ===");
        logger.info("Abre con: gradle openTrace");
        
        if (context != null) {
            context.close();
        }
    }
}
