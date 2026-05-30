package com.panini.supporttickets.domain.model

enum class TicketStatus(val label: String) {
    OPEN("Abierto"),
    IN_PROGRESS("En progreso"),
    BLOCKED("Bloqueado"),
    RESOLVED("Resuelto")
}
