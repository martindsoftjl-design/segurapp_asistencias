# 📋 Sistema de Control de Asistencias

Proyecto desarrollado como parte del curso  
**Experiencias Formativas en Situaciones Reales de Trabajo IV – CIBERTEC**

---

## 📌 Descripción

El Sistema de Control de Asistencias es una aplicación Android desarrollada en Kotlin que permite registrar y gestionar la asistencia de empleados, incluyendo:

- Registro de entrada
- Registro de salida
- Control de tardanzas
- Registro de faltas
- Reporte diario de asistencias

El sistema valida automáticamente:
- Si ya se registró la entrada
- Si la salida ya fue registrada
- Si se intenta registrar salida sin entrada previa

---

## 🛠️ Tecnologías Utilizadas

- Kotlin
- Android Studio
- MVVM Architecture
- Room Database
- LiveData
- Navigation Component
- ViewBinding

---

## 🧱 Arquitectura

El proyecto está desarrollado bajo el patrón **MVVM**, organizado en:

```
data/
domain/
ui/
```

- **Data:** Entidades, DAO, base de datos y mappers
- **Domain:** Modelos y casos de uso
- **UI:** Fragments, ViewModels y navegación

---

## 📊 Funcionalidades Principales

- Registro de asistencia (Entrada / Salida)
- Detección automática de tardanza
- Cálculo de asistencias, tardanzas y faltas
- Reporte diario
- Validaciones de negocio

---

## 📷 Capturas del Sistema

*(Agregar aquí capturas de pantalla de la aplicación)*

---

## 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio
2. Abrir en Android Studio
3. Sincronizar Gradle
4. Ejecutar en emulador o dispositivo físico

---

## Autor

**Jorge Luis Martin Diaz**  
Estudiante de Desarrollo de Sistemas  
CIBERTEC

---

## 📌 Estado del Proyecto

Proyecto académico finalizado y funcional.
