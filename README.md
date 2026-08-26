# QA Automation Framework

Framework robusto y mantenible para automatización de pruebas con:
- **Java 21**
- **Playwright** para automatización del navegador
- **Cucumber** para BDD (Behavior-Driven Development)
- **JUnit 5** para ejecución de pruebas
- **Page Object Model (POM)** para estructura mantenible
- **Allure** para reportería avanzada

## Estructura del Proyecto

```
qa-automation-framework/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/qa/automation/
│       │       ├── base/
│       │       │   └── BasePage.java          # Clase base con métodos reutilizables
│       │       ├── config/
│       │       │   ├── ConfigurationManager.java
│       │       │   └── DriverManager.java      # Gestión de navegador
│       │       ├── pages/
│       │       │   └── LoginPage.java          # Page Object de ejemplo
│       │       ├── steps/
│       │       │   └── LoginSteps.java         # Step definitions de Cucumber
│       │       ├── hooks/
│       │       │   └── Hooks.java              # Before/After Cucumber
│       │       └── runner/
│       │           └── TestRunner.java         # Ejecutor de pruebas
│       └── resources/
│           ├── config.json                     # Configuración del framework
│           └── features/
│               └── Login.feature               # Archivos .feature
├── build.gradle.kts
└── README.md
```

## Requisitos

- Java 21 o superior
- Gradle 8.0 o superior

## Instalación

1. **Clonar el repositorio:**
```bash
git clone https://github.com/AdrianQA25/qa-automation-framework.git
cd qa-automation-framework
```

2. **Instalar dependencias:**
```bash
gradle build
```

## Configuración

Editar `src/test/resources/config.json` para configurar:
- `baseUrl`: URL base de la aplicación
- `browser`: chromium, firefox, webkit
- `headless`: true/false
- `timeout`: Timeout en milisegundos
- `takeScreenshots`: Capturar pantallas en fallos

## Ejecución de Pruebas

### Ejecutar todas las pruebas:
```bash
gradle test
```

### Ejecutar tag específico:
```bash
gradle test --args="--tags=@smoke"
```

### Ejecutar feature específica:
```bash
gradle test --args="--features=src/test/resources/features/Login.feature"
```

## Generar Reportes Allure

### Generar reporte:
```bash
gradle test
allure serve target/allure-results
```

## Características Principales

### BasePage.java
Métodos reutilizables para:
- ✅ Click, doble click, click derecho
- ✅ Enviar texto (sendKeys)
- ✅ Obtener texto (getText)
- ✅ Obtener atributos
- ✅ Esperas explícitas
- ✅ Validaciones (isVisible, isPresent, isEnabled)
- ✅ Manejo de dropdowns
- ✅ Manejo de checkboxes
- ✅ Capturas de pantalla
- ✅ Manejo de alertas
- ✅ Ejecución de JavaScript
- ✅ Manejo de frames
- ✅ Scroll, hover, etc.

### Page Object Model
Cada página tiene su propia clase que extiende `BasePage`:
```java
public class LoginPage extends BasePage {
    private static final String USERNAME_INPUT = "input[name='username']";
    
    public LoginPage(Page page) {
        super(page);
    }
    
    public void enterUsername(String username) {
        sendKeys(USERNAME_INPUT, username);
    }
}
```

### Hooks
Ejecutan automáticamente antes y después de cada escenario:
- Inicializar navegador
- Navegar a URL base
- Capturar pantallas en fallos
- Limpiar datos del navegador
- Cerrar navegador

### Logging
Logs detallados en cada acción usando SLF4J + Logback

## Mejores Prácticas

1. **Selectores CSS**: Preferir CSS selectors sobre XPath para mejor rendimiento
2. **Nombres descriptivos**: Usar nombres claros en métodos y variables
3. **Reutilización**: Heredar de BasePage para acceder a métodos comunes
4. **Logging**: Loguear acciones importantes para debugging
5. **Waits**: Usar esperas explícitas en lugar de sleep
6. **Screenshots**: Capturar pantallas en escenarios fallidos
7. **Assertions**: Usar AssertJ para aserciones claras

## Roadmap Futuro

- [ ] Integración con CI/CD
- [ ] Ejecución paralela mejorada
- [ ] Métodos para upload de archivos
- [ ] Manejo avanzado de modales
- [ ] Ejemplos de API testing
- [ ] Data-driven testing
- [ ] Integración con TestNG

## Soporte

Para reportar issues o sugerencias, abrir una issue en el repositorio.

## Autor

Adrian QA - QA Automation Engineer
