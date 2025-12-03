# ESPECIFICACIÓN TÉCNICA: Módulo de Recomendaciones para Entrenadores

## REQUERIMIENTO FUNCIONAL

**Los entrenadores deben poder generar recomendaciones según el avance del usuario. Este módulo permitirá a los entrenadores analizar el progreso de sus estudiantes y crear recomendaciones personalizadas basadas en métricas de rendimiento, frecuencia de entrenamiento, nivel de esfuerzo percibido (RPE), y otros indicadores de progreso.**

---

## CONTEXTO DEL SISTEMA ACTUAL

### Arquitectura Backend (Spring Boot)
- **Framework**: Spring Boot con JPA/Hibernate
- **Seguridad**: JWT con filtros de validación de token
- **Roles**: Usuario, Entrenador, Administrador
- **Base de datos**: H2 (desarrollo) con entidades JPA

### Entidades Existentes Relevantes:

1. **Recommendation**: Entidad para almacenar recomendaciones de entrenadores
   - Campos: `recommendationId`, `content` (texto de la recomendación, max 1000 caracteres), `commentDate` (LocalDate), `generalProgress` (GeneralProgress), `trainer` (User)
   - Tabla: `recommendations`
   - Relaciones:
     - `@ManyToOne` con `GeneralProgress` (nullable = true)
     - `@ManyToOne` con `User` (trainer, nullable = true)

2. **GeneralProgress**: Progreso general del usuario
   - Campos: `progressId`, `type` (String, max 30), `percentage` (BigDecimal, precision 6 scale 3), `days_or_weeks` (String, max 10)
   - Tabla: `general_progress`
   - Relaciones:
     - `@OneToMany` con `Recommendation` (lista de recomendaciones)
     - `@OneToMany` con `CompleteExercise` (ejercicios completados)

3. **ExerciseProgress** (a implementar en módulo anterior): Registros de progreso diario/semanal
   - Campos: `progressId`, `user`, `workoutProgram`, `exercise`, `recordDate`, `periodType` (DAILY/WEEKLY), `repetitions`, `timeMinutes`, `distanceKm`, `rpe`, `notes`, `estimatedCaloriesBurnt`
   - Representa el progreso detallado del usuario en ejercicios específicos

4. **TrainerTrainee**: Relación entre entrenador y estudiante
   - Campos: `id`, `trainer` (User), `trainee` (User), `startDate`, `endDate`
   - Permite identificar qué estudiantes están asignados a cada entrenador

5. **WorkoutProgram**: Programas de entrenamiento
   - Campos: `workoutId`, `name`, `description`, `photoUrl`, `creationDate`, `completed`, `user` (creator)
   - Representa las rutinas de entrenamiento del estudiante

6. **HistoricalRecord**: Registros históricos genéricos
   - Campos: `recordId`, `user`, `event`, `workoutProgram`, `details`, `estimatedBurntCalories`
   - Puede contener información adicional sobre el progreso

### Endpoints Backend Existentes (En fase de desarrollo, permisos ajustables):

#### Recommendation (Recomendaciones)
- **GET** `/api/v1/recommendations` - Obtener todas las recomendaciones
- **GET** `/api/v1/recommendations/{id}` - Obtener recomendación por ID
- **POST** `/api/v1/recommendations` - Crear recomendación
- **PUT** `/api/v1/recommendations/{id}` - Actualizar recomendación
- **DELETE** `/api/v1/recommendations/{id}` - Eliminar recomendación

**Nota**: Estos endpoints actualmente tienen `@PreAuthorize("hasAuthority('Administrador')")` por estar en fase de desarrollo. En producción se ajustarán los permisos para permitir acceso a entrenadores. El frontend puede usar estos endpoints directamente, filtrando en el cliente o esperando que el backend filtre por el entrenador autenticado.

**Estructura de RecommendationRequest actual**:
```java
{
  "content": "string" // Solo el contenido de texto
}
```

