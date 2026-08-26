package com.qa.automation.hooks;

import com.qa.automation.config.ConfigurationManager;
import com.qa.automation.config.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hooks - Configuración de Before y After para Cucumber
 */
public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp() {
        logger.info("========== Iniciando Escenario ==========");
        DriverManager.initBrowser();
        String baseUrl = ConfigurationManager.getBaseUrl();
        DriverManager.getPage().navigate(baseUrl);
        logger.info("Navegador inicializado y dirigido a: " + baseUrl);
    }

    @After
    public void tearDown(io.cucumber.java.Scenario scenario) {
        logger.info("========== Finalizando Escenario ==========");
        
        if (scenario.isFailed()) {
            logger.error("Escenario fallido: " + scenario.getName());
            
            // Captura de pantalla en caso de fallo
            if (ConfigurationManager.isTakeScreenshots()) {
                try {
                    String screenshotPath = ConfigurationManager.getScreenshotPath() + 
                            scenario.getName().replace(" ", "_") + ".png";
                    DriverManager.getPage().screenshot(
                            new com.microsoft.playwright.Page.ScreenshotOptions()
                                    .setPath(java.nio.file.Paths.get(screenshotPath))
                    );
                    Allure.addAttachment("Fallo - " + scenario.getName(), 
                            new java.io.FileInputStream(screenshotPath));
                    logger.info("Captura de pantalla guardada: " + screenshotPath);
                } catch (Exception e) {
                    logger.error("Error al capturar pantalla", e);
                }
            }
        } else {
            logger.info("Escenario ejecutado exitosamente: " + scenario.getName());
        }

        // Limpiar datos del navegador
        DriverManager.clearBrowserData();
        
        // Cerrar navegador
        DriverManager.closeBrowser();
    }
}
