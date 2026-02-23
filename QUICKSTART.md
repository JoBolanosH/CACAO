# 🚀 Quick Start Guide

La forma más rápida de empezar a usar CACAO.

## Opción 1: Con Docker (Recomendado)

### Requisitos
- Docker 20.10+
- Docker Compose 2.0+

### Pasos

```bash
# 1. Clonar el repositorio
git clone <repository-url>
cd CACAO

# 2. Iniciar los servicios
docker-compose up --build

# ⏳ Esperar 30-60 segundos mientras se construyen los contenedores...

# 3. Acceder a la aplicación
# Frontend: http://localhost:5173
# Backend API: http://localhost:8080
```

### Credenciales
```
Usuario: admin
Contraseña: admin123
```

---

## Opción 2: Desarrollo Local

### Requisitos
- Java 21 JDK
- Node.js 18+
- Maven 3.8+

### Backend

**Terminal 1:**
```bash
cd backend/auth-service
mvn clean install
mvn spring-boot:run
```

Esperar hasta ver:
```
Started AuthServiceApplication in X seconds
```

### Frontend

**Terminal 2:**
```bash
cd frontend
npm install
npm run dev
```

Abrir navegador: `http://localhost:5173`

---

## 📊 Verificar que Todo Funciona

```bash
# Health Check
curl http://localhost:8080/api/auth/health

# Expected Response:
# {"status":"UP","service":"auth-service","version":"1.0.0"}
```

---

## 🔑 Probar Login

### Opción A: Via Frontend
1. Ir a `http://localhost:5173`
2. Ingresa `admin` / `admin123`
3. Verás el token generado

### Opción B: Via CURL
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

---

## 🛑 Detener Servicios

### Docker
```bash
docker-compose down
```

### Local
- Presiona `Ctrl+C` en ambas terminales

---

## 📚 Documentación Adicional

- **Develop Locally**: Ver `DEVELOPMENT.md`
- **Architecture**: Ver `ARCHITECTURE.md`
- **Main README**: Ver `README.md`

---

## ❓ Problemas Comunes

| Problema | Solución |
|----------|----------|
| Puerto 8080 en uso | `lsof -i :8080` y matar el proceso |
| Port 5173 en uso | Cambiar en `vite.config.ts` o matar proceso |
| Contenedores no inician | `docker-compose down && docker-compose up --build` |
| npm no encuentra dependencias | `cd frontend && rm -rf node_modules && npm install` |

---

**¡Listo!** 🎉 Ya tienes CACAO ejecutándose.
