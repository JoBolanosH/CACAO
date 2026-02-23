# CACAO - Microservicios de Autenticación y Autorización

**CACAO** es una arquitectura de microservicios moderna basada en **Spring Boot** para el backend y **React + Vite + TypeScript** para el frontend. Diseñada para demostrar patrones profesionales de desarrollo en una aplicación de autenticación y autorización.

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────┐
│           CACAO Microservices Platform              │
├─────────────────────────────────────────────────────┤
│                                                     │
│  ┌──────────────────┐      ┌──────────────────┐   │
│  │    Frontend      │      │   Auth Service   │   │
│  │  React + Vite    │◄────►│   Spring Boot    │   │
│  │   TypeScript     │      │   Java 21        │   │
│  │    Port 5173     │      │   Port 8080      │   │
│  └──────────────────┘      └──────────────────┘   │
│                                  │                 │
│                            ┌─────▼──────┐         │
│                            │  H2 Memory  │         │
│                            │  Database   │         │
│                            └─────────────┘         │
│                                                     │
└─────────────────────────────────────────────────────┘
```

## 📋 Requisitos Previos

- **Docker** 20.10+ y **Docker Compose** 2.0+
- O para desarrollo local:
  - **Java** 21 JDK
  - **Node.js** 18+
  - **Maven** 3.8+

## 🚀 Inicio Rápido

### Con Docker Compose (Recomendado)

```bash
# Clonar el repositorio
git clone <repository-url>
cd CACAO

# Construir e iniciar los contenedores
docker-compose up --build

# Acceder a la aplicación
# Frontend: http://localhost:5173
# Backend API: http://localhost:8080
```

### Desarrollo Local

#### Backend (Auth Service)

```bash
cd backend/auth-service

# Instalar dependencias y compilar
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run

# La aplicación estará disponible en http://localhost:8080
```

#### Frontend

```bash
cd frontend

# Instalar dependencias
npm install

# Ejecutar servidor de desarrollo
npm run dev

# La aplicación estará disponible en http://localhost:5173
```

## 📚 Endpoints de la API

### Autenticación

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "token": "eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNzA5Nzg5NTEwLCJleHAiOjE3MDk4NzU5MDB9",
  "tokenType": "Bearer",
  "username": "admin",
  "roles": ["ROLE_USER"]
}
```

#### Validar Token
```http
POST /api/auth/validate
Authorization: Bearer <token>
```

**Respuesta:**
```json
{
  "valid": true
}
```

#### Logout
```http
POST /api/auth/logout
Authorization: Bearer <token>
```

#### Health Check
```http
GET /api/auth/health
```

**Respuesta:**
```json
{
  "status": "UP",
  "service": "auth-service",
  "version": "1.0.0"
}
```

## 👤 Credenciales de Prueba

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| admin   | admin123  | ROLE_USER |
| user    | user123   | ROLE_USER |

> 📌 Estas credenciales se generan automáticamente al iniciar la aplicación en la base de datos H2.

## 📂 Estructura del Proyecto

```
CACAO/
├── backend/
│   └── auth-service/
│       ├── src/
│       │   ├── main/java/com/cacao/auth/
│       │   │   ├── AuthServiceApplication.java
│       │   │   ├── config/           # Configuración Spring
│       │   │   ├── controller/       # REST Controllers
│       │   │   ├── dto/              # Data Transfer Objects
│       │   │   ├── exception/        # Exception Handlers
│       │   │   ├── model/            # Entidades JPA
│       │   │   ├── repository/       # Repository Pattern
│       │   │   ├── service/          # Business Logic
│       │   │   └── util/             # Utilidades (JWT, Base64, etc)
│       │   └── resources/
│       │       └── application.yml
│       ├── Dockerfile
│       └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── App.tsx           # Componente principal
│   │   ├── App.css
│   │   ├── main.tsx          # Entry point
│   │   ├── index.css
│   │   └── vite-env.d.ts
│   ├── public/
│   │   └── index.html
│   ├── Dockerfile
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   └── .env.example
│
├── docker-compose.yml
├── .env.example
└── README.md
```

## 🛠️ Tecnologías Utilizadas

### Backend
- **Spring Boot 3.4.2**
- **Java 21**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database** (desarrollo)
- **Maven**

### Frontend
- **React 18.2.0**
- **TypeScript 5.2.0**
- **Vite 5.0.0**
- **Axios**

## 🔧 Configuración

### Variables de Entorno

Crear un archivo `.env` en la raíz del proyecto basado en `.env.example`:

```bash
# Copiar archivo de ejemplo
cp .env.example .env

# Editar según necesidad
# Por defecto funciona con Docker
```

## 📊 Base de Datos

En desarrollo, se utiliza **H2 Database** (en memoria):

- **Console H2**: `http://localhost:8080/h2-console`
- **URL JDBC**: `jdbc:h2:mem:cacaodb`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

Para acceder a la consola, usar las credenciales anteriores.

## 🐳 Comandos Docker

```bash
# Iniciar servicios
docker-compose up

# Iniciar en background
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Reconstruir imágenes
docker-compose build --no-cache

# Ejecutar comando en un contenedor
docker-compose exec auth-service sh
```

## 📝 Scripts Disponibles

### Frontend

```bash
npm run dev        # Iniciar servidor de desarrollo
npm run build      # Compilar para producción
npm run preview    # Vista previa de build
npm run lint       # Ejecutar linter
npm run type-check # Verificar tipos TypeScript
```

### Backend

```bash
mvn clean install  # Instalar dependencias
mvn spring-boot:run # Ejecutar aplicación
mvn test          # Ejecutar tests
mvn compile       # Solo compilar
```

## 🔐 Seguridad

- ✅ CORS habilitado para desarrollo
- ✅ CSRF deshabilitado para API REST
- ✅ JWT Token-based authentication
- ✅ Password hashing con BCrypt
- ✅ Session stateless

## 📈 Próximos Pasos

- [ ] Implementar persistencia con base de datos PostgreSQL
- [ ] Agregar autenticación OAuth2/OIDC
- [ ] Implementar refresh tokens
- [ ] Agregar 2FA
- [ ] Agregar más microservicios
- [ ] Configurar API Gateway
- [ ] Implementar logging centralizado
- [ ] Agregar métricas y monitoreo

## 🤝 Contribución

Las contribuciones son bienvenidas. Por favor:

1. Hacer un fork del proyecto
2. Crear una rama para la feature (`git checkout -b feature/AmazingFeature`)
3. Commit los cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📧 Contacto

Para preguntas o soporte, contactar a través de los issues del repositorio.

---

**Última actualización**: Febrero 2026
**Versión**: 1.0.0