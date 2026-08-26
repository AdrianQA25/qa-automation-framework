# Ejemplos Avanzados

## Tabla de Contenidos
1. [Manejo de Dropdowns](#manejo-de-dropdowns)
2. [Manejo de Checkboxes y Radio Buttons](#manejo-de-checkboxes-y-radio-buttons)
3. [Esperas Explícitas](#esperas-explícitas)
4. [Manejo de Modales](#manejo-de-modales)
5. [Ejecución de JavaScript](#ejecución-de-javascript)
6. [Manejo de Frames](#manejo-de-frames)
7. [Upload de Archivos](#upload-de-archivos)
8. [Ventanas Emergentes](#ventanas-emergentes)

## Manejo de Dropdowns

### Ejemplo 1: Seleccionar por valor visible
```java
public class SearchPage extends BasePage {
    private static final String CATEGORY_DROPDOWN = "select#category";
    
    public void selectCategory(String value) {
        selectByVisibleText(CATEGORY_DROPDOWN, value);
        logger.info("Categoría seleccionada: " + value);
    }
}
```

### Ejemplo 2: Seleccionar por value
```java
public void selectCategoryByValue(String value) {
    selectByValue(CATEGORY_DROPDOWN, value);
    logger.info("Categoría seleccionada por value: " + value);
}
```

### Ejemplo 3: Seleccionar por índice
```java
public void selectFirstCategory() {
    selectByIndex(CATEGORY_DROPDOWN, 0);
    logger.info("Primera categoría seleccionada");
}
```

## Manejo de Checkboxes y Radio Buttons

### Ejemplo 1: Checkbox
```java
public class SettingsPage extends BasePage {
    private static final String ACCEPT_TERMS = "input#acceptTerms";
    private static final String NEWSLETTER = "input#newsletter";
    
    public void acceptTermsAndConditions() {
        checkCheckbox(ACCEPT_TERMS);
        logger.info("Términos y condiciones aceptados");
    }
    
    public void unsubscribeNewsletter() {
        uncheckCheckbox(NEWSLETTER);
        logger.info("Newsletter desuscrito");
    }
    
    public boolean isNewsletterChecked() {
        return isCheckboxChecked(NEWSLETTER);
    }
}
```

### Ejemplo 2: Radio Buttons
```java
public class PaymentPage extends BasePage {
    private static final String CREDIT_CARD = "input[value='creditcard']";
    private static final String PAYPAL = "input[value='paypal']";
    
    public void selectCreditCardPayment() {
        click(CREDIT_CARD);
        logger.info("Pago con tarjeta de crédito seleccionado");
    }
    
    public void selectPayPalPayment() {
        click(PAYPAL);
        logger.info("Pago con PayPal seleccionado");
    }
}
```

## Esperas Explícitas

### Ejemplo 1: Esperar a que elemento sea visible
```java
public class CheckoutPage extends BasePage {
    private static final String ORDER_CONFIRMATION = "div.order-confirmation";
    
    public void waitForOrderConfirmation(int timeoutSeconds) {
        waitForVisibility(ORDER_CONFIRMATION, timeoutSeconds);
        logger.info("Confirmación de orden visible después de " + timeoutSeconds + " segundos");
    }
}
```

### Ejemplo 2: Esperar a que elemento sea clickeable
```java
public class LoginPage extends BasePage {
    private static final String SUBMIT_BUTTON = "button[type='submit']";
    
    public void clickSubmitButtonWhenReady(int timeoutSeconds) {
        waitForClickability(SUBMIT_BUTTON, timeoutSeconds);
        click(SUBMIT_BUTTON);
        logger.info("Botón clickeado después de esperar");
    }
}
```

### Ejemplo 3: Esperar a que elemento desaparezca
```java
public class LoadingPage extends BasePage {
    private static final String LOADING_SPINNER = "div.loading-spinner";
    
    public void waitForLoadingToComplete(int timeoutSeconds) {
        waitForInvisibility(LOADING_SPINNER, timeoutSeconds);
        logger.info("Loading completado");
    }
}
```

## Manejo de Modales

### Ejemplo: Modal de confirmación
```java
public class ConfirmationModal extends BasePage {
    private static final String MODAL = "div.modal";
    private static final String CONFIRM_BTN = "button.btn-confirm";
    private static final String CANCEL_BTN = "button.btn-cancel";
    
    public void confirmAction() {
        waitForVisibility(MODAL, 10);
        click(CONFIRM_BTN);
        waitForInvisibility(MODAL, 10);
        logger.info("Acción confirmada");
    }
    
    public void cancelAction() {
        waitForVisibility(MODAL, 10);
        click(CANCEL_BTN);
        waitForInvisibility(MODAL, 10);
        logger.info("Acción cancelada");
    }
}
```

## Ejecución de JavaScript

### Ejemplo 1: Scroll a elemento
```java
public class ProductPage extends BasePage {
    private static final String REVIEWS_SECTION = "section#reviews";
    
    public void scrollToReviews() {
        scrollToElement(REVIEWS_SECTION);
        logger.info("Scroll a reviews completado");
    }
}
```

### Ejemplo 2: Ejecutar JS personalizado
```java
public class HomePage extends BasePage {
    
    public void setLocalStorageValue(String key, String value) {
        String script = "localStorage.setItem('" + key + "', '" + value + "');";
        executeJavaScript(script);
        logger.info("LocalStorage actualizado");
    }
    
    public String getLocalStorageValue(String key) {
        String script = "return localStorage.getItem('" + key + "');";
        Object result = executeJavaScript(script);
        return result != null ? result.toString() : null;
    }
}
```

## Manejo de Frames

### Ejemplo: Interactuar con elemento dentro de iframe
```java
public class EmbeddedPage extends BasePage {
    private static final String IFRAME = "iframe#embedded-form";
    private static final String EMAIL_INPUT = "input[name='email']";
    
    public void fillFormInIframe(String email) {
        switchToFrame(IFRAME);
        sendKeys(EMAIL_INPUT, email);
        switchToDefaultContent();
        logger.info("Formulario en iframe rellenado");
    }
}
```

## Upload de Archivos

### Ejemplo: Upload simple
```java
public class DocumentUploadPage extends BasePage {
    private static final String FILE_INPUT = "input[type='file']";
    
    public void uploadDocument(String filePath) {
        uploadFile(FILE_INPUT, filePath);
        logger.info("Archivo subido: " + filePath);
    }
}
```

## Ventanas Emergentes

### Ejemplo: Manejo de alertas
```java
public class AlertsPage extends BasePage {
    private static final String ALERT_BUTTON = "button#show-alert";
    
    public void handleSimpleAlert() {
        click(ALERT_BUTTON);
        acceptAlert();
        logger.info("Alert aceptada");
    }
    
    public String handleConfirmAlert() {
        click(ALERT_BUTTON);
        String alertText = getAlertText();
        dismissAlert();
        logger.info("Alert rechazada. Texto: " + alertText);
        return alertText;
    }
}
```

## Steps con Cucumber

### Ejemplo completo: Escenario de login con todos los patrones

**Feature File:**
```gherkin
Feature: Login Avanzado
  
  Scenario: Login con validaciones completas
    Given Usuario navega a la página de login
    When Usuario ingresa email "test@example.com"
    And Usuario ingresa contraseña "password123"
    And Usuario acepta los términos y condiciones
    And Usuario selecciona el país "Colombia"
    And Usuario hace click en login
    Then Usuario debería ver mensaje de bienvenida
    And Datos de usuario deberían estar en localStorage
```

**Step Implementation:**
```java
@Given("Usuario navega a la página de login")
public void navigateToLogin() {
    loginPage.navigateTo();
}

@When("Usuario ingresa email {string}")
public void enterEmail(String email) {
    loginPage.enterEmail(email);
}

@And("Usuario ingresa contraseña {string}")
public void enterPassword(String password) {
    loginPage.enterPassword(password);
}

@And("Usuario acepta los términos y condiciones")
public void acceptTerms() {
    settingsPage.acceptTermsAndConditions();
}

@And("Usuario selecciona el país {string}")
public void selectCountry(String country) {
    loginPage.selectCountry(country);
}

@And("Usuario hace click en login")
public void clickLogin() {
    loginPage.clickLoginButton();
}

@Then("Usuario debería ver mensaje de bienvenida")
public void verifyWelcomeMessage() {
    Assert.assertTrue(loginPage.isWelcomeMessageVisible());
}

@And("Datos de usuario deberían estar en localStorage")
public void verifyLocalStorage() {
    String userData = loginPage.getLocalStorageValue("user");
    Assert.assertNotNull(userData);
}
```

## Best Practices

✅ **Siempre usar esperas explícitas en lugar de sleep**
✅ **Loguear acciones importantes**
✅ **Usar selectores CSS en lugar de XPath**
✅ **Mantener los selectores como constantes**
✅ **Capturar pantallas en escenarios fallidos**
✅ **Usar nombres descriptivos en métodos**
✅ **Documentar métodos complejos**

---

Para más información, ver [README.md](README.md)
