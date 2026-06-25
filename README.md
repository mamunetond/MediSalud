# MediSalud - API de Gestión de Citas Médicas

## Descripción

MediSalud es una API REST desarrollada en Spring Boot para la gestión de citas médicas.

La solución permite administrar médicos, pacientes y citas médicas, aplicando reglas de negocio para garantizar la correcta programación y cancelación de citas.

---

# Funcionalidades

## Médicos

* Registrar médicos.
* Consultar médicos.
* Actualizar médicos.
* Eliminar médicos.

## Pacientes

* Registrar pacientes.
* Consultar pacientes.
* Actualizar pacientes.
* Eliminar pacientes.

## Citas

* Agendar citas médicas.
* Cancelar citas.
* Consultar todas las citas.
* Consultar citas por médico.
* Consultar citas por paciente.
* Consultar citas por estado.

## Penalizaciones

* Registrar penalizaciones por cancelaciones tardías.
* Restringir la creación de citas para pacientes con tres o más penalizaciones activas.

---

# Arquitectura

Se implementó una arquitectura en capas siguiendo las buenas prácticas de Spring Boot.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Base de Datos
```

## Justificación

### Controller

Responsable de exponer los endpoints REST y recibir las solicitudes HTTP.

### Service

Contiene toda la lógica de negocio y validaciones.

### Repository

Encargado del acceso a datos mediante Spring Data JPA.

### Entity

Representa las tablas de la base de datos.

### DTO

Permite desacoplar las peticiones y respuestas de las entidades.

---

# Tecnologías Utilizadas

* Java 17
* Spring Boot 3.5.x
* Spring Data JPA
* H2 Database
* Lombok
* Swagger / OpenAPI
* JUnit 5
* Mockito
* Maven

---

# Reglas de Negocio Implementadas

## RN-01

Las citas solo pueden programarse dentro del horario laboral permitido.

## RN-02

Un médico no puede tener dos citas en el mismo horario.

## RN-03

La fecha de nacimiento del paciente no puede ser futura.

## RN-04

Un paciente no puede tener una cita duplicada con el mismo médico en la misma fecha y hora.

## RN-05

Si una cita se cancela con menos de 24 horas de anticipación, se registra una penalización.

## RN-06

Un paciente con tres o más penalizaciones activas no puede agendar nuevas citas.

---

# Instalación y Ejecución

## Clonar repositorio

```bash
git clone https://github.com/mamunetond/MediSalud.git
```

## Ingresar al proyecto

```bash
cd MediSalud
```

## Compilar proyecto

```bash
mvn clean install
```

## Ejecutar aplicación

```bash
mvn spring-boot:run
```

---

# Swagger

Una vez iniciada la aplicación:

```text
http://localhost:8080/swagger-ui/index.html
```

Documentación OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

---

# Consola H2

```text
http://localhost:8080/h2-console
```

Configuración por defecto:

```text
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password:
```

---

# Endpoints

## Médicos

### Crear médico

**POST /api/medicos**

Ejemplo Request:

```json
{
  "nombreCompleto": "Juan Pérez",
  "especialidad": "Cardiología",
  "telefono": "3001234567",
  "email": "juan@medisalud.com"
}
```

---

## Pacientes

### Crear paciente

**POST /api/pacientes**

Ejemplo Request:

```json
{
  "nombreCompleto": "María Gómez",
  "documento": "12345678",
  "telefono": "3009876543",
  "email": "maria@gmail.com",
  "fechaNacimiento": "1995-05-10"
}
```

---

## Citas

### Crear cita

**POST /api/citas**

Ejemplo Request:

```json
{
  "pacienteId": "UUID-PACIENTE",
  "medicoId": "UUID-MEDICO",
  "fechaHora": "2026-07-01T10:00:00"
}
```

Ejemplo Response:

```json
{
  "id": 1,
  "fechaHora": "2026-07-01T10:00:00",
  "estado": "PROGRAMADA"
}
```

### Cancelar cita

**PATCH /api/citas/{id}/cancelar**

### Listar citas

**GET /api/citas**

### Listar citas por médico

**GET /api/citas/medico/{medicoId}**

### Listar citas por paciente

**GET /api/citas/paciente/{pacienteId}**

### Listar citas por estado

**GET /api/citas/estado/{estado}**

---

# Manejo de Excepciones

La aplicación implementa un `GlobalExceptionHandler` para manejar errores de forma consistente.

Ejemplo:

```json
{
  "timestamp": "2026-06-24T20:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Médico no encontrado"
}
```

---

# Pruebas

Para ejecutar los tests:

```bash
mvn test
```

Las pruebas cubren:

* Creación de citas.
* Validación de disponibilidad médica.
* Validación de penalizaciones.
* Cancelación de citas.
* Manejo de excepciones.
* Casos de error.

---

# Autor

Desarrollado por Mario Alejandro Muñetón Durango
Fecha: 24/06/2026
Correo: mmunetondurango@gmail.com
Celular: +57 3166399954
Cedula: 1001011737
