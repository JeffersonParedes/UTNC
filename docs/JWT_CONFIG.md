# Configuración de Variables de Entorno para JWT

## ✅ Cambios Realizados

Se ha externalizado la configuración del JWT desde el código fuente hacia el archivo `application.properties` para mejorar la seguridad del sistema.

### Archivos Modificados

1. **`application.properties`** - Se agregaron las propiedades:
   - `jwt.secret` - Secret key para firmar los tokens JWT
   - `jwt.expiration.seconds` - Tiempo de expiración en segundos (por defecto 24 horas)

2. **`JWTAuthenticationConfig.java`** - Se modificó para inyectar las propiedades usando `@Value`

3. **`.env.example`** - Nuevo archivo de ejemplo para variables de entorno

## 🔐 Configuración en Desarrollo

Por defecto, la aplicación usará valores seguros:
- **Secret:** Un valor por defecto largo (debe cambiarse en producción)
- **Expiración:** 86400 segundos (24 horas)

## 🚀 Configuración en Producción

### Opción 1: Variables de Entorno (Recomendado)

Configura estas variables de entorno antes de ejecutar la aplicación:

```bash
# Windows PowerShell
$env:JWT_SECRET="tu-secret-muy-seguro-generado-con-openssl"
$env:JWT_EXPIRATION="86400"

# Linux/Mac
export JWT_SECRET="tu-secret-muy-seguro-generado-con-openssl"
export JWT_EXPIRATION="86400"
```

### Opción 2: Archivo application.properties

Edita directamente el archivo `src/main/resources/application.properties` y reemplaza los valores por defecto.

## 🔑 Generar un Secret Seguro

Usa uno de estos métodos para generar un secret aleatorio y seguro:

```bash
# Opción 1: OpenSSL (64 caracteres base64)
openssl rand -base64 48

# Opción 2: PowerShell (Windows)
[Convert]::ToBase64String((1..48 | ForEach-Object { Get-Random -Maximum 256 }))

# Opción 3: Python
python -c "import secrets; print(secrets.token_urlsafe(48))"
```

## ⏰ Valores de Expiración Comunes

- `3600` = 1 hora
- `86400` = 24 horas (predeterminado)
- `604800` = 7 días
- `2592000` = 30 días

## ⚠️ Importante

- **NUNCA** subas el archivo `.env` con secrets reales a un repositorio Git
- El archivo `.env.example` es solo un ejemplo y debe ser copiado a `.env` con valores reales
- Cambia el secret por defecto antes de desplegar en producción
