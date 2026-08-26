Feature: Login Feature
  Como usuario
  Quiero poder hacer login en la aplicación
  Para acceder a mi cuenta

  Scenario: Login exitoso con credenciales válidas
    Given Usuario navega a la página de login
    When Usuario realiza login con "user@example.com" y "password123"
    Then Usuario debería ver mensaje de bienvenida "Invalid username or password!"

  Scenario: Login fallido con contraseña incorrecta
    Given Usuario navega a la página de login
    When Usuario realiza login con "user@example.com" y "wrongpassword"
    Then Mensaje de error debería ser mostrado
    And Usuario debería ver mensaje de error "Invalid credentials"

  Scenario: Login fallido con usuario no existente
    Given Usuario navega a la página de login
    When Usuario realiza login con "nonexistent@example.com" y "password123"
    Then Mensaje de error debería ser mostrado
    And Usuario debería ver mensaje de error "User not found"
