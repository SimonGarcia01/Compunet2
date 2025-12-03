# ANÁLISIS COMPLETO DE ENDPOINTS BACKEND

## RESUMEN EJECUTIVO

**Fecha de análisis**: 2025-11-17  
**Total de Controladores REST**: 21  
**Endpoints implementados**: ~85 endpoints  
**Endpoints faltantes críticos**: 1 (`/api/v1/exercise_progress/*`)

---

## ENDPOINTS IMPLEMENTADOS POR MÓDULO

### 1. **Auth** (`/api/v1/auth`)
- ✅ `POST /api/v1/auth/login` - Iniciar sesión
- ✅ `POST /api/v1/auth/register` - Registro de usuario
- **Permisos**: Público

### 2. **Users** (`/api/v1/users`)
- ✅ `GET /api/v1/users/{id}` - Obtener usuario por ID
- ✅ `GET /api/v1/users` - Obtener todos los usuarios
- ✅ `POST /api/v1/users` - Crear usuario
- ✅ `PUT /api/v1/users/{id}` - Actualizar usuario
- ✅ `DELETE /api/v1/users/{id}` - Eliminar usuario
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`

### 3. **Exercises** (`/api/v1/exercises`)
- ✅ `GET /api/v1/exercises/{id}` - Obtener ejercicio por ID
- ✅ `GET /api/v1/exercises` - Obtener todos los ejercicios
- ✅ `POST /api/v1/exercises` - Crear ejercicio
- ✅ `PUT /api/v1/exercises/{id}` - Actualizar ejercicio
- ✅ `DELETE /api/v1/exercises/{id}` - Eliminar ejercicio
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`

### 4. **WorkoutPrograms** (`/api/v1/workout_programs`)
- ✅ `GET /api/v1/workout_programs/{id}` - Obtener programa por ID
- ✅ `GET /api/v1/workout_programs` - Obtener todos los programas
- ✅ `POST /api/v1/workout_programs` - Crear programa
- ✅ `PUT /api/v1/workout_programs/{id}` - Actualizar programa
- ✅ `DELETE /api/v1/workout_programs/{id}` - Eliminar programa
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`
- **Nota**: El frontend filtra por `user.email` en el cliente

### 5. **WorkoutExercises** (`/api/v1/workout_exercises`)
- ✅ `GET /api/v1/workout_exercises/{workoutId}/{exerciseId}` - Obtener ejercicio de rutina
- ✅ `GET /api/v1/workout_exercises` - Obtener todos los ejercicios de rutinas
- ✅ `POST /api/v1/workout_exercises` - Crear ejercicio en rutina
- ✅ `PUT /api/v1/workout_exercises/{workoutId}/{exerciseId}` - Actualizar ejercicio en rutina
- ✅ `DELETE /api/v1/workout_exercises/{workoutId}/{exerciseId}` - Eliminar ejercicio de rutina
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`

### 6. **TrainerTrainee** (`/api/v1/trainers_trainees`)
- ✅ `GET /api/v1/trainers_trainees/{id}` - Obtener relación por ID
- ✅ `GET /api/v1/trainers_trainees` - Obtener todas las relaciones
- ✅ `POST /api/v1/trainers_trainees` - Crear relación entrenador-estudiante
- ✅ `PUT /api/v1/trainers_trainees/{id}` - Actualizar relación
- ✅ `DELETE /api/v1/trainers_trainees/{id}` - Eliminar relación
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`
- **Nota**: El frontend filtra por `trainer.email` en el cliente

### 7. **Recommendations** (`/api/v1/recommendations`)
- ✅ `GET /api/v1/recommendations/{id}` - Obtener recomendación por ID
- ✅ `GET /api/v1/recommendations` - Obtener todas las recomendaciones
- ✅ `POST /api/v1/recommendations` - Crear recomendación
- ✅ `PUT /api/v1/recommendations/{id}` - Actualizar recomendación
- ✅ `DELETE /api/v1/recommendations/{id}` - Eliminar recomendación
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`
- **Nota**: El frontend filtra por `trainer.email` en el cliente