**Estructura de RecommendationResponse**:
```java
{
  "recommendationId": Integer,
  "generalProgress": GeneralProgressResponse,
  "trainer": UserResponse,
  "content": String,
  "commentDate": LocalDate (YYYY-MM-DD)
}
```

**Limitación actual**: El `RecommendationRequest` solo incluye `content`, pero la entidad `Recommendation` requiere `generalProgress` y `trainer`. Esto sugiere que el backend necesita ser actualizado para:
1. Incluir `generalProgressId` y `trainerEmail` en el `RecommendationRequest`
2. O extraer el `trainer` del token JWT y permitir crear recomendaciones sin `GeneralProgress` (para recomendaciones directas al estudiante)

#### GeneralProgress (Progreso General)
- **GET** `/api/v1/general_progress` - Obtener todos los registros de progreso general
- **GET** `/api/v1/general_progress/{id}` - Obtener progreso general por ID

**Nota**: Endpoints disponibles para consultar el progreso general asociado a recomendaciones.

#### ExerciseProgress (Progreso de Ejercicios - a implementar en módulo anterior)
- **GET** `/api/v1/exercise_progress/my` - Obtener mi progreso (Usuario autenticado)
- **GET** `/api/v1/exercise_progress/my?workoutId={id}&startDate={date}&endDate={date}` - Con filtros
- **POST** `/api/v1/exercise_progress` - Crear registro (Usuario autenticado)
- **PUT** `/api/v1/exercise_progress/{id}` - Actualizar registro (Usuario autenticado)
- **DELETE** `/api/v1/exercise_progress/{id}` - Eliminar registro (Usuario autenticado)

**Nota**: Estos endpoints están diseñados para usuarios viendo su propio progreso. Para entrenadores, se puede usar el endpoint `/my` con el email del estudiante si el backend lo permite, o filtrar en el frontend después de obtener todos los registros (si el endpoint retorna todos los registros del sistema en desarrollo).

#### TrainerTrainee (Relaciones Entrenador-Estudiante)
- **GET** `/api/v1/trainers_trainees` - Obtener todas las relaciones
- **POST** `/api/v1/trainers_trainees` - Crear relación

**Nota**: Para identificar estudiantes asignados al entrenador autenticado.

#### WorkoutProgram (Programas de Entrenamiento)
- **GET** `/api/v1/workout_programs` - Obtener todos los programas
- **GET** `/api/v1/workout_programs/{id}` - Obtener programa por ID

**Nota**: Para obtener las rutinas del estudiante y analizar su progreso.

---

## ARQUITECTURA FRONTEND

### Stack Tecnológico
- **React 18+** con TypeScript
- **Redux Toolkit** para gestión de estado
- **React Router v6** para routing
- **shadcn/ui** para componentes UI
- **Tailwind CSS** para estilos
- **Lucide React** para iconos
- **date-fns** para manejo de fechas
- **Recharts** o **Chart.js** para visualización de métricas (opcional, para análisis visual)

### Estructura de Carpetas Propuesta

```
src/
├── pages/
│   └── trainer/
│       ├── Students.tsx (ya existe, modificar)
│       ├── StudentProgress.tsx (NUEVO - Dashboard de progreso del estudiante)
│       └── Recommendations.tsx (NUEVO - Vista de recomendaciones)
├── components/
│   └── trainer/
│       ├── RecommendationDialog.tsx (NUEVO - Diálogo para crear/editar recomendación)
│       ├── RecommendationCard.tsx (NUEVO - Card de recomendación)
│       ├── RecommendationList.tsx (NUEVO - Lista de recomendaciones)
│       ├── ProgressAnalysis.tsx (NUEVO - Análisis visual del progreso)
│       ├── RecommendationForm.tsx (NUEVO - Formulario de recomendación)
│       └── ProgressMetrics.tsx (NUEVO - Métricas resumidas para análisis)
├── store/
│   └── recommendations/
│       ├── recommendationsSlice.ts (NUEVO - Estado de recomendaciones)
│       └── recommendationsThunk.ts (NUEVO - Thunks para API calls)
└── services/
    └── http.ts (ya existe, usar apiFetch)
```

