package com.panini.supporttickets.data.mock

import com.panini.supporttickets.data.repository.TicketRepository
import com.panini.supporttickets.domain.model.Ticket
import com.panini.supporttickets.domain.model.TicketCategory
import com.panini.supporttickets.domain.model.TicketPriority
import com.panini.supporttickets.domain.model.TicketStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class MockTicketRepository : TicketRepository {
    private val _tickets = MutableStateFlow(seedTickets().sortedByPriority())
    override val tickets: StateFlow<List<Ticket>> = _tickets

    override fun observeTicket(ticketId: String): Flow<Ticket?> =
        tickets.map { list -> list.firstOrNull { it.id == ticketId } }

    override suspend fun createTicket(
        title: String,
        description: String,
        supplier: String,
        category: TicketCategory,
        priority: TicketPriority,
        distributionRegion: String
    ): Ticket {
        val ticket = Ticket(
            id = "TCK-${System.currentTimeMillis()}",
            title = title,
            description = description,
            priority = priority,
            status = TicketStatus.OPEN,
            supplier = supplier,
            createdAt = LocalDate.now().toString(),
            category = category,
            distributionRegion = distributionRegion
        )
        _tickets.update { current -> (current + ticket).sortedByPriority() }
        return ticket
    }

    override suspend fun updateStatus(ticketId: String, status: TicketStatus) {
        _tickets.update { current ->
            current.map { ticket ->
                if (ticket.id == ticketId) ticket.copy(status = status) else ticket
            }.sortedByPriority()
        }
    }

    override suspend fun updatePriority(ticketId: String, priority: TicketPriority) {
        _tickets.update { current ->
            current.map { ticket ->
                if (ticket.id == ticketId) ticket.copy(priority = priority) else ticket
            }.sortedByPriority()
        }
    }

    private fun List<Ticket>.sortedByPriority(): List<Ticket> =
        sortedWith(compareByDescending<Ticket> { it.priority.rank }.thenByDescending { it.createdAt })

    private fun seedTickets(): List<Ticket> =
        listOf(
            Ticket(
                id = "TCK-1001",
                title = "Faltan sobres de estampas en envío mayorista",
                description = "Un envío mayorista para el lanzamiento en Norteamérica llegó con menos sobres de estampas que los reportados en el manifiesto del proveedor.",
                priority = TicketPriority.CRITICAL,
                status = TicketStatus.OPEN,
                supplier = "Suministros Coleccionables Globales",
                createdAt = "2026-05-18",
                category = TicketCategory.INVENTORY,
                distributionRegion = "Norteamérica"
            ),
            Ticket(
                id = "TCK-1002",
                title = "Entrega retrasada a tiendas minoristas",
                description = "Las tiendas minoristas del corredor Pacífico reportaron ventanas de entrega incumplidas para los kits iniciales del álbum FIFA World Cup 2026.",
                priority = TicketPriority.HIGH,
                status = TicketStatus.IN_PROGRESS,
                supplier = "Logística Ruta Rápida",
                createdAt = "2026-05-19",
                category = TicketCategory.LOGISTICS,
                distributionRegion = "Corredor Pacífico"
            ),
            Ticket(
                id = "TCK-1003",
                title = "Cajas de álbum duplicadas en lote de proveedor",
                description = "Un lote del proveedor incluyó cajas de álbum duplicadas y redujo el espacio disponible en cajas para paquetes de sobres de estampas.",
                priority = TicketPriority.MEDIUM,
                status = TicketStatus.BLOCKED,
                supplier = "Socios de Impresión de Álbumes",
                createdAt = "2026-05-20",
                category = TicketCategory.SUPPLIER,
                distributionRegion = "Bodega central"
            ),
            Ticket(
                id = "TCK-1004",
                title = "Paquete dañado recibido en punto de distribución",
                description = "El punto de distribución regional recibió embalaje exterior dañado que afecta los exhibidores de álbumes.",
                priority = TicketPriority.HIGH,
                status = TicketStatus.OPEN,
                supplier = "Operaciones Empaque Seguro",
                createdAt = "2026-05-21",
                category = TicketCategory.DISTRIBUTION,
                distributionRegion = "Sureste"
            ),
            Ticket(
                id = "TCK-1005",
                title = "Diferencia de inventario en bodega regional",
                description = "El conteo de bodega para cajas premium de estampas no coincide con la captura de inventario del panel de soporte.",
                priority = TicketPriority.MEDIUM,
                status = TicketStatus.IN_PROGRESS,
                supplier = "Centro Regional de Despacho",
                createdAt = "2026-05-22",
                category = TicketCategory.INVENTORY,
                distributionRegion = "Medio Oeste"
            ),
            Ticket(
                id = "TCK-1006",
                title = "Ruta de entrega de proveedor incorrecta",
                description = "Una ruta de entrega fue asignada al punto de cruce equivocado, retrasando el reabastecimiento para la semana de lanzamiento del álbum.",
                priority = TicketPriority.LOW,
                status = TicketStatus.RESOLVED,
                supplier = "Distribución Enlace Ruta",
                createdAt = "2026-05-23",
                category = TicketCategory.SUPPORT_INCIDENT,
                distributionRegion = "Noreste"
            )
        )
}
