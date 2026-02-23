# Guía de Desarrollo Local

Esta guía te ayudará a establecer y ejecutar el proyecto CACAO en tu máquina local.

## ✅ Requisitos Previos

### Backend
- Java Development Kit (JDK) 21 o superior
- Maven 3.8.1 o superior
- Git

### Frontend
- Node.js 18.0.0 o superior (incluye npm)
- Git

## 🔧 Configuración Inicial

### 1. Clonar el Repositorio

```bash
git clone <repository-url>
cd CACAO
```

### 2. Backend Setup

```bash
cd backend/auth-service

# Instalar dependencias y compilar
mvn clean install

# Ejecutar la aplicación (Puerto: 8080)
mvn spring-boot:run
```

**Verificar que funciona:**
```bash
curl http://localhost:8080/api/auth/health
# Debe retornar: {"status":"UP","service":"auth-service","version":"1.0.0"}
```

### 3. Frontend Setup

En otra terminal:

```bash
cd frontend

# Instalar dependencias
npm install

# Ejecutar servidor de desarrollo (Puerto: 5173)
npm run dev
```

**Acceder a:**
- Frontend: `http://localhost:5173`
- API: `http://localhost:8080`

## 🧪 Testing

### Test unitarios del Backend

```bash
cd backend/auth-service
mvn test
```

### Lint Frontend

```bash
cd frontend
npm run lint
npm run type-check
```

## 📊 Rutas Útiles

| Recurso | URL |
|---------|-----|
| Frontend App | http://localhost:5173 |
| Backend API | http://localhost:8080 |
| Health Check | http://localhost:8080/api/auth/health |
| H2 Console | http://localhost:8080/h2-console |
| Actuator Metrics | http://localhost:8080/actuator/metrics |

## 🔑 Credenciales de Prueba

```
Usuario: admin
Contraseña: admin123

Usuario: user
Contraseña: user123
```

## 📝 Ejemplos de Requests

### Login Request

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### Sample Response

```json
{
  "token": "eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNzA5Nzg5NTEwLCJleHAiOjE3MDk4NzU5MDB9",
  "tokenType": "Bearer",
  "username": "admin",
  "roles": ["ROLE_USER"]
}
```

## 🐛 Troubleshooting

### Puerto 8080 en uso

```bash
# Linux/Mac
lsof -i :8080
# Matar el proceso
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Puerto 5173 en uso

```bash
# Mismo proceso anterior pero con puerto 5173
```

### Maven no encuentra Java

```bash
# Asegurar que JAVA_HOME está configurado
echo $JAVA_HOME

# Si no está configurado:
export JAVA_HOME=/path/to/java21
```

### Node modules problemas

```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

## 📚 Variables de Entorno

Crear un archivo `.env` en la raíz del proyecto:

```env
# Frontend
VITE_API_URL=http://localhost:8080

# Backend (Opcional para desarrollo local)
SPRING_PROFILES_ACTIVE=dev
```

## 📈 Próximas Sesiones de Desarrollo

Cuando regreses:

1. Abrir dos terminales
2. Terminal 1:
   ```bash
   cd backend/auth-service
   mvn spring-boot:run
   ```
3. Terminal 2:
   ```bash
   cd frontend
   npm run dev
   ```
4. Acceder a http://localhost:5173

---

**Última actualización**: Febrero 2026