---

## MAPEO DE SERVICIOS FRONTEND CON ENDPOINTS BACKEND

### Store Redux - Recommendations Slice

#### Thunk: `fetchStudentRecommendations`
- **Endpoint Backend**: `GET /api/v1/recommendations`
- **Descripción**: Obtener todas las recomendaciones y filtrar por estudiante en el frontend
- **Payload**: `{ studentEmail: string }`
- **Response Type**: `RecommendationResponse[]` - Filtrar donde `generalProgress` está asociado al estudiante, o donde `trainer.email === userEmail` y la recomendación está relacionada con el estudiante
- **Lógica Frontend**: 
  - Obtener todas las recomendaciones del endpoint
  - Filtrar por recomendaciones donde el `trainer.email === userEmail` (entrenador autenticado)
  - Si `generalProgress` está disponible, verificar que corresponde al estudiante
  - Alternativamente, si el backend permite filtrar por estudiante, usar query params
- **Estado Redux**: Almacenar en `recommendations.studentRecommendations: Record<string, Recommendation[]>` (keyed por email de estudiante)

#### Thunk: `createRecommendation`
- **Endpoint Backend**: `POST /api/v1/recommendations`
- **Descripción**: Crear una nueva recomendación para un estudiante
- **Payload**: `{ studentEmail: string, content: string, generalProgressId?: number }`
- **Request Body**: 
  ```typescript
  {
    content: string,
    generalProgressId?: number, // Opcional, si se asocia a un GeneralProgress específico
    // El backend debería extraer trainer.email del token JWT
  }
  ```
- **Response Type**: `MsgResp` con mensaje de éxito
- **Lógica Frontend**: 
  - Validar que el estudiante está asignado al entrenador (verificar en `TrainerTrainee`)
  - Construir el payload con `content` y opcionalmente `generalProgressId`
  - El backend debería extraer el `trainer` del token JWT automáticamente
  - Si el backend no soporta esto, incluir `trainerEmail` en el payload (requiere ajuste backend)
- **Estado Redux**: Después de crear, refrescar `fetchStudentRecommendations`

#### Thunk: `updateRecommendation`
- **Endpoint Backend**: `PUT /api/v1/recommendations/{id}`
- **Descripción**: Actualizar una recomendación existente
- **Payload**: `{ recommendationId: number, content: string }`
- **Request Body**: `{ content: string }`
- **Response Type**: `MsgResp` con mensaje de éxito
- **Lógica Frontend**: 
  - Validar que la recomendación pertenece al entrenador autenticado
  - Actualizar solo el contenido
- **Estado Redux**: Actualizar en `recommendations.studentRecommendations` después de actualizar

#### Thunk: `deleteRecommendation`
- **Endpoint Backend**: `DELETE /api/v1/recommendations/{id}`
- **Descripción**: Eliminar una recomendación
- **Payload**: `{ recommendationId: number }`
- **Response Type**: `MsgResp` con mensaje de éxito
- **Lógica Frontend**: 
  - Validar que la recomendación pertenece al entrenador autenticado
  - Eliminar de la lista después de confirmación
- **Estado Redux**: Remover de `recommendations.studentRecommendations` después de eliminar

#### Thunk: `fetchStudentProgressForAnalysis` (Opcional - para análisis avanzado)
- **Endpoint Backend**: `GET /api/v1/exercise_progress/my` (o endpoint que retorne todos)
- **Descripción**: Obtener progreso detallado del estudiante para análisis y generación de recomendaciones
- **Payload**: `{ studentEmail: string, filters?: ProgressFilters }`
- **Response Type**: `ExerciseProgress[]` - Filtrar donde `user.email === studentEmail`
- **Lógica Frontend**: 
  - Obtener registros de progreso del estudiante
  - Calcular métricas: promedio de RPE, total de calorías, frecuencia de entrenamiento, tendencias
  - Usar estas métricas para sugerir recomendaciones inteligentes
- **Estado Redux**: Almacenar en `recommendations.analysisData: Record<string, AnalysisData>`

