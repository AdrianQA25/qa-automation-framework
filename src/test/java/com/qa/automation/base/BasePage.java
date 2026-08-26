package com.qa.automation.base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BasePage - Clase base que contiene todos los métodos reutilizables
 * para interactuar con elementos de página usando Playwright
 * 
 * Incluye soporte para tracing automático y grabación de errores
 */
public class BasePage {
    protected Page page;
    protected BrowserContext context;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(Page page) {
        this.page = page;
        this.context = page.context();
    }

    // ========== MÉTODOS DE TRACING ==========

    /**
     * Iniciar tracing con capturas de pantalla y snapshots
     * Útil para grabar y luego visualizar con Playwright Inspector
     */
    public void startTracing(String traceName) {
        if (context == null) {
            logger.warn("Contexto de navegador no disponible para tracing");
            return;
        }

        logger.info("Iniciando tracing: " + traceName);
        try {
            context.tracing().start(new BrowserContext.TracingStartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
            logger.info("✓ Tracing iniciado correctamente");
        } catch (Exception e) {
            logger.error("Error al iniciar tracing: " + e.getMessage(), e);
        }
    }

    /**
     * Detener tracing y guardar archivo
     * El archivo se guardará en: target/traces/{traceName}.zip
     */
    public void stopTracing(String traceName) {
        if (context == null) {
            logger.warn("Contexto de navegador no disponible para detener tracing");
            return;
        }

        logger.info("Deteniendo tracing: " + traceName);
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filePath = String.format("target/traces/%s_%s.zip", traceName, timestamp);
            
            context.tracing().stop(new BrowserContext.TracingStopOptions()
                    .setPath(Paths.get(filePath)));
            
            logger.info("✓ Tracing guardado en: " + filePath);
            logger.info("  Visualiza con: gradle openTrace");
        } catch (Exception e) {
            logger.error("Error al detener tracing: " + e.getMessage(), e);
        }
    }

