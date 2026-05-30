# POC de tickets de soporte Panini

Prueba de concepto Android para la gestión interna de tickets de soporte de Panini relacionados con proveedores, distribución, inventario, logística e incidentes de soporte para el álbum FIFA World Cup 2026.

## Tecnologías

- Kotlin
- Android
- Jetpack Compose
- Material 3
- MVVM
- Navigation Compose
- StateFlow
- Preparación para Retrofit
- Integración con repositorio simulado
- Banderas de funcionalidad locales

## Estructura del repositorio

```text
panini-support-tickets-poc/
|-- app/
|   |-- build.gradle.kts
|   `-- src/main/java/com/panini/supporttickets/
|       |-- core/
|       |-- data/
|       |   |-- mock/
|       |   |-- remote/
|       |   `-- repository/
|       |-- domain/
|       |   |-- mapper/
|       |   `-- model/
|       |-- flags/
|       |-- network/
|       `-- ui/
|           |-- components/
|           |-- login/
|           |-- navigation/
|           |-- theme/
|           `-- tickets/
|-- contracts/
|   `-- tickets-api.yaml
|-- docs/
|   |-- event-driven-strategy.md
|   |-- feature-flags.md
|   |-- system-flow.md
|   `-- technical-decisions.md
|-- video/
|   `-- demo-link.md
|-- build.gradle.kts
|-- gradle.properties
|-- settings.gradle.kts
`-- README.md
```

## Cómo ejecutar la aplicación Android

1. Abre el repositorio en Android Studio.
2. Deja que Android Studio sincronice el proyecto Gradle.
3. Selecciona la configuración de ejecución `app`.
4. Ejecuta la aplicación en un emulador Android o en un dispositivo físico.

Si Gradle está instalado localmente, el proyecto también se puede compilar desde la raíz del repositorio:

```bash
gradle :app:assembleDebug
```

## Funcionalidades MVP

- Inicio de sesión simulado.
- Lista de tickets con datos realistas de operaciones de soporte a proveedores de Panini.
- Vista de detalle del ticket.
- Actualización del estado del ticket.
- Actualización de prioridad controlada por bandera de funcionalidad.
- Creación de tickets controlada por bandera de funcionalidad.
- Filtrado por categoría disponible en la lista de tickets.
- Actualizaciones reactivas de la lista mediante StateFlow.
- Manejo de estados de carga, error y éxito.

## Resumen de arquitectura

La aplicación usa una estructura MVVM simple:

- `ui`: pantallas Compose, navegación, componentes compartidos, tema y ViewModels.
- `domain`: modelos de tickets, enums y helpers de mapeo.
- `data`: contrato del repositorio, implementación simulada, marcador de posición remoto y DTOs.
- `network`: proveedor Retrofit e interfaz del servicio API.
- `flags`: banderas de funcionalidad locales para control de funcionalidades a nivel de prueba de concepto.
- `core`: contenedor compartido de la aplicación y modelo de estado de UI.

La capa visual se llama `ui`. No existe un paquete `presentation`.

## Notas de integración simulada

La fuente de datos activa es `MockTicketRepository`. Almacena tickets en un `MutableStateFlow` y los expone como `StateFlow<List<Ticket>>`.

Cuando se crea un ticket o se actualiza la prioridad, el repositorio emite una nueva lista ordenada. Las pantallas Compose recolectan ese estado y se recomponen automáticamente.

## Notas para integración futura con backend

`RetrofitProvider`, `TicketApiService`, los DTOs y el contrato OpenAPI están incluidos para preparar la integración con servidor. Más adelante, el repositorio simulado puede reemplazarse por una implementación remota sin cambiar el flujo principal de pantallas.
