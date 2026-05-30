# Flujo general del sistema

## Descripción general

El sistema representa un flujo interno de soporte operativo para Panini. El usuario inicia sesión, revisa tickets existentes, filtra incidentes por categoría, consulta el detalle de un ticket y ejecuta acciones permitidas según el estado de los Feature Flags.

El flujo está diseñado para demostrar una aplicación reactiva: los cambios realizados por el usuario se reflejan en el listado sin recargar manualmente la pantalla.

## Actores

- Usuario de operaciones.
- Coordinador de soporte a proveedores.
- Coordinador de soporte de distribución.
- Supervisor operativo con capacidad para priorizar incidentes.

## Flujo principal

1. El usuario abre la aplicación.
2. La aplicación muestra una pantalla de inicio de sesión simulado.
3. Después del ingreso, el usuario llega al listado de tickets.
4. Desde el listado puede filtrar tickets por categoría.
5. El usuario puede activar o desactivar funcionalidades usando los switches de Feature Flags.
6. Si la creación de tickets está habilitada, el usuario puede abrir el formulario de creación.
7. Al crear un ticket, el repositorio emite una nueva lista y el listado se actualiza automáticamente.
8. El usuario puede abrir el detalle de un ticket.
9. Desde el detalle puede actualizar el estado del ticket.
10. Si la actualización de prioridad está habilitada, también puede cambiar la prioridad.
11. Al cambiar la prioridad, el repositorio reordena la lista y emite el nuevo estado.

## Flujos condicionados por Feature Flags

La creación de tickets depende de `ticketCreationEnabled`. Si la bandera está apagada, el sistema oculta la acción de creación y bloquea el formulario.

La actualización de prioridad depende de `priorityUpdateEnabled`. Si la bandera está apagada, el detalle mantiene visible la prioridad actual, pero no presenta controles para modificarla.

## Actualización del estado visual

Las pantallas no se actualizan por llamadas manuales de refresco. El repositorio mantiene el estado de tickets en StateFlow y los ViewModels exponen ese estado a Compose.

Cuando el estado cambia, Compose recompone las partes afectadas de la interfaz. Esta estrategia mantiene el flujo simple y escalable, porque nuevas pantallas pueden observar el mismo estado sin duplicar lógica de sincronización.

## Preparación para crecimiento

El flujo actual funciona con datos simulados, pero está estructurado para evolucionar hacia una arquitectura conectada a servicios reales. La navegación, los ViewModels y las pantallas no dependen de que los datos estén en memoria.

En una versión futura, el mismo flujo podría consumir una API REST paginada, aplicar permisos por usuario, persistir estado localmente y recibir actualizaciones desde el backend sin cambiar el comportamiento esperado por el usuario.
