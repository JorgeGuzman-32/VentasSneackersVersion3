# VentasSneackers - Version 2

## Contexto
Sistema de venta de sneakers implementado con arquitectura de microservicios, separacion por capas CSR (Controller-Service-Repository/Model), documentacion OpenAPI y enrutamiento centralizado con API Gateway.

## Integrantes
- Estudiante 1: Francisco Ordenes
- Estudiante 2: Matias Aguilera
- Estudiante 3: Jorge Guzman

## Microservicios implementados
- eureka-server (descubrimiento de servicios)
- gateway (enrutamiento centralizado)
- venta-sneackers (servicio de dominio)

## Rutas principales del Gateway
- GET /api/** -> venta-sneackers
- GET /doc/** -> swagger-ui del servicio
- GET /v3/api-docs/** -> OpenAPI JSON del servicio

## Swagger / OpenAPI
- Servicio principal: http://localhost:8181/doc/swagger-ui.html
- Via Gateway: http://localhost:8081/doc/swagger-ui.html

## Pruebas unitarias
- Ubicacion: src/test/java
- Frameworks: JUnit 5 + Mockito
- Comando: ./mvnw clean verify
- Cobertura: configurada en pom_v2.xml con JaCoCo (minimo 80 por ciento de lineas)

## Ejecucion local
1. Levantar MySQL local o contenedor MySQL.
2. Ejecutar eureka-server.
3. Ejecutar venta-sneackers.
4. Ejecutar gateway.

## Ejecucion con Docker Compose (v2)
1. Ubicarse en carpeta venta-sneackers.
2. Ejecutar: docker compose -f docker-compose_v2.yml up --build
3. Verificar:
   - Eureka: http://localhost:8761
   - App: http://localhost:8181
   - Gateway: http://localhost:8081

## Variables de entorno recomendadas
- EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
- DB_URL
- DB_USER
- DB_PASSWORD

## Evidencia sugerida para defensa
- Captura de tests exitosos.
- Captura de cobertura JaCoCo.
- Swagger funcional en app y gateway.
- Servicios levantados en Docker.
