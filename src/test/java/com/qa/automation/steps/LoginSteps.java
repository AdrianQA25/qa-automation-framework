package com.qa.automation.steps;

import com.qa.automation.config.DriverManager;
import com.qa.automation.pages.LoginPage;
import io.cucumber.java.en.*;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoginSteps - Step definitions para escenarios de login
 */
public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private LoginPage loginPage;

    @Given("Usuario navega a la página de login")
    public void userNavigatesToLoginPage() {
        logger.info("Navegando a página de login");
        loginPage = new LoginPage(DriverManager.getPage());
        // URL debería ser configurada en hooks/setup
    }

    @When("Usuario ingresa username {string}")
    public void userEntersUsername(String username) {
        logger.info("Ingresando username: " + username);
        loginPage.enterUsername(username);
    }

    @When("Usuario ingresa password {string}")
    public void userEntersPassword(String password) {
        logger.info("Ingresando password");
        loginPage.enterPassword(password);
    }

    @When("Usuario hace click en botón login")
    public void userClicksLoginButton() {
        logger.info("Haciendo click en botón login");
        loginPage.clickLoginButton();
    }

    @When("Usuario realiza login con {string} y {string}")
    public void userLogsIn(String username, String password) {
        logger.info("Realizando login con username: " + username);
        loginPage = new LoginPage(DriverManager.getPage());
        loginPage.login(username, password);
    }

    @Then("Usuario debería ver mensaje de error {string}")
    public void userShouldSeeErrorMessage(String expectedError) {
        logger.info("Verificando mensaje de error");
        String actualError = loginPage.getErrorMessage();
        Assertions.assertThat(actualError).contains(expectedError);
    }

    @Then("Usuario debería ver mensaje de bienvenida {string}")
    public void userShouldSeeWelcomeMessage(String expectedWelcome) {
        logger.info("Verificando mensaje de bienvenida");
        String actualWelcome = loginPage.getWelcomeMessage();
        Assertions.assertThat(actualWelcome).contains(expectedWelcome);
    }

    @Then("Mensaje de error debería ser mostrado")
    public void errorMessageShouldBeDisplayed() {
        logger.info("Verificando si mensaje de error es mostrado");
        Assertions.assertThat(loginPage.isErrorMessageDisplayed()).isTrue();
    }
}