### 8. **GeneralProgress** (`/api/v1/general_progress`)
- ✅ `GET /api/v1/general_progress/{id}` - Obtener progreso general por ID
- ✅ `GET /api/v1/general_progress` - Obtener todos los registros de progreso general
- ✅ `POST /api/v1/general_progress` - Crear registro de progreso general
- ✅ `PUT /api/v1/general_progress/{id}` - Actualizar progreso general
- ✅ `DELETE /api/v1/general_progress/{id}` - Eliminar progreso general
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`

### 9. **HistoricalRecords** (`/api/v1/historical_records`)
- ✅ `GET /api/v1/historical_records/{id}` - Obtener registro histórico por ID
- ✅ `GET /api/v1/historical_records` - Obtener todos los registros históricos
- ✅ `POST /api/v1/historical_records` - Crear registro histórico
- ✅ `PUT /api/v1/historical_records/{id}` - Actualizar registro histórico
- ✅ `DELETE /api/v1/historical_records/{id}` - Eliminar registro histórico
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`

### 10. **Events** (`/api/v1/events`)
- ✅ `GET /api/v1/events/{id}` - Obtener evento por ID
- ✅ `GET /api/v1/events` - Obtener todos los eventos
- ✅ `POST /api/v1/events` - Crear evento
- ✅ `PUT /api/v1/events/{id}` - Actualizar evento
- ✅ `DELETE /api/v1/events/{id}` - Eliminar evento
- **Permisos**: `@PreAuthorize("hasAuthority('Administrador')")`

### 11. **Otros módulos** (Roles, Privileges, Notifications, Messages, etc.)
- Todos tienen CRUD completo con permisos de Administrador

---

## ENDPOINTS FALTANTES (CRÍTICOS)

### ❌ **ExerciseProgress** (`/api/v1/exercise_progress/*`)
**Estado**: NO EXISTE

**Endpoints que el frontend intenta usar**:
- ❌ `GET /api/v1/exercise_progress/my` - Obtener mi progreso
- ❌ `GET /api/v1/exercise_progress/my?workoutId={id}&startDate={date}&endDate={date}` - Con filtros
- ❌ `POST /api/v1/exercise_progress` - Crear registro de progreso
- ❌ `PUT /api/v1/exercise_progress/{id}` - Actualizar registro
- ❌ `DELETE /api/v1/exercise_progress/{id}` - Eliminar registro

**Impacto**:
- El módulo de progreso de estudiantes no puede mostrar datos reales
- El análisis de progreso para recomendaciones no funciona
- El frontend retorna arrays vacíos como fallback

**Solución temporal**: El frontend maneja 404 y redirecciones (3xx) retornando arrays vacíos.

**Solución definitiva**: Implementar el módulo `ExerciseProgress` en el backend según la especificación `ESPECIFICACION_REGISTRO_PROGRESO.md`.

---

## ANÁLISIS DE PERMISOS

### Patrón Actual
**Todos los endpoints** (excepto Auth) tienen:
```java
@PreAuthorize("hasAuthority('Administrador')")
```

### Implicaciones
1. **En desarrollo**: Solo usuarios con rol "Administrador" pueden acceder
2. **Filtrado en frontend**: El frontend debe filtrar datos por usuario autenticado
3. **En producción**: Los permisos deberán ajustarse para permitir:
   - Entrenadores: Ver/crear recomendaciones, ver progreso de sus estudiantes
   - Usuarios: Ver/crear su propio progreso, ver sus recomendaciones

---

## MAPEO FRONTEND → BACKEND

### ✅ Endpoints que funcionan correctamente:

| Frontend Thunk | Endpoint Backend | Estado | Filtrado |
|----------------|------------------|--------|----------|
| `fetchMyStudents` | `GET /api/v1/trainers_trainees` | ✅ | Frontend filtra por `trainer.email` |
| `fetchStudentWorkouts` | `GET /api/v1/workout_programs` | ✅ | Frontend filtra por `user.email` |
| `fetchStudentWorkoutDetails` | `GET /api/v1/workout_programs/{id}` | ✅ | Frontend valida pertenencia |
| `createTrainerTrainee` | `POST /api/v1/trainers_trainees` | ✅ | Backend extrae trainer del token |
| `fetchStudentRecommendations` | `GET /api/v1/recommendations` | ✅ | Frontend filtra por `trainer.email` |
| `createRecommendation` | `POST /api/v1/recommendations` | ✅ | Backend debería extraer trainer del token |
| `updateRecommendation` | `PUT /api/v1/recommendations/{id}` | ✅ | - |
| `deleteRecommendation` | `DELETE /api/v1/recommendations/{id}` | ✅ | - |

