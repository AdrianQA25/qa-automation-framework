package com.qa.automation.pages;

import com.microsoft.playwright.Page;
import com.qa.automation.base.BasePage;

/**
 * Clase de ejemplo para demostrar cómo extender BasePage
 * Reemplaza los selectores y métodos según tus necesidades
 */
public class LoginPage extends BasePage {
    
    // Selectores
    private static final String USERNAME_INPUT = "input[name='username']";
    private static final String PASSWORD_INPUT = "input[name='password']";
    private static final String LOGIN_BUTTON = "button[type='submit']";
    private static final String ERROR_MESSAGE = ".error-message";
    private static final String WELCOME_TEXT = ".welcome-message";

    public LoginPage(Page page) {
        super(page);
    }

    /**
     * Ingresar nombre de usuario
     */
    public LoginPage enterUsername(String username) {
        logger.info("Ingresando usuario: " + username);
        sendKeys(USERNAME_INPUT, username);
        return this;
    }

    /**
     * Ingresar contraseña
     */
    public LoginPage enterPassword(String password) {
        logger.info("Ingresando contraseña");
        sendKeys(PASSWORD_INPUT, password);
        return this;
    }

    /**
     * Click en botón de login
     */
    public void clickLoginButton() {
        logger.info("Click en botón login");
        click(LOGIN_BUTTON);
    }

    /**
     * Realizar login completo
     */
    public void login(String username, String password) {
        logger.info("Realizando login con usuario: " + username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Obtener mensaje de error
     */
    public String getErrorMessage() {
        logger.info("Obteniendo mensaje de error");
        return getText(ERROR_MESSAGE);
    }

    /**
     * Verificar si hay mensaje de error
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Verificando si se muestra mensaje de error");
        return isVisible(ERROR_MESSAGE);
    }

    /**
     * Obtener mensaje de bienvenida
     */
    public String getWelcomeMessage() {
        logger.info("Obteniendo mensaje de bienvenida");
        return getText(WELCOME_TEXT);
    }
}
