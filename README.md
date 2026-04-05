# Alumnos API Core (Spring Boot Backend)

Este es el servicio central (Backend) para el **Sistema de Gestión de Alumnos**. Una API REST robusta diseñada bajo una arquitectura de capas, preparada para servir datos de forma consistente a múltiples clientes (Web, Mobile y Desktop).

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.x
* **Persistencia:** Spring Data JPA (Hibernate)
* **Base de Datos:** MySQL
* **Entorno de Desarrollo:** **Ubuntu Desktop (OS Principal)** 🐧

---

## 🏗️ Arquitectura de Persistencia

El corazón de este proyecto es la gestión eficiente de datos. Se utilizó **Spring Data JPA** para abstraer la complejidad del SQL y garantizar la escalabilidad:

* **JpaRepository:** Implementación de interfaces para CRUD automático y consultas personalizadas (Query Methods).
* **Seguridad:** Protección nativa contra SQL Injection y manejo de transacciones.
* **Relaciones:** Mapeo de entidades `@Entity` con relaciones `@OneToMany` y `@ManyToOne`.

--- 
