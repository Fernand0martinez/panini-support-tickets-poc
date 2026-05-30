package com.panini.supporttickets.data.remote.dto

data class TicketResponseDto(
    val id: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val supplier: String,
    val createdAt: String,
    val category: String,
    val distributionRegion: String
)
