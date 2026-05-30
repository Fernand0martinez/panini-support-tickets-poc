package com.panini.supporttickets.domain.model

enum class TicketPriority(val label: String, val rank: Int) {
    LOW("Baja", 1),
    MEDIUM("Media", 2),
    HIGH("Alta", 3),
    CRITICAL("Crítica", 4)
}