### Interfaces TypeScript Propuestas

```typescript
export interface Recommendation {
  recommendationId: number;
  content: string;
  commentDate: string; // YYYY-MM-DD
  generalProgress?: {
    progressId: number;
    type: string;
    percentage: number;
    days_or_weeks: string;
  };
  trainer: {
    email: string;
    name?: string;
  };
}

export interface RecommendationRequest {
  content: string;
  generalProgressId?: number;
  // trainerEmail se extrae del token JWT en el backend
}

export interface AnalysisData {
  avgRpe: number;
  totalCalories: number;
  trainingFrequency: number; // días/semanas con actividad
  trend: 'improving' | 'stable' | 'declining';
  lastActivityDate: string;
  mostUsedWorkout?: string;
  recommendations: string[]; // Sugerencias automáticas basadas en métricas
}
```

---

## DISEÑO DE UI Y COMPONENTES

### 1. Vista Principal: Recommendations.tsx
- **Ruta**: `/app/trainer/students/:studentEmail/recommendations`
- **Descripción**: Página principal para gestionar recomendaciones de un estudiante específico
- **Componentes principales**:
  - Header con nombre del estudiante y botón "Nueva Recomendación"
  - Sección de análisis de progreso (métricas resumidas)
  - Lista de recomendaciones existentes
  - Filtros por fecha, tipo de progreso asociado

### 2. RecommendationDialog.tsx
- **Props**: `{ open: boolean, onClose: () => void, studentEmail: string, recommendation?: Recommendation }`
- **Descripción**: Diálogo modal para crear o editar recomendaciones
- **Campos del formulario**:
  - Textarea para `content` (máximo 1000 caracteres, con contador)
  - Select opcional para asociar a `GeneralProgress` específico
  - Preview del progreso del estudiante (opcional, para contexto)
- **Validaciones**:
  - `content` es requerido y no puede estar vacío
  - `content` no puede exceder 1000 caracteres
- **Acciones**: Crear/Actualizar, Cancelar

### 3. RecommendationCard.tsx
- **Props**: `{ recommendation: Recommendation, onEdit: () => void, onDelete: () => void }`
- **Descripción**: Card individual para mostrar una recomendación
- **Información mostrada**:
  - Contenido de la recomendación (texto completo, con scroll si es largo)
  - Fecha de creación (`commentDate`)
  - Información del progreso asociado (si existe)
  - Botones de acción: Editar, Eliminar
- **Estilo**: Card con sombra, hover effect, colores suaves

### 4. RecommendationList.tsx
- **Props**: `{ recommendations: Recommendation[], studentEmail: string, onEdit: (rec: Recommendation) => void, onDelete: (id: number) => void }`
- **Descripción**: Lista de recomendaciones con ordenamiento y filtros
- **Funcionalidades**:
  - Ordenar por fecha (más reciente primero, más antiguo primero)
  - Filtrar por rango de fechas
  - Búsqueda por contenido
  - Estado vacío cuando no hay recomendaciones
- **Layout**: Grid o lista vertical con cards

### 5. ProgressAnalysis.tsx (Opcional - Mejora futura)
- **Props**: `{ studentEmail: string, progressData: ExerciseProgress[] }`
- **Descripción**: Análisis visual del progreso del estudiante para ayudar al entrenador a generar recomendaciones informadas
- **Visualizaciones**:
  - Gráfico de línea: Evolución de RPE a lo largo del tiempo
  - Gráfico de barras: Calorías quemadas por semana
  - Indicadores: Frecuencia de entrenamiento, tendencias
  - Alertas: Patrones que requieren atención (ej: RPE muy bajo consistentemente, falta de actividad)

### 6. ProgressMetrics.tsx
- **Props**: `{ studentEmail: string }`
- **Descripción**: Métricas resumidas del progreso del estudiante para contexto rápido
- **Métricas mostradas**:
  - RPE promedio (últimos 7/30 días)
  - Total de calorías (último mes)
  - Días activos (último mes)
  - Rutina más usada
  - Última actividad
