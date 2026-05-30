# Estrategia basada en eventos

## Enfoque general

La aplicación usa una estrategia reactiva basada en eventos internos. Cada operación que modifica tickets genera un nuevo estado en el repositorio. Ese estado se emite mediante StateFlow y las pantallas que lo observan se actualizan automáticamente.

En este POC los eventos no vienen de un broker externo ni de un backend en tiempo real. Se simulan localmente desde el repositorio, pero el modelo de comunicación es el mismo: una acción cambia el estado del dominio y ese cambio se propaga a los consumidores interesados.

## Eventos principales

Los eventos relevantes dentro de la aplicación son:

- Creación de un ticket.
- Cambio de estado de un ticket.
- Cambio de prioridad de un ticket.
- Cambio de filtro de categoría en el listado.

Cada evento produce una nueva representación del estado. La UI no necesita consultar manualmente si hubo cambios; solo observa el flujo expuesto por el ViewModel.

## Propagación de cambios

El flujo de propagación es:

```text
Acción del usuario
-> ViewModel
-> Repository
-> StateFlow emite nuevo estado
-> ViewModel expone estado de pantalla
-> Compose recompone la UI
```

Este modelo reduce la posibilidad de inconsistencias visuales. Si el repositorio cambia, todas las pantallas que dependen de ese estado reciben la actualización desde la misma fuente.

## Actualización del listado de tickets

El listado de tickets observa un StateFlow generado por el ViewModel. Ese ViewModel combina la lista de tickets del repositorio con la categoría seleccionada por el usuario.

Cuando se crea un ticket, se actualiza su estado o cambia su prioridad, el repositorio emite una nueva lista. El listado recibe esa emisión y se recompone automáticamente. Esto elimina la necesidad de llamadas explícitas de refresco después de cada operación.

## Cambio dinámico de prioridades

La prioridad forma parte del modelo de dominio del ticket. Cuando el usuario actualiza la prioridad desde el detalle, el repositorio reemplaza el ticket afectado y vuelve a ordenar la lista usando el ranking de prioridad.

Después de actualizar la lista, el repositorio emite el nuevo estado. Como consecuencia, un ticket que pasa a prioridad crítica puede subir automáticamente en el listado sin que la pantalla tenga lógica especial para reordenar datos.

## Valor técnico de la estrategia

La estrategia basada en eventos resuelve el problema de sincronizar pantallas con datos cambiantes. También prepara el proyecto para escenarios más grandes, como sincronización con backend, actualizaciones push, WebSockets o eventos de dominio emitidos desde servicios externos.

El beneficio principal es que la UI se mantiene declarativa: muestra el estado actual del sistema, no administra manualmente los pasos necesarios para llegar a ese estado.
