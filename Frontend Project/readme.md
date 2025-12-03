# Proyecto Final - Sistema de Gestión de Fitness
## Parte Frontend
## Jugo de Borojó Group

### 👥 Equipo de Desarrollo
* **Juan José Angarita Yela - A00380919**
* **Daniela Castaño Moreno - A00401805**
* **Simón García Zuluaga - A00371828**
.
---
## URLs Importantes por despliegue final:
El backend y front se encuentran en el computador 17 del 206M con IP: 192.168.131.37
El backend quedó configurado en: **http://192.168.131.37:8080/borojo-backend/**
El frontend quedó configurado en: **http://192.168.131.37:8080/borojo-front/**

---

## 📋 Descripción del Proyecto

Este proyecto es un **Sistema de Gestión de Fitness** desarrollado con Spring Boot que permite administrar usuarios, entrenamientos, eventos y seguimiento del progreso físico. El sistema implementa un modelo de roles y privilegios para gestionar diferentes tipos de usuarios (administradores, entrenadores y usuarios regulares).

### 🎯 Funcionalidades Principales
- **Visualización estética y ordenada del proyecto**: integración de backend con frontend de manera organizada y estética
- **Gestión de Usuarios**: Creación, actualización y administración de usuarios con sistema de roles
- **Sistema de Roles y Privilegios**: Control de acceso basado en roles (Usuario, Entrenador, Administrador)
- **Programas de Entrenamiento**: Creación y asignación de rutinas de ejercicio personalizadas
- **Seguimiento de Progreso**: Registro y monitoreo del progreso de los usuarios
- **Gestión de Eventos**: Organización de eventos deportivos y actividades grupales
- **Sistema de Mensajería**: Comunicación entre entrenadores y usuarios
- **Notificaciones**: Sistema de notificaciones para eventos y actividades
- **Recomendaciones**: Generación de recomendaciones personalizadas basadas en el progreso

---

## 🛠️ Tecnologías Utilizadas

- **Frontend**: React
- **JS Version**: 17

---

## 🏗️ Arquitectura del Sistema

### Estructura del Proyecto
```
src/
├── main/
│   ├── java/org/example/taller2springboot/
│   │   ├── entity/          # Entidades JPA (27 clases)
│   │   ├── repository/      # Repositorios Spring Data JPA
│   │   ├── service/         # Interfaces de servicios
│   │   │   └── impl/        # Implementaciones de servicios
│   │   └── Taller2SpringBootApplication.java
│   └── resources/
│       ├── application.properties
│       └── data.sql         # Datos de prueba
└── test/
    └── java/                # Tests unitarios e integración
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- Maven 3.6 o superior
- IDE recomendado: Visual Studio Code

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd frontend-proyecto-final-jugodeborojo
   ```

2. **Navegar al directorio del proyecto React**
   ```bash
   cd frontend-proyecto-final-jugodeborojo
   ```

3. **Compilar el proyecto**
   ```bash
   npm run dev
   ```
---


### 🌐 Acceso a la Aplicación

Una vez iniciada la aplicación:
- **Aplicación**: `http://localhost:8080`
- **Base de Datos H2 Console**: `http://localhost:8080/h2`

---

## 📞 Contacto

Para preguntas o soporte, contactar a cualquier miembro del equipo **Jugo de Borojó Group**.

---

## 📄 Licencia

Este proyecto es desarrollado como parte del curso de Computación en Internet II en la Universidad Icesi.