- **Layout**: Grid de cards pequeños con iconos

### 7. Integración en StudentProgress.tsx
- **Modificación**: Agregar pestaña o sección "Recomendaciones" en el dashboard de progreso del estudiante
- **Navegación**: Botón "Ver Recomendaciones" que lleva a `/app/trainer/students/:studentEmail/recommendations`
- **Vista rápida**: Mostrar las 3 recomendaciones más recientes en el dashboard principal

---

## FLUJOS DE USUARIO

### Flujo 1: Crear Recomendación desde Dashboard de Progreso
1. Entrenador navega a `/app/trainer/students/:studentEmail/progress`
2. Entrenador revisa el progreso del estudiante (métricas, gráficos, tabla de registros)
3. Entrenador identifica un patrón o área de mejora
4. Entrenador hace clic en "Crear Recomendación" o en la pestaña "Recomendaciones"
5. Se abre `RecommendationDialog` con contexto del progreso del estudiante
6. Entrenador escribe la recomendación en el textarea
7. (Opcional) Entrenador asocia la recomendación a un `GeneralProgress` específico
8. Entrenador hace clic en "Crear"
9. La recomendación se guarda y aparece en la lista
10. El estudiante puede ver la recomendación (en su vista de usuario, si está implementada)

### Flujo 2: Gestionar Recomendaciones Existentes
1. Entrenador navega a `/app/trainer/students/:studentEmail/recommendations`
2. Entrenador ve la lista de recomendaciones ordenadas por fecha
3. Entrenador puede:
   - **Editar**: Hacer clic en "Editar" en una card, se abre el diálogo con el contenido prellenado
   - **Eliminar**: Hacer clic en "Eliminar", se muestra confirmación, luego se elimina
   - **Filtrar**: Usar filtros de fecha para encontrar recomendaciones específicas
4. Después de editar/eliminar, la lista se actualiza automáticamente

### Flujo 3: Análisis Inteligente (Opcional - Mejora futura)
1. Entrenador navega a la vista de recomendaciones
2. El sistema muestra `ProgressAnalysis` con métricas calculadas
3. El sistema sugiere recomendaciones automáticas basadas en patrones:
   - "RPE promedio bajo: Considera aumentar la intensidad"
   - "Falta de actividad reciente: Motiva al estudiante a retomar"
   - "Excelente progreso: Felicita y sugiere nuevos objetivos"
4. Entrenador puede usar estas sugerencias como base para crear recomendaciones personalizadas

---

## CONSIDERACIONES TÉCNICAS

### Seguridad
- **Filtrado en Frontend**: Como los endpoints retornan todos los datos en desarrollo, el frontend debe validar que:
  - Solo se muestren recomendaciones donde `trainer.email === userEmail` (entrenador autenticado)
  - Solo se puedan crear recomendaciones para estudiantes asignados al entrenador (verificar en `TrainerTrainee`)
  - Solo se puedan editar/eliminar recomendaciones propias del entrenador
- **Validación de Relaciones**: Antes de crear una recomendación, verificar que existe relación `TrainerTrainee` donde `trainer.email === userEmail` y `trainee.email === studentEmail`
- **En Producción**: Los permisos de los endpoints se ajustarán para que el backend filtre automáticamente, pero por ahora el filtrado es responsabilidad del frontend

### Performance
- Implementar cache en Redux para evitar requests repetidos de recomendaciones
- Lazy loading de recomendaciones (paginación si hay muchas)
- Debounce en búsqueda y filtros

### Validaciones Frontend
- `content`: Requerido, mínimo 10 caracteres, máximo 1000 caracteres
- Validar que el estudiante está asignado al entrenador antes de crear recomendación
- Mostrar mensajes de error claros si falla la creación/actualización

