# Guía de Troubleshooting

## Tabla de Contenidos
1. [Errores de Elementos No Encontrados](#errores-de-elementos-no-encontrados)
2. [Problemas de Timeouts](#problemas-de-timeouts)
3. [Errores de Conexión](#errores-de-conexión)
4. [Problemas con Cucumber](#problemas-con-cucumber)
5. [Errores de Reportes Allure](#errores-de-reportes-allure)
6. [Problemas de Dependencias](#problemas-de-dependencias)
7. [Problemas con Playwright](#problemas-con-playwright)
8. [Debugging y Logs](#debugging-y-logs)
9. [Errores Comunes y Soluciones](#errores-comunes-y-soluciones)

---

## Errores de Elementos No Encontrados

### Error: `NoSuchElementException`

**Problema:**
```
NoSuchElementException: no such element: Unable to locate element
```

**Causas comunes:**
- Selector CSS/XPath incorrecto
- Elemento no ha cargado aún
- Elemento está dentro de un iframe
- Elemento está oculto o tiene display: none

**Soluciones:**

1. **Verificar el selector en el navegador:**
```javascript
// Abrir consola del navegador (F12) y ejecutar:
document.querySelector("tu-selector-aqui")
// Si devuelve null, el selector es incorrecto
```

2. **Usar espera explícita:**
```java
public void clickElement(String selector) {
    waitForVisibility(selector, 10); // Esperar 10 segundos
    click(selector);
}
```

3. **Revisar si está en un iframe:**
```java
public void handleElementInIframe(String iframeSelector, String elementSelector) {
    switchToFrame(iframeSelector);
    click(elementSelector);
    switchToDefaultContent();
}
```

4. **Verificar visibilidad:**
```java
if (isVisible(selector)) {
    click(selector);
} else {
    logger.error("Elemento no es visible: " + selector);
}
```

---

## Problemas de Timeouts

### Error: `TimeoutException`

**Problema:**
```
TimeoutException: Timeout 10000ms exceeded. waiting for locator
```

**Causas comunes:**
- Timeout muy corto
- Elemento tarda más en cargar
- Selector incorrecto
- Página está en estado de carga indefinida

**Soluciones:**

1. **Aumentar el timeout:**
```java
// En BasePage.java
private static final int DEFAULT_TIMEOUT = 15; // Aumentar de 10 a 15

// O en método específico
waitForVisibility(selector, 20); // 20 segundos
```

2. **Esperar a que cargue completamente:**
```java
public void waitForPageLoad() {
    try {
        Thread.sleep(2000); // Espera general (úsalo como último recurso)
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
```

3. **Usar espera implícita:**
```java
// En BasePage constructor
page.setDefaultTimeout(15000); // 15 segundos
```

4. **Debuggear el estado de la página:**
```java
public void debugPageState(String selector) {
    try {
        boolean isVisible = isVisible(selector);
        boolean isPresent = isPresent(selector);
        logger.info("Selector: " + selector);
        logger.info("Visible: " + isVisible);
        logger.info("Presente: " + isPresent);
    } catch (Exception e) {
        logger.error("Error al debuggear: " + e.getMessage());
    }
}
```

---

## Errores de Conexión

### Error: `ERR_CONNECTION_REFUSED`

**Problema:**
```
net::ERR_CONNECTION_REFUSED
No se puede conectar a localhost:8080
```

**Causas:**
- Servidor no está corriendo
- Puerto incorrecto
- Firewall bloqueando conexión
- Base de datos no disponible

**Soluciones:**

1. **Verificar que el servidor esté corriendo:**
```bash
# Verificar si el puerto está en uso
lsof -i :8080  # macOS/Linux
netstat -ano | findstr :8080  # Windows
```

2. **Iniciar el servidor:**
```bash
# Ejemplo para aplicación Spring Boot
gradle bootRun

# O si está en Docker
docker-compose up -d
```

3. **Cambiar la URL en configuración:**
```java
// En application.properties
app.base.url=http://localhost:3000
```

4. **Aumentar tiempo de conexión:**
```java
// En BasePage.java
page.context().browser().newContext(
    new Browser.NewContextOptions()
        .setTimeout(30000) // 30 segundos
);
```

---

## Problemas con Cucumber

### Error: `Step undefined`

**Problema:**
```
Step 'Usuario navega a la página de login' is undefined
```

**Causas:**
- Step implementation no existe
- Paquete de steps no es escanneado
- Nombre del step no coincide

**Soluciones:**

1. **Verificar que el step esté implementado:**
```java
// En src/test/java/com/qa/automation/steps/LoginSteps.java
@Given("Usuario navega a la página de login")
public void navigateToLogin() {
    loginPage.navigateTo();
}
```

2. **Verificar configuración de Cucumber:**
```java
// En src/test/resources/cucumber.properties
cucumber.glue=com.qa.automation.steps
```

3. **Verificar el nombre exacto:**
```gherkin
# Feature file
Given Usuario navega a la página de login

# Step implementation debe tener exactamente el mismo nombre
@Given("Usuario navega a la página de login")
```

### Error: `No scenarios found`

**Problema:**
```
No scenarios found in 'src/test/resources/features/'
```

**Soluciones:**

1. **Verificar que existan archivos .feature:**
```bash
ls -la src/test/resources/features/
```

2. **Verificar sintaxis del .feature:**
```gherkin
Feature: Descripción de la funcionalidad
  
  Scenario: Nombre del escenario
    Given paso 1
    When paso 2
    Then paso 3
```

3. **Verificar que Runner esté bien configurado:**
```java
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.qa.automation.steps",
    plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class CucumberRunner {
}
```

---

## Errores de Reportes Allure

### Error: `Allure report not generated`

**Problema:**
```
Target directory does not contain Allure results
```

**Causas:**
- Allure no se ejecutó correctamente
- Resultados no se generaron
- Plugin no configurado

**Soluciones:**

1. **Verificar configuración en build.gradle:**
```gradle
dependencies {
    testImplementation 'io.qameta.allure:allure-playwright:2.21.0'
}

test {
    useJUnitPlatform()
    systemProperty 'allure.results.directory', 'target/allure-results'
}
```

2. **Generar y abrir reporte:**
```bash
# Ejecutar pruebas
gradle test

# Generar reporte
allure generate target/allure-results -o target/allure-report --clean

# Abrir reporte
allure open target/allure-report
```

3. **Verificar permisos:**
```bash
chmod -R 755 target/allure-results
```

---

## Problemas de Dependencias

### Error: `Could not find gradle dependency`

**Problema:**
```
Could not find org.gradle:gradle-core:7.0
```

**Causas:**
- Versión incorrecta
- Repositorio no configurado
- Conflicto de dependencias

**Soluciones:**

1. **Verificar versión en build.gradle:**
```gradle
dependencies {
    testImplementation 'io.cucumber:cucumber-java:7.14.0'
    testImplementation 'io.cucumber:cucumber-junit:7.14.0'
}
```

2. **Limpiar caché y descargar:**
```bash
gradle clean
gradle build --refresh-dependencies
```

3. **Verificar repositorios:**
```gradle
repositories {
    mavenCentral()
    maven { url 'https://repo.maven.apache.org/maven2' }
}
```

---

## Problemas con Playwright

### Error: `Playwright browser not found`

**Problema:**
```
Failed to launch browser. Executable doesn't exist at /path/to/browser
```

**Soluciones:**

1. **Instalar navegadores:**
```bash
# Descargar navegadores de Playwright
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"

# O en Gradle
gradle playwrightInstall
```

2. **Verificar instalación:**
```bash
ls ~/Library/Caches/ms-playwright/ # macOS
ls ~/.cache/ms-playwright/         # Linux
```

### Error: `Browser crashed`

**Problema:**
```
Browser process crashed
```

**Causas:**
- Recursos insuficientes
- Incompatibilidad de versiones
- Problemas de memoria

**Soluciones:**

1. **Aumentar memoria:**
```bash
export JAVA_OPTS="-Xmx2048m"
gradle test
```

2. **Usar modo headless:**
```java
// En BasePage.java
BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
    .setHeadless(true);
```

3. **Actualizar Playwright:**
```gradle
dependencies {
    testImplementation 'com.microsoft.playwright:playwright:1.40.0'
}
```

---

## Debugging y Logs

### Revisar logs de pruebas

**Ubicaciones:**
```
target/logs/automation.log        # Logs de la aplicación
target/allure-results/            # Resultados de Allure
build/reports/tests/              # Reportes de JUnit
```

### Aumentar nivel de logging

**En application-test.properties:**
```properties
logging.level.root=INFO
logging.level.com.qa.automation=DEBUG
logging.level.com.microsoft.playwright=DEBUG
logging.file.name=target/logs/automation.log
```

### Capturas de pantalla en fallos

**En BasePage.java:**
```java
public void takeScreenshot(String name) {
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String filename = "target/screenshots/" + name + "_" + timestamp + ".png";
    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(filename)));
    logger.info("Screenshot guardada: " + filename);
}
```

**Uso en steps:**
```java
@Then("Verificar elemento visible")
public void verifyElement() {
    try {
        Assert.assertTrue(page.isVisible(selector));
    } catch (AssertionError e) {
        page.takeScreenshot("error_verification");
        throw e;
    }
}
```

---

## Errores Comunes y Soluciones

| Error | Causa | Solución |
|-------|-------|----------|
| `StaleElementReferenceException` | Elemento fue removido del DOM | Usar nuevas referencias, evitar guardar elementos |
| `ElementNotInteractableException` | Elemento no es clickeable | Esperar visibilidad y clickeabilidad |
| `WebDriverException: unknown error: call function result missing 'value'` | Problema en comunicación con Playwright | Reiniciar navegador |
| `Gradle build failed` | Error en compilación | Verificar sintaxis Java y dependencias |
| `Feature file not found` | Ruta incorrecta | Usar ruta relativa `src/test/resources/features/` |
| `Port already in use` | Proceso anterior no terminó | `lsof -i :8080` y matar proceso |
| `Authentication failed` | Credenciales incorrectas | Verificar usuario/contraseña en archivo de config |
| `Network timeout` | Conexión lenta | Aumentar timeout en BasePage |

---

## Contacto y Soporte

Si el problema persiste:
1. Revisar los logs completos
2. Tomar screenshot/video del error
3. Abrir un issue en el repositorio con detalles
4. Contactar al equipo de QA Automation

**Archivos útiles para compartir:**
- `target/logs/automation.log`
- `target/allure-results/`
- Screenshot del error
- Feature file que falla
- Versión de Java: `java -version`
- Versión de Gradle: `gradle -v`

---

Para más información, ver [README.md](README.md) y [ADVANCED_EXAMPLES.md](ADVANCED_EXAMPLES.md)