### ❌ Endpoints que NO existen:

| Frontend Thunk | Endpoint Backend | Estado | Solución Temporal |
|----------------|------------------|--------|-------------------|
| `fetchStudentProgress` | `GET /api/v1/exercise_progress/my` | ❌ | Retorna array vacío |
| `analyzeStudentProgress` | `GET /api/v1/exercise_progress/my` | ❌ | Retorna análisis vacío |

---

## PROBLEMAS IDENTIFICADOS

### 1. **Endpoint ExerciseProgress no existe**
- **Causa**: El módulo no ha sido implementado en el backend
- **Síntoma**: Redirección a `/auth/login` → Error CORS
- **Solución temporal**: Frontend detecta redirecciones (3xx) y retorna datos vacíos
- **Solución definitiva**: Implementar `ExerciseProgressRestController` según especificación

### 2. **Permisos muy restrictivos**
- **Causa**: Todos los endpoints requieren rol "Administrador"
- **Impacto**: En desarrollo funciona, pero en producción necesitará ajustes
- **Solución**: Ajustar `@PreAuthorize` cuando se pase a producción

### 3. **Falta de endpoints específicos por usuario**
- **Causa**: No hay endpoints como `/my` que filtren automáticamente
- **Impacto**: El frontend debe hacer filtrado manual
- **Solución**: Implementar endpoints específicos o ajustar permisos del backend

### 4. **RecommendationRequest incompleto** ✅ CORREGIDO
- **Causa**: Solo incluía `content`, pero la entidad necesita `trainer` y `generalProgress`
- **Solución aplicada**: 
  - ✅ Agregado `generalProgressId` opcional a `RecommendationRequest`
  - ✅ Agregado getter/setter para `trainer` en entidad `Recommendation`
  - ✅ Actualizado `RecommendationServiceImpl` para extraer `trainer` del token JWT usando `SecurityContextHolder`
  - ✅ El backend ahora busca el `User` existente por email y lo asigna como `trainer`
  - ✅ Si se proporciona `generalProgressId`, busca y asigna el `GeneralProgress` existente

---

## RECOMENDACIONES

### Prioridad Alta
1. **Implementar ExerciseProgress**:
   - Crear entidad `ExerciseProgress`
   - Crear `ExerciseProgressRestController`
   - Crear `ExerciseProgressService` y `ExerciseProgressServiceImpl`
   - Crear DTOs (`ExerciseProgressRequest`, `ExerciseProgressResponse`)
   - Crear Mapper

### Prioridad Media
2. **Ajustar RecommendationRequest**:
   - El backend debe extraer `trainer` del token JWT en `createRecommendation`
   - Permitir `generalProgressId` opcional en el request

3. **Ajustar permisos para producción**:
   - Entrenadores: Ver/crear recomendaciones, ver progreso de estudiantes
   - Usuarios: Ver/crear su propio progreso

### Prioridad Baja
4. **Endpoints específicos por usuario**:
   - `GET /api/v1/exercise_progress/my` - Progreso del usuario autenticado
   - `GET /api/v1/recommendations/my` - Recomendaciones del usuario autenticado
   - `GET /api/v1/workout_programs/my` - Rutinas del usuario autenticado

---

## CONCLUSIÓN

El backend tiene **21 controladores REST** con **~85 endpoints** implementados. El único módulo crítico faltante es **ExerciseProgress**, que es necesario para:
- Mostrar progreso detallado de estudiantes
- Generar análisis para recomendaciones
- Registrar progreso diario/semanal

El frontend está preparado para manejar la ausencia de este endpoint retornando datos vacíos, pero para funcionalidad completa, debe implementarse en el backend.

