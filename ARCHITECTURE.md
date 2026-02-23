# Arquitectura del Microservicio

## 📐 Visión General

CACAO es un microservicio monolítico de autenticación construido con Spring Boot que se comunica con un frontend de React/TypeScript a través de APIs REST.

## 🔄 Flujo de Autenticación

```
┌─────────────┐
│   Frontend  │
│  (React)    │
└──────┬──────┘
       │
       │ POST /api/auth/login
       │ {username, password}
       │
       ▼
┌──────────────────────────────────┐
│   Auth Controller                │
│  - ValidateRequest               │
│  - CallAuthService               │
└──────────┬───────────────────────┘
           │
           │ delegateAuthentication()
           │
           ▼
┌──────────────────────────────────┐
│   Auth Service                   │
│  - SearchUser                    │
│  - VerifyPassword                │
│  - GenerateJWT                   │
└──────────┬───────────────────────┘
           │
           │ findByUsername()
           │
           ▼
┌──────────────────────────────────┐
│   User Repository (JPA)          │
│  - Query Database                │
└──────────┬───────────────────────┘
           │
           │ User Entity
           │
           ▼
┌──────────────────────────────────┐
│   H2 Database                    │
│  ├── users table                 │
│  └── (future: tokens, roles)     │
└──────────────────────────────────┘
```

## 📦 Estructura de Paquetes

### `com.cacao.auth.config`
Configuración de la aplicación:
- `SecurityConfig` - Configuración de Spring Security, CORS, etc.
- `DataInitializer` - Inicialización de datos de prueba

### `com.cacao.auth.controller`
REST Controllers:
- `AuthController` - Endpoints de autenticación
  - `POST /api/auth/login` - Autenticar usuario
  - `POST /api/auth/validate` - Validar token
  - `POST /api/auth/logout` - Cerrar sesión
  - `GET /api/auth/health` - Health check

### `com.cacao.auth.service`
Lógica de negocio:
- `AuthService` - Servicio de autenticación
  - `authenticate()` - Verificar credenciales
  - `validateToken()` - Validar JWT
  - `logout()` - Invalidar sesión

### `com.cacao.auth.repository`
Acceso a datos (JPA):
- `UserRepository` - CRUD de usuarios

### `com.cacao.auth.model`
Entidades:
- `User` - Entidad de usuario persistida

### `com.cacao.auth.dto`
Transfer Objects:
- `LoginRequest` - Datos de entrada (username, password)
- `LoginResponse` - Datos de salida (token, username, roles)

### `com.cacao.auth.exception`
Excepciones personalizadas:
- `AuthenticationException` - Error de autenticación
- `UserNotFoundException` - Usuario no encontrado

### `com.cacao.auth.util`
Utilidades:
- `JwtUtil` - Generación y validación de JWT
- `Base64Util` - Codificación/decodificación Base64

## 🗄️ Modelo de Datos

### User Entity

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Hasheada con BCrypt

    @Column(nullable = false)
    private String email;

    @Column(name = "is_active")
    private Boolean isActive;
}
```

**Tabla SQL:**
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);
```

## 🔐 Flujo de Seguridad

### 1. Password Hashing
- Algoritmo: BCrypt
- Salt: Automático (10 rounds)
- Implementación: `passwordEncoder.encode(password)`

### 2. Token Generation
- Tipo: JWT (Mock - Base64 en desarrollo)
- Payload: `{username, iat, exp}`
- Expiración: 24 horas

### 3. CORS Configuration
- Origins permitidos: `*` (desarrollo)
- Methods: GET, POST, PUT, DELETE, OPTIONS
- Headers: `*`
- Credentials: No

## 📊 Diagrama de Capas

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (REST Controllers, DTOs, Validation)   │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Service Layer                   │
│  (Business Logic, Authentication)       │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Data Access Layer               │
│  (JPA Repositories, Database)           │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         Database                        │
│  (H2 - Development, PostgreSQL - Prod)  │
└─────────────────────────────────────────┘
```

## 🔌 API Contracts

### Login Endpoint

**Request:**
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNzA5Nzg5NTEwLCJleHAiOjE3MDk4NzU5MDB9",
  "tokenType": "Bearer",
  "username": "admin",
  "roles": ["ROLE_USER"]
}
```

**Response (401 Unauthorized):**
```json
{
  "error": "Usuario o contraseña inválidos"
}
```

## 🚀 Deployment Consideration

Para producción:
1. Cambiar base de datos a PostgreSQL
2. Implementar JWT real con librería `jjwt`
3. Agregar Redis para token blacklist
4. Configurar HTTPS/TLS
5. Implementar rate limiting
6. Agregar logging centralizado
7. Configurar secrets management

## 📚 Patrones de Diseño Utilizados

- **Repository Pattern** - Abstracción de acceso a datos
- **Service Layer Pattern** - Separación de lógica de negocio
- **DTO Pattern** - Transferencia segura de datos
- **Exception Handling** - Manejo centralizado de errores
- **Dependency Injection** - Inyección de dependencias con Spring

---

**Última actualización**: Febrero 2026
