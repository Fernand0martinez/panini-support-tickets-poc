package com.panini.supporttickets.ui.navigation

sealed class AppRoute(val route: String) {
    data object Login : AppRoute("login")
    data object TicketList : AppRoute("tickets")
    data object CreateTicket : AppRoute("tickets/create")
    data object TicketDetail : AppRoute("tickets/{ticketId}") {
        fun create(ticketId: String): String = "tickets/$ticketId"
    }
}
