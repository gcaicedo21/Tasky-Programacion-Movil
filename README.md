# TASKY - Programación móvil

Aplicación Android desarrollada en **Kotlin + Jetpack Compose** para registrar, consultar, modificar, completar y eliminar tareas.

## Funcionalidades

- Pantalla inicial de bienvenida.
- Registro de tareas con:
  - título,
  - descripción,
  - fecha límite,
  - prioridad baja, media o alta.
- Listado de tareas.
- Filtros: todas, pendientes y completadas.
- Búsqueda por título o descripción.
- Vista de detalle.
- Edición de tareas.
- Marcar una tarea como completada o pendiente.
- Eliminación con confirmación.
- Calendario/listado organizado por fecha.
- Estadísticas de tareas.
- Pantalla de perfil/información del proyecto.
- Persistencia local: las tareas se conservan al cerrar y volver a abrir la aplicación.

## Arquitectura

El proyecto está organizado por responsabilidades:

```text
com.gcaicedo.tasky
├── data
│   ├── model
│   │   ├── Priority.kt
│   │   └── Task.kt
│   └── repository
│       ├── TaskRepository.kt
│       └── LocalTaskRepository.kt
├── ui
│   ├── components
│   ├── screens
│   ├── theme
│   ├── MainSection.kt
│   ├── TaskViewModel.kt
│   └── TaskyApp.kt
├── util
│   └── DateUtils.kt
└── MainActivity.kt
```

Se utiliza una separación sencilla tipo **MVVM**:

- **Model:** representa la información de una tarea.
- **Repository:** guarda y recupera las tareas del dispositivo.
- **ViewModel:** contiene la lógica de la aplicación.
- **UI:** contiene las pantallas y componentes visuales de Jetpack Compose.

## Persistencia

Para este alcance académico se utiliza `SharedPreferences` con JSON para guardar las tareas localmente. No requiere conexión a Internet ni servicios externos.

## Requisitos

- Android Studio.
- JDK 17.
- Android SDK 36.
- Min SDK 26.

## Cómo ejecutar

1. Abrir la carpeta raíz del proyecto en Android Studio.
2. Esperar a que termine **Gradle Sync**.
3. Seleccionar un emulador o dispositivo Android.
4. Seleccionar la configuración `app`.
5. Pulsar **Run ▶**.

> Si se copia este código sobre un proyecto creado previamente en Android Studio, se debe conservar el archivo `gradle/wrapper/gradle-wrapper.jar` que genera Android Studio.

## Pruebas recomendadas

1. Crear una tarea de prioridad alta.
2. Crear otra tarea de prioridad baja.
3. Comprobar los filtros Pendientes y Completadas.
4. Marcar una tarea como completada.
5. Editar título, descripción, fecha y prioridad.
6. Cerrar y volver a abrir la app para verificar la persistencia.
7. Eliminar una tarea y confirmar que desaparece.
8. Revisar Calendario y Estadísticas.

## Autor

Proyecto académico de Programación Móvil.
