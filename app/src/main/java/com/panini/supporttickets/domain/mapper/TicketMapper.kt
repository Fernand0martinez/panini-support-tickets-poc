package com.panini.supporttickets.domain.mapper

import com.panini.supporttickets.data.remote.dto.TicketResponseDto
import com.panini.supporttickets.domain.model.Ticket
import com.panini.supporttickets.domain.model.TicketCategory
import com.panini.supporttickets.domain.model.TicketPriority
import com.panini.supporttickets.domain.model.TicketStatus

fun TicketResponseDto.toDomain(): Ticket =
    Ticket(
        id = id,
        title = title,
        description = description,
        priority = enumValueOf<TicketPriority>(priority),
        status = enumValueOf<TicketStatus>(status),
        supplier = supplier,
        createdAt = createdAt,
        category = enumValueOf<TicketCategory>(category),
        distributionRegion = distributionRegion
    )

fun Ticket.toResponseDto(): TicketResponseDto =
    TicketResponseDto(
        id = id,
        title = title,
        description = description,
        priority = priority.name,
        status = status.name,
        supplier = supplier,
        createdAt = createdAt,
        category = category.name,
        distributionRegion = distributionRegion
    )
