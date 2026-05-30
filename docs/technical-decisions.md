# Justificación técnica y decisiones arquitectónicas

## Propósito de la solución

Esta prueba de concepto implementa una aplicación Android para la gestión interna de tickets de soporte de Panini. El dominio del sistema se centra en incidentes relacionados con proveedores, inventario, distribución, logística y soporte operativo para el álbum FIFA World Cup 2026.

El objetivo técnico no es únicamente mostrar pantallas funcionales, sino validar una base de arquitectura que pueda crecer hacia una solución conectada a backend, con reglas de negocio más complejas, control de capacidades por rol y actualización reactiva de datos.

## Justificación técnica

La solución fue diseñada para separar responsabilidades desde el inicio. La interfaz no administra directamente la fuente de datos ni conoce los detalles de persistencia. En su lugar, las pantallas observan estado producido por ViewModels, y los ViewModels coordinan las operaciones contra un repositorio.

Esta separación permite que el repositorio simulado usado en el POC pueda reemplazarse más adelante por un repositorio remoto sin rediseñar las pantallas. También facilita pruebas unitarias sobre lógica de presentación y evita que la UI quede acoplada a datos mock, llamadas HTTP o reglas de ordenamiento.

## Decisiones arquitectónicas

- Se utiliza MVVM para separar UI, estado de pantalla y acceso a datos.
- Se utiliza Jetpack Compose porque permite construir interfaces declarativas que reaccionan naturalmente a cambios de estado.
- Se utiliza StateFlow como mecanismo reactivo para propagar cambios desde el repositorio hacia la UI.
- Se define un contrato de repositorio para aislar la fuente de datos actual.
- Se usa un repositorio simulado durante el POC para validar el flujo funcional sin depender de un backend.
- Se preparan DTOs, Retrofit y contrato OpenAPI para facilitar una futura integración remota.
- Se implementan Feature Flags locales para demostrar control dinámico de funcionalidades.

## Escalabilidad de la solución

La arquitectura permite evolucionar por capas. Primero se puede sustituir el repositorio simulado por una implementación remota. Luego se pueden agregar persistencia local, paginación real, autenticación, autorización por roles y sincronización con eventos del backend.

El patrón actual evita que esas mejoras obliguen a reescribir toda la aplicación. La UI seguiría observando estado, mientras el repositorio sería responsable de decidir si los datos vienen de memoria, base de datos local, API REST o una combinación de fuentes.

## Criterio de diseño

Se priorizó una arquitectura simple, explícita y defendible para una prueba de concepto. No se agregaron frameworks innecesarios de inyección de dependencias, base de datos local ni configuración remota porque todavía no son requerimientos funcionales del MVP.

La solución mantiene un equilibrio entre simplicidad y posibilidad de crecimiento: demuestra el flujo completo, pero deja puntos claros para evolucionar hacia una implementación productiva.
