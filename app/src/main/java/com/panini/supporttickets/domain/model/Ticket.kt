package com.panini.supporttickets.domain.model

data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val priority: TicketPriority,
    val status: TicketStatus,
    val supplier: String,
    val createdAt: String,
    val category: TicketCategory,
    val distributionRegion: String
)
