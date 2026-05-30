# Feature Flags

## Propósito

Los Feature Flags permiten activar o desactivar funcionalidades sin eliminar código ni modificar la navegación principal. En esta prueba de concepto se usan para demostrar cómo una aplicación puede controlar capacidades de forma dinámica.

Esta estrategia es útil cuando una funcionalidad debe habilitarse gradualmente, probarse con usuarios específicos o depender de reglas de negocio como roles, permisos o configuración remota.

## Flags implementadas

La aplicación implementa dos banderas principales:

- `ticketCreationEnabled`: controla si el usuario puede crear nuevos tickets.
- `priorityUpdateEnabled`: controla si el usuario puede actualizar la prioridad de un ticket.

El filtro por categoría no se maneja como Feature Flag porque se considera una capacidad base del listado. No representa una funcionalidad experimental ni una regla de acceso.

## Implementación actual

Las banderas viven en un objeto local llamado `FeatureFlags`. Su estado se maneja con `mutableStateOf`, por lo que Compose puede reaccionar automáticamente cuando cambia el valor.

La pantalla de listado incluye switches para modificar las banderas en tiempo de ejecución. Esto permite probar la estrategia sin editar código, recompilar o reiniciar la aplicación.

Cuando `ticketCreationEnabled` está desactivada, la acción de crear ticket desaparece del listado y la pantalla de creación queda bloqueada si se accede a ella. Cuando `priorityUpdateEnabled` está desactivada, el detalle del ticket muestra la prioridad como información, pero no permite modificarla.

## Problema que resuelven

Los Feature Flags desacoplan el despliegue técnico de la disponibilidad funcional. El código puede existir en la aplicación, pero la funcionalidad puede estar bloqueada hasta que el negocio o el equipo técnico decidan habilitarla.

Esto reduce riesgo en despliegues, facilita pruebas controladas y permite responder rápido si una funcionalidad debe apagarse temporalmente.

## Evolución esperada

En una versión productiva, estas banderas podrían venir desde un backend, un servicio de configuración remota o un endpoint de capacidades asociado al usuario autenticado.

También podrían persistirse localmente con DataStore para mantener el estado entre sesiones. Otro paso natural sería conectar las banderas con roles, por ejemplo permitiendo que operadores creen tickets, pero que solo supervisores actualicen prioridades.