### Mejoras Futuras (Opcional)
1. **Recomendaciones Automáticas**: Usar IA/ML para generar sugerencias basadas en patrones de progreso
2. **Plantillas de Recomendaciones**: Permitir guardar plantillas reutilizables
3. **Notificaciones**: Notificar al estudiante cuando se crea una nueva recomendación
4. **Historial de Recomendaciones**: Ver evolución de recomendaciones a lo largo del tiempo
5. **Categorización**: Agregar categorías a las recomendaciones (Nutrición, Técnica, Intensidad, etc.)

---

## PLAN DE IMPLEMENTACIÓN

### Fase 1: Configuración y Estructura Base
1. Crear estructura de carpetas: `src/store/recommendations/`, `src/components/trainer/` (si no existe)
2. Crear `recommendationsSlice.ts` con estado inicial
3. Crear `recommendationsThunk.ts` con thunks básicos (fetch, create, update, delete)
4. Integrar el slice en `src/store/index.ts`

### Fase 2: Componentes UI Base
1. Crear `RecommendationDialog.tsx` (formulario de creación/edición)
2. Crear `RecommendationCard.tsx` (card individual)
3. Crear `RecommendationList.tsx` (lista con filtros)
4. Crear `ProgressMetrics.tsx` (métricas resumidas)

### Fase 3: Página Principal
1. Crear `Recommendations.tsx` en `src/pages/trainer/`
2. Integrar todos los componentes
3. Agregar navegación y routing en `App.tsx`
4. Conectar con Redux store

### Fase 4: Integración con Dashboard de Progreso
1. Modificar `StudentProgress.tsx` (si existe) para agregar sección de recomendaciones
2. Agregar botón "Crear Recomendación" en el dashboard
3. Mostrar recomendaciones recientes en vista rápida

### Fase 5: Validaciones y Manejo de Errores
1. Agregar validaciones en formularios
2. Manejar errores de API (404, 400, 401, 500)
3. Mostrar mensajes de éxito/error con toasts
4. Validar permisos y relaciones antes de crear

### Fase 6: Mejoras y Optimizaciones
1. Agregar paginación si es necesario
2. Implementar cache en Redux
3. Optimizar renders con React.memo donde sea apropiado
4. Agregar loading states y skeletons

### Fase 7: Testing y Refinamiento
1. Probar todos los flujos de usuario
2. Verificar que las validaciones funcionan correctamente
3. Asegurar que el filtrado por entrenador funciona
4. Optimizaciones de performance
5. Testing manual completo

---

## NOTAS FINALES

- **Mapeo de URLs**: Todos los servicios del frontend deben usar exactamente las URLs de endpoints existentes especificadas en la sección "MAPEO DE SERVICIOS FRONTEND CON ENDPOINTS BACKEND"
- **Autenticación**: El token JWT se envía automáticamente por `apiFetch`, el backend extrae el email del token
- **Filtrado en Desarrollo**: Como los endpoints están en fase de desarrollo y retornan todos los datos, el frontend debe implementar la lógica de filtrado:
  - Filtrar recomendaciones por `trainer.email === userEmail`
  - Validar que el estudiante está asignado al entrenador antes de crear recomendaciones
- **Ajustes Backend Necesarios**: El `RecommendationRequest` actual solo incluye `content`, pero para crear recomendaciones funcionales, el backend debería:
  - Extraer el `trainer` del token JWT automáticamente
  - Permitir incluir `generalProgressId` opcional en el request
  - O permitir crear recomendaciones sin `GeneralProgress` (para recomendaciones directas al estudiante)
- **Validación de Relaciones**: El frontend debe validar que un estudiante está asignado al entrenador antes de mostrar o crear recomendaciones
- **Consistencia**: Mantener los mismos patrones de código que en el resto de la aplicación (naming conventions, estructura de carpetas, etc.)
- **Extensibilidad**: El diseño debe permitir agregar funcionalidades avanzadas (análisis automático, plantillas, categorías) en el futuro sin refactorizar completamente

Este módulo debe ser una herramienta clave que los entrenadores usen para guiar y motivar a sus estudiantes, proporcionando feedback personalizado basado en datos reales de progreso. La usabilidad y la claridad de la información son críticas para que las recomendaciones sean efectivas.

