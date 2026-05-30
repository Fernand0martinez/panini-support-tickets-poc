package com.panini.supporttickets.domain.model

enum class TicketCategory(val label: String) {
    SUPPLIER("Proveedor"),
    LOGISTICS("Logística"),
    DISTRIBUTION("Distribución"),
    INVENTORY("Inventario"),
    SUPPORT_INCIDENT("Incidente de soporte")
}
