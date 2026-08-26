# Guía de Grabación de Tests con Playwright Inspector

## Tabla de Contenidos
1. [Introducción](#introducción)
2. [Requisitos](#requisitos)
3. [Método 1: Grabación Simple (PWDEBUG)](#método-1-grabación-simple-pwdebug)
4. [Método 2: Grabación con Tracing Completo](#método-2-grabación-con-tracing-completo)
5. [Método 3: Grabación con Configuración](#método-3-grabación-con-configuración)
6. [Cómo Copiar el Código Generado](#cómo-copiar-el-código-generado)
7. [Visualizar Grabaciones](#visualizar-grabaciones)
8. [Troubleshooting](#troubleshooting)

---

## Introducción

Playwright Inspector es una herramienta poderosa que permite **grabar tus acciones manualmente en el navegador y genera automáticamente el código Java** correspondiente.

**Ventajas:**
- ✅ No necesitas escribir selectores manualmente
- ✅ Genera código Java optimizado
- ✅ Perfecto para aprender la sintaxis de Playwright
- ✅ Útil para crear tests complejos rápidamente

---

## Requisitos

### Instalados en tu sistema:
```bash
# Verificar Java 21
java -version
# Debe mostrar: openjdk 21.x.x

# Verificar Gradle
gradle -v
# Debe mostrar: Gradle 8.0 o superior

# Verificar Node.js (opcional, para visualizar traces)
node -v
npm -v
```

### En el proyecto:
- ✅ `RecorderTest.java` creado
- ✅ `build.gradle` actualizado con tasks
- ✅ Dependencia de Playwright configurada

---

## Método 1: Grabación Simple (PWDEBUG)

### 🎬 Paso a paso:

**1. Abre terminal en la raíz del proyecto**

**2. Ejecuta el comando:**
```bash
gradle recordTest
```

**3. Verás:**
```
============================================================
🎬 INICIANDO GRABACIÓN CON PLAYWRIGHT INSPECTOR
============================================================
1. Se abrirá el navegador y el Inspector simultáneamente
2. Realiza tus acciones manualmente (60 segundos)
3. El Inspector capturará el código Java automáticamente
4. Copia el código generado en tu test
============================================================
```

**4. Se abren 2 ventanas:**
- 🌐 Navegador (donde haces las acciones)
- 🔍 Inspector de Playwright (graba y genera código)

**5. En 60 segundos:**
- Haz clicks en elementos
- Escribe en campos
- Navega por la página
- El Inspector lo captura TODO

**6. Copia el código:**
- En el Inspector, selecciona el código generado
- Cópialo a tu test (archivo `.java`)

### Ejemplo de código generado:
```java
// Código generado automáticamente por Inspector
page.navigate("https://example.com");
page.click("input[name='username']");
page.fill("input[name='username']", "usuario123");
page.click("button:has-text('Login')");
page.waitForLoadState("networkidle");
```

---

## Método 2: Grabación con Tracing Completo

### 📹 Para grabaciones más detalladas:

**Ejecuta:**
```bash
gradle recordTestWithTrace
```

**Se grabará:**
- ✓ Screenshots de cada acción
- ✓ Snapshots del DOM (estado de la página)
- ✓ Eventos del navegador
- ✓ Código Java generado
- ✓ Timeline de ejecución

**Resultado:** `target/trace.zip`

**Usar traces para debugging:**
```bash
gradle openTrace
```

Esto abre un visualizador interactivo donde puedes:
- Ver cada acción paso a paso
- Ver screenshots de cada momento
- Inspeccionar el DOM en cada punto
- Revisar los eventos del navegador

---

## Método 3: Grabación con Configuración

### ⚙️ Usa la configuración de tu proyecto:

**Ejecuta:**
```bash
gradle recordTestWithConfig
```

**Ventajas:**
- Usa la URL base del `config.json`
- Respeta la configuración de navegador
- Hereda la configuración general del proyecto

**Archivo `config.json` esperado:**
```json
{
  "baseUrl": "https://tu-aplicacion.com",
  "browser": "chromium",
  "headless": false,
  "timeout": 10000
}
```

---

## Cómo Copiar el Código Generado

### 📋 Paso a paso:

**1. Durante la grabación en Playwright Inspector:**

```
┌─────────────────────────────────────────────────┐
│ Playwright Inspector                            │
├─────────────────────────────────────────────────┤
│                                                 │
│  page.navigate("https://example.com");         │
│  page.click("input[name='username']");         │
│  page.fill("input[name='username']", "test");  │
│  page.click("button");                         │
│  page.waitForSelector("h1");                   │
│                                                 │
└─────────────────────────────────────────────────┘
```

**2. Selecciona TODO el código (Ctrl+A o Cmd+A)**

**3. Copia (Ctrl+C o Cmd+C)**

**4. Pega en tu test Java:**

```java
@Test
void miPruebaGrabada() {
    page.navigate("https://example.com");
    page.click("input[name='username']");
    page.fill("input[name='username']", "test");
    page.click("button");
    page.waitForSelector("h1");
}
```

---

## Visualizar Grabaciones

### 📺 Ver traces capturados:

**Comando:**
```bash
gradle openTrace
```

**Se abre un navegador con visualizador interactivo donde puedes:**

1. **Ver timeline** - Línea temporal de acciones
2. **Screenshots** - Captura de cada momento
3. **DOM Snapshots** - Estado del HTML en cada paso
4. **Eventos** - Network, console, etc.
5. **Logs** - Mensajes de la aplicación

### Navegar en el visualizador:
- ⏮️ Ir al principio
- ⏪ Atrás
- ▶️ Reproducir
- ⏭️ Adelante
- 🔍 Zoom en screenshots

---

## Workflow Completo

### 🔄 Flujo típico de uso:

```
1. Ejecuta: gradle recordTest
           ↓
2. Se abren navegador + Inspector
           ↓
3. Haces acciones manualmente (clicks, escritura, etc.)
           ↓
4. Inspector captura todo
           ↓
5. Copia código del Inspector
           ↓
6. Pega en RecorderTest.java o tu test
           ↓
7. Ejecuta: gradle test
           ↓
8. ¡Test automatizado listo!
```

---

## Ejemplos Prácticos

### Ejemplo 1: Grabar Login

```bash
gradle recordTest
```

En el Inspector, realiza:
1. Click en campo username
2. Escribe: `admin`
3. Click en campo password
4. Escribe: `password123`
5. Click en botón Login
6. Espera a que cargue dashboard

Código generado:
```java
@Test
void testLogin() {
    page.navigate("https://app.example.com/login");
    page.click("input[name='username']");
    page.fill("input[name='username']", "admin");
    page.click("input[name='password']");
    page.fill("input[name='password']", "password123");
    page.click("button[type='submit']");
    page.waitForURL("**/dashboard");
}
```

### Ejemplo 2: Grabar con Validación

```bash
gradle recordTestWithTrace
```

Realiza acciones y luego valida:
```java
@Test
void testFormSubmission() {
    page.navigate("https://example.com/form");
    page.fill("input[name='name']", "Juan");
    page.fill("input[name='email']", "juan@example.com");
    page.click("button[type='submit']");
    
    // Validar resultado
    String message = page.locator(".success-message").textContent();
    assertThat(message).contains("Formulario enviado");
}
```

---

## Troubleshooting

### ❌ "El Inspector no se abre"

**Solución:**
```bash
# Verifica que PWDEBUG está bien seteado
export PWDEBUG=1
gradle recordTest
```

### ❌ "Error: command not found: npx"

**Solución:**
```bash
# Instala Node.js desde https://nodejs.org/
# O usa: npm install -g @playwright/test
```

### ❌ "El navegador se cierra inmediatamente"

**Solución:**
El test probablemente terminó los 60 segundos. Abre el Inspector más rápido o aumenta el tiempo en `RecorderTest.java`:

```java
// Cambiar de 60000 a 120000 (2 minutos)
Thread.sleep(120000);
```

### ❌ "No veo el código generado en el Inspector"

**Solución:**
1. Asegúrate de hacer alguna acción (click, escribir, etc.)
2. El Inspector genera código **mientras interactúas**
3. Si no hay acciones, no hay código

### ❌ "Error: gradle: command not found"

**Solución:**
```bash
# Verifica Gradle está instalado
gradle -v

# Si no está, instálalo desde: https://gradle.org/install/
```

---

## Tips y Mejores Prácticas

### 💡 Tips útiles:

1. **Sé específico:** Realiza acciones claras y pausadas
2. **Espera a que cargue:** Déjalo cargar completamente antes de actuar
3. **Usa nombres significativos:** Cuando copies el código, renombra variables
4. **Revisa selectores:** El código generado es bueno pero revísalo
5. **Reutiliza métodos:** Coloca acciones comunes en BasePage.java

### ✅ Mejores prácticas:

```java
// ❌ Evita: Código generado directamente en test
page.click("button");
page.waitForSelector("h1");

// ✅ Mejor: Encapsula en métodos de PageObject
loginPage.clickLoginButton();
loginPage.waitForDashboard();
```

---

## Referencia Rápida

| Comando | Propósito |
|---------|----------|
| `gradle recordTest` | Grabación simple con Inspector |
| `gradle recordTestWithTrace` | Grabación con screenshots y DOM |
| `gradle recordTestWithConfig` | Grabación usando config.json |
| `gradle openTrace` | Visualizar grabaciones (traces) |
| `PWDEBUG=1 gradle test` | Ejecutar cualquier test con Inspector |

---

## Próximos Pasos

1. ✅ Graba algunos tests básicos
2. ✅ Copia el código generado
3. ✅ Colócalo en tus Page Objects
4. ✅ Refactoriza para reutilizabilidad
5. ✅ Integra en tu suite de tests

---

## Más Información

- [Documentación oficial de Playwright Inspector](https://playwright.dev/java/docs/debug#inspector)
- [Playwright Java API](https://playwright.dev/java/docs/api/class-page)
- [Guía de selectores CSS](https://www.w3schools.com/cssref/selectors_intro.asp)

---

Para más ayuda, revisa [TROUBLESHOOTING.md](TROUBLESHOOTING.md) o [README.md](README.md)
