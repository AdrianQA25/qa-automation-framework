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
│       │       │   └── BasePage.java                    # Clase base con métodos reutilizables
│       │       ├── config/
│       │       │   ├── ConfigurationManager.java
│       │       │   └── DriverManager.java                # Gestión de navegador
│       │       ├── pages/
│       │       │   └── LoginPage.java                    # Page Object de ejemplo
│       │       ├── steps/
│       │       │   └── LoginSteps.java                   # Step definitions de Cucumber
│       │       ├── hooks/
│       │       │   └── Hooks.java                        # Before/After Cucumber
│       │       ├── recorder/
│       │       │   └── RecorderTest.java                 # Tests para grabar con Inspector
│       │       └── runner/
│       │           └── TestRunner.java                   # Ejecutor de pruebas
│       └── resources/
│           ├── config.json                               # Configuración del framework
│           └── features/
│               └── Login.feature                         # Archivos .feature
├── build.gradle                                          # Build file con Groovy syntax + Recording tasks
├── RECORDING.md                                          # Guía de grabación con Playwright Inspector
├── CONTRIBUTING.md                                       # Guía de contribución
├── ADVANCED_EXAMPLES.md                                  # Ejemplos avanzados
├── TROUBLESHOOTING.md                                    # Guía de troubleshooting
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

2. **Verificar versión de Java:**
```bash
java -version
# Debe ser Java 21 o superior
```

3. **Instalar dependencias:**
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

### Ejecutar con task personalizado:
```bash
gradle runTests
```

## 🎬 Grabación de Tests con Playwright Inspector

Playwright Inspector permite **grabar tus acciones manualmente y generar automáticamente el código Java**.

### Comandos de Grabación:

| Comando | Descripción |
|---------|-------------|
| `gradle recordTest` | Grabación simple - Inspector + Navegador abiertos |
| `gradle recordTestWithTrace` | Grabación con screenshots, snapshots y eventos |
| `gradle recordTestWithConfig` | Grabación usando configuración de config.json |
| `gradle openTrace` | Visualizar grabaciones guardadas (traces) |

### Ejemplo Rápido:

```bash
# 1. Ejecuta la grabación
gradle recordTest

# 2. Se abrirán el navegador y el Inspector
# 3. Realiza tus acciones manualmente (60 segundos)
# 4. Copia el código generado en el Inspector
# 5. Pégalo en tu test
```

**Para más detalles:** Ver [RECORDING.md](RECORDING.md)

## Generar Reportes Allure

### Generar reporte:
```bash
gradle test
gradle allureReport
```

### Abrir reporte en navegador:
```bash
gradle openAllureReport
```

### O con comando directo:
```bash
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
- ✅ Manejo de frames/iframes
- ✅ Upload de archivos
- ✅ **Tracing automático** (startTracing, stopTracing)
- ✅ **Screenshots en fallos** (takeScreenshotOnFailure)

## Dependencias Principales

| Dependencia | Versión | Propósito |
|------------|---------|----------|
| Playwright | 1.40.0 | Automatización del navegador |
| Cucumber | 7.14.0 | BDD y ejecución de features |
| JUnit 5 | 5.10.0 | Framework de testing |
| Allure | 2.25.0 | Generación de reportes |
| SLF4J + Logback | 2.0.9 / 1.4.12 | Logging |
| AssertJ | 3.24.1 | Aserciones mejoradas |
| GSON | 2.10.1 | Manejo de JSON |
| WebDriverManager | 5.6.3 | Gestión de drivers |

## Documentación

- [RECORDING.md](RECORDING.md) - **Guía de grabación con Playwright Inspector** 🎬
- [CONTRIBUTING.md](CONTRIBUTING.md) - Guía para contribuir al proyecto
- [ADVANCED_EXAMPLES.md](ADVANCED_EXAMPLES.md) - Ejemplos avanzados de uso
- [TROUBLESHOOTING.md](TROUBLESHOOTING.md) - Solución de problemas comunes

## Estructura de Carpetas de Salida

Después de ejecutar las pruebas:
```
target/
├── allure-results/          # Resultados para Allure
├── allure-report/           # Reporte HTML generado
├── traces/                  # Grabaciones de traces (ZIP)
├── screenshots/             # Capturas de pantalla en fallos
├── test-results/            # Resultados de JUnit
├── logs/                    # Logs de la ejecución
└── trace.zip                # Última grabación (para openTrace)
```

## Best Practices

✅ Usar Page Object Model (POM)
✅ Escribir steps legibles en Cucumber
✅ Usar esperas explícitas en lugar de sleep()
✅ Loguear acciones importantes
✅ Capturar pantallas en casos de fallo
✅ Mantener selectores como constantes
✅ Documentar métodos complejos
✅ Usar assertions claras y descriptivas
✅ Usar Playwright Inspector para grabar tests complejos

## Workflow Típico

```
1. Clonar repositorio
        ↓
2. Instalar dependencias (gradle build)
        ↓
3. Crear tests:
   - Opción A: Grabar con Inspector (gradle recordTest)
   - Opción B: Escribir manualmente
        ↓
4. Ejecutar tests (gradle test)
        ↓
5. Revisar reportes (gradle openAllureReport)
        ↓
6. ¡Tests automatizados listos!
```

## Troubleshooting

Si encuentras problemas:
1. Revisa [TROUBLESHOOTING.md](TROUBLESHOOTING.md)
2. Verifica los logs en `target/logs/`
3. Revisa el reporte Allure en `target/allure-report/`
4. Para grabación: Ver [RECORDING.md](RECORDING.md)

## Contribuir

Por favor lee [CONTRIBUTING.md](CONTRIBUTING.md) para detalles sobre cómo contribuir a este proyecto.

## Licencia

Este proyecto está bajo licencia MIT.

## Autor

Adrian QA - QA Automation Engineer
