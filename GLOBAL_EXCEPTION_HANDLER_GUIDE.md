# 🛡️ GlobalExceptionHandler - Guía de Implementación

## ✅ ¿Qué hemos implementado?

### 📁 **Archivos creados:**
```
📂 exception/
├── GlobalExceptionHandler.java     # Manejador global de errores
├── ErrorResponse.java              # Estructura de respuesta de errores
├── UserAlreadyExistsException.java # Excepción para usuarios duplicados
└── ResourceNotFoundException.java   # Excepción para recursos no encontrados
```

### 🔧 **Archivos mejorados:**
- `AuthServiceImpl.java` - Ahora usa excepciones específicas y logging
- `AuthRestController.java` - Agregadas validaciones @Valid y logging

## 🎯 **Beneficios implementados:**

### ✅ **Respuestas de error consistentes:**
```json
{
  "timestamp": "2024-01-15 10:30:45",
  "status": 400,
  "error": "Error de validación",
  "message": "Datos de entrada inválidos",
  "details": {
    "email": "El email es obligatorio",
    "password": "La contraseña debe tener al menos 8 caracteres"
  }
}
```

### ✅ **Manejo específico de errores:**
- **401 Unauthorized**: Credenciales incorrectas
- **404 Not Found**: Recursos no encontrados
- **409 Conflict**: Usuario ya existe
- **400 Bad Request**: Errores de validación
- **500 Internal Server Error**: Errores internos

### ✅ **Logging completo:**
- Logs de info para operaciones exitosas
- Logs de warning para errores de usuario
- Logs de error para problemas internos

## 🧪 **Ejemplos de uso:**

### **1. Registro con email duplicado:**
```bash
POST /api/auth/registro
{
  "nombre": "Juan",
  "email": "usuario@existente.com",
  "password": "123456"
}
```

**Respuesta (409 Conflict):**
```json
{
  "timestamp": "2024-01-15 10:30:45",
  "status": 409,
  "error": "Usuario ya existe",
  "message": "Ya existe un usuario registrado con el email: usuario@existente.com"
}
```

### **2. Login con credenciales incorrectas:**
```bash
POST /api/auth/login
{
  "email": "usuario@test.com",
  "password": "incorrecta"
}
```

**Respuesta (401 Unauthorized):**
```json
{
  "timestamp": "2024-01-15 10:30:45",
  "status": 401,
  "error": "Credenciales inválidas",
  "message": "Email o contraseña incorrectos"
}
```

### **3. Datos inválidos (sin @Valid):**
```bash
POST /api/auth/registro
{
  "nombre": "",
  "email": "email-invalido",
  "password": "123"
}
```

**Respuesta (400 Bad Request):**
```json
{
  "timestamp": "2024-01-15 10:30:45",
  "status": 400,
  "error": "Error de validación",
  "message": "Datos de entrada inválidos",
  "details": {
    "nombre": "El nombre es obligatorio",
    "email": "El email debe tener un formato válido",
    "password": "La contraseña debe tener al menos 8 caracteres"
  }
}
```

## 🔄 **Flujo de manejo de errores:**

```
1. Usuario hace request → AuthRestController
2. @Valid valida datos → Si falla: MethodArgumentNotValidException
3. AuthService procesa → Si falla: UserAlreadyExistsException, BadCredentialsException
4. GlobalExceptionHandler captura → Convierte a ErrorResponse
5. Cliente recibe respuesta JSON consistente
```

## 🎯 **Próximos pasos sugeridos:**

### **1. Agregar validaciones a DTOs:**
```java
public class RegistroDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
    
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
```

### **2. Usar excepciones en otros servicios:**
```java
// En CarreraService
if (!carreraRepository.existsById(id)) {
    throw ResourceNotFoundException.forCarrera(id);
}

// En AlumnoService
if (alumnoRepository.findByDni(dni).isPresent()) {
    throw UserAlreadyExistsException.forDni(dni);
}
```

### **3. Configurar logging en application.properties:**
```properties
# Configuración de logging
logging.level.Grupo_5.UTNC=INFO
logging.level.org.springframework.security=DEBUG
logging.pattern.console=%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
```

## ✨ **Resultado:**
- ✅ **Manejo centralizado** de todos los errores
- ✅ **Respuestas JSON consistentes** para el frontend
- ✅ **Logging completo** para debugging
- ✅ **Experiencia de usuario mejorada** con mensajes claros
- ✅ **Código más limpio** sin try-catch repetidos
- ✅ **Cumple estándares** de Spring Boot profesional

¡Tu aplicación ahora tiene un manejo de errores de nivel empresarial! 🚀