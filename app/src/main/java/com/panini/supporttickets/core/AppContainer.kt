package com.panini.supporttickets.core

import com.panini.supporttickets.data.mock.MockTicketRepository
import com.panini.supporttickets.data.repository.TicketRepository

object AppContainer {
    val ticketRepository: TicketRepository = MockTicketRepository()
}
