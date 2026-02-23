# Stage 1: Build
FROM node:20-alpine AS builder

WORKDIR /build

# Copiar package files
COPY package*.json ./

# Instalar dependencias
RUN npm ci

# Copiar código fuente
COPY . .

# Build la aplicación
RUN npm run build

# Stage 2: Runtime
FROM node:20-alpine

WORKDIR /app

# Instalar servidor HTTP simple
RUN npm install -g serve

# Copiar archivos construidos desde builder
COPY --from=builder /build/dist ./dist

# Exponer puerto
EXPOSE 5173

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:5173/ || exit 1

# Ejecutar aplicación con serve
CMD ["serve", "-s", "dist", "-l", "5173"]
