package com.panini.supporttickets.data.repository

import com.panini.supporttickets.domain.model.Ticket
import com.panini.supporttickets.domain.model.TicketCategory
import com.panini.supporttickets.domain.model.TicketPriority
import com.panini.supporttickets.domain.model.TicketStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TicketRepository {
    val tickets: StateFlow<List<Ticket>>

    fun observeTicket(ticketId: String): Flow<Ticket?>

    suspend fun createTicket(
        title: String,
        description: String,
        supplier: String,
        category: TicketCategory,
        priority: TicketPriority,
        distributionRegion: String
    ): Ticket

    suspend fun updateStatus(ticketId: String, status: TicketStatus)

    suspend fun updatePriority(ticketId: String, priority: TicketPriority)
}
