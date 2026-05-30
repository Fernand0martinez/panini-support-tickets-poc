package com.panini.supporttickets.data.remote

import com.panini.supporttickets.data.repository.TicketRepository
import com.panini.supporttickets.domain.model.Ticket
import com.panini.supporttickets.domain.model.TicketCategory
import com.panini.supporttickets.domain.model.TicketPriority
import com.panini.supporttickets.domain.model.TicketStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RemoteTicketRepository : TicketRepository {
    override val tickets: StateFlow<List<Ticket>> = MutableStateFlow<List<Ticket>>(emptyList())

    override fun observeTicket(ticketId: String): Flow<Ticket?> =
        MutableStateFlow<Ticket?>(null)

    override suspend fun createTicket(
        title: String,
        description: String,
        supplier: String,
        category: TicketCategory,
        priority: TicketPriority,
        distributionRegion: String
    ): Ticket {
        error("Remote integration is not enabled for this PoC.")
    }

    override suspend fun updateStatus(ticketId: String, status: TicketStatus) {
        error("Remote integration is not enabled for this PoC.")
    }

    override suspend fun updatePriority(ticketId: String, priority: TicketPriority) {
        error("Remote integration is not enabled for this PoC.")
    }
}