    /**
     * Tomar captura de pantalla automática en caso de fallo
     */
    public void takeScreenshotOnFailure(String testName) {
        logger.info("Capturando pantalla por fallo en test: " + testName);
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filePath = String.format("target/screenshots/%s_FAILED_%s.png", testName, timestamp);
            
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(filePath)));
            
            logger.error("Screenshot capturada: " + filePath);
        } catch (Exception e) {
            logger.error("Error al capturar pantalla: " + e.getMessage(), e);
        }
    }

    /**
     * Habilitar/Deshabilitar modo debug
     */
    public void setDebugMode(boolean enabled) {
        if (enabled) {
            logger.info("✓ Modo DEBUG activado");
            page.pause();
        } else {
            logger.info("✓ Modo DEBUG desactivado");
        }
    }

    // ========== MÉTODOS EXISTENTES ==========

    /**
     * Navega a una URL específica
     */
    public void navigateTo(String url) {
        logger.info("Navegando a: " + url);
        page.navigate(url);
    }

    /**
     * Click en un elemento
     */
    public void click(String selector) {
        logger.info("Click en selector: " + selector);
        try {
            page.click(selector);
        } catch (PlaywrightException e) {
            logger.error("Error al hacer click en: " + selector, e);
            throw e;
        }
    }

    /**
     * Click con espera implícita
     */
    public void clickWithWait(String selector, int timeout) {
        logger.info("Click con espera en: " + selector);
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeout));
        page.click(selector);
    }

    /**
     * Doble click en un elemento
     */
    public void doubleClick(String selector) {
        logger.info("Doble click en: " + selector);
        page.dblclick(selector);
    }

    /**
     * Click derecho en un elemento
     */
    public void rightClick(String selector) {
        logger.info("Click derecho en: " + selector);
        page.click(selector, new Page.ClickOptions().setButton(MouseButton.RIGHT));
    }

    /**
     * Enviar texto a un campo de entrada
     */
    public void sendKeys(String selector, String text) {
        logger.info("Escribiendo en " + selector + ": " + text);
        try {
            page.fill(selector, text);
        } catch (PlaywrightException e) {
            logger.error("Error al escribir en: " + selector, e);
            throw e;
        }
    }

    /**
     * Limpiar un campo de entrada
     */
    public void clearField(String selector) {
        logger.info("Limpiando campo: " + selector);
        page.fill(selector, "");
    }

    /**
     * Obtener texto de un elemento
     */
    public String getText(String selector) {
        logger.info("Obteniendo texto de: " + selector);
        try {
            String text = page.textContent(selector);
            logger.info("Texto obtenido: " + text);
            return text != null ? text.trim() : "";
        } catch (PlaywrightException e) {
            logger.error("Error al obtener texto de: " + selector, e);
            throw e;
        }
    }

    /**
     * Obtener el valor de un atributo
     */
    public String getAttribute(String selector, String attribute) {
        logger.info("Obteniendo atributo '" + attribute + "' de: " + selector);
        String value = page.getAttribute(selector, attribute);
        logger.info("Valor obtenido: " + value);
        return value;
    }

    /**
     * Verificar si un elemento está visible
     */
    public boolean isVisible(String selector) {
        logger.info("Verificando visibilidad de: " + selector);
        try {
            return page.isVisible(selector);
        } catch (PlaywrightException e) {
            logger.warn("Elemento no visible: " + selector);
            return false;
        }
    }

    /**
     * Verificar si un elemento está presente en el DOM
     */
    public boolean isPresent(String selector) {
        logger.info("Verificando presencia de: " + selector);
        return page.querySelector(selector) != null;
    }

    /**
     * Verificar si un elemento está habilitado
     */
    public boolean isEnabled(String selector) {
        logger.info("Verificando si está habilitado: " + selector);
        try {
            return page.isEnabled(selector);
        } catch (PlaywrightException e) {
            logger.warn("Elemento no habilitado: " + selector);
            return false;
        }
    }

    /**
     * Esperar a que un elemento sea visible
     */
    public void waitForElement(String selector, int timeout) {
        logger.info("Esperando elemento: " + selector);
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeout));
    }

    /**
     * Esperar a que un elemento desaparezca
     */
    public void waitForElementToDisappear(String selector, int timeout) {
        logger.info("Esperando que desaparezca: " + selector);
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeout));
    }

    /**
     * Esperar una cantidad de milisegundos
     */
    public void waitForMillis(long millis) {
        logger.info("Esperando " + millis + " milisegundos");
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("Interrupción durante espera", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Obtener el número de elementos que coinciden con el selector
     */
    public int getElementCount(String selector) {
        logger.info("Contando elementos con selector: " + selector);
        int count = page.querySelectorAll(selector).size();
        logger.info("Elementos encontrados: " + count);
        return count;
    }

    /**
     * Obtener el título de la página
     */
    public String getPageTitle() {
        logger.info("Obteniendo título de página");
        return page.title();
    }

    /**
     * Obtener la URL actual
     */
    public String getCurrentUrl() {
        logger.info("Obteniendo URL actual");
        return page.url();
    }

    /**
     * Ejecutar JavaScript
     */
    public Object executeScript(String script, Object... args) {
        logger.info("Ejecutando script: " + script);
        return page.evaluate(script, args);
    }

    /**
     * Hacer scroll a un elemento
     */
    public void scrollToElement(String selector) {
        logger.info("Scrolleando a elemento: " + selector);
        page.locator(selector).scrollIntoViewIfNeeded();
    }

    /**
     * Hacer hover sobre un elemento
     */
    public void hover(String selector) {
        logger.info("Hover sobre: " + selector);
        page.hover(selector);
    }

    /**
     * Seleccionar opción en un dropdown
     */
    public void selectOption(String selector, String value) {
        logger.info("Seleccionando opción '" + value + "' en: " + selector);
        page.selectOption(selector, value);
    }

    /**
     * Obtener todas las opciones de un dropdown
     */
    public java.util.List<String> getSelectOptions(String selector) {
        logger.info("Obteniendo opciones de: " + selector);
        return page.locator(selector + " option").allTextContents();
    }

    /**
     * Verificar si un checkbox está seleccionado
     */
    public boolean isCheckboxChecked(String selector) {
        logger.info("Verificando checkbox: " + selector);
        return page.isChecked(selector);
    }

    /**
     * Marcar un checkbox
     */
    public void checkCheckbox(String selector) {
        logger.info("Marcando checkbox: " + selector);
        page.check(selector);
    }

    /**
     * Desmarcar un checkbox
     */
    public void uncheckCheckbox(String selector) {
        logger.info("Desmarcando checkbox: " + selector);
        page.uncheck(selector);
    }

    /**
     * Tomar una captura de pantalla
     */
    public void takeScreenshot(String filename) {
        logger.info("Tomando captura de pantalla: " + filename);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filename)));
    }

    /**
     * Aceptar alerta
     */
    public void acceptAlert() {
        logger.info("Aceptando alerta");
        page.onceDialog(dialog -> {
            logger.info("Alerta aceptada: " + dialog.message());
            dialog.accept();
        });
    }

    /**
     * Rechazar alerta
     */
    public void dismissAlert() {
        logger.info("Rechazando alerta");
        page.onceDialog(dialog -> {
            logger.info("Alerta rechazada: " + dialog.message());
            dialog.dismiss();
        });
    }

    /**
     * Obtener texto de la alerta
     */
    public String getAlertText() {
        logger.info("Obteniendo texto de alerta");
        final String[] alertText = new String[1];
        page.onceDialog(dialog -> {
            alertText[0] = dialog.message();
            dialog.accept();
        });
        return alertText[0];
    }

    /**
     * Cambiar a un frame específico
     */
    public Frame switchToFrame(String selector) {
        logger.info("Cambiando a frame: " + selector);
        ElementHandle iframe = page.waitForSelector(selector,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.ATTACHED));

        if (iframe == null) {
            throw new PlaywrightException("No se encontró el iframe con selector: " + selector);
        }

        Frame frame = iframe.contentFrame();
        if (frame == null) {
            throw new PlaywrightException("No se pudo obtener el frame para el selector: " + selector);
        }

        return frame;
    }

    /**
     * Cerrar la página
     */
    public void closePage() {
        logger.info("Cerrando página");
        if (page != null) {
            page.close();
        }
    }
}
