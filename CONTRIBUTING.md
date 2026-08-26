# Guía de Contribución

Gracias por tu interés en contribuir a este framework. Aquí encontrarás las pautas para hacerlo correctamente.

## Cómo Contribuir

### 1. Fork el Repositorio
```bash
git clone https://github.com/AdrianQA25/qa-automation-framework.git
cd qa-automation-framework
```

### 2. Crear una Rama
```bash
git checkout -b feature/mi-caracteristica
```

### 3. Hacer Cambios
- Mantener la estructura del proyecto
- Seguir las convenciones de nombres
- Escribir código limpio y documentado
- Agregar logs usando SLF4J

### 4. Commit
```bash
git commit -m "Descripción clara del cambio"
```

### 5. Push
```bash
git push origin feature/mi-caracteristica
```

### 6. Pull Request
- Describir los cambios realizados
- Incluir referencias a issues relacionados
- Asegurarse de que todas las pruebas pasen

## Convenciones de Código

### Nombres de Variables
```java
// ✅ Bien
private static final String LOGIN_BUTTON = "button[type='submit']";
private int timeoutMillis;

// ❌ Mal
private static final String btn = "button[type='submit']";
private int t;
```

### Comentarios y Documentación
```java
/**
 * Descripción clara del método
 * @param selector CSS selector
 * @return true si el elemento es visible
 */
public boolean isVisible(String selector) {
    // implementación
}
```

### Métodos en Page Objects
```java
public class MyPage extends BasePage {
    private static final String ELEMENT = "selector";
    
    public MyPage(Page page) {
        super(page);
    }
    
    public void performAction() {
        click(ELEMENT);
    }
}
```

## Estructura de Archivos

Nuevas page objects deben ir en: `src/test/java/com/qa/automation/pages/`

Nuevos steps deben ir en: `src/test/java/com/qa/automation/steps/`

Nuevos features deben ir en: `src/test/resources/features/`

## Testing

Antes de hacer un Pull Request:

1. Ejecutar todas las pruebas
```bash
gradle test
```

2. Verificar que no haya errores de compilación

3. Revisar los logs en `target/logs/`

## Reportar Issues

Abrir un issue con:
- Descripción clara del problema
- Pasos para reproducir
- Comportamiento esperado vs actual
- Información del sistema (OS, Java version)

## Preguntas o Dudas

Abrir una issue con la etiqueta `question`

---

**¡Gracias por contribuir! 🎉**
