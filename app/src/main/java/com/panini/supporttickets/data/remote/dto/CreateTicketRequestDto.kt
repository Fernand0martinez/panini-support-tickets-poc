package com.panini.supporttickets.data.remote.dto

data class CreateTicketRequestDto(
    val title: String,
    val description: String,
    val priority: String,
    val supplier: String,
    val category: String,
    val distributionRegion: String
)
