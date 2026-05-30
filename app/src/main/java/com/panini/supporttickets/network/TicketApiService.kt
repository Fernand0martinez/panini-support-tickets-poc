package com.panini.supporttickets.network

import com.panini.supporttickets.data.remote.dto.CreateTicketRequestDto
import com.panini.supporttickets.data.remote.dto.LoginRequestDto
import com.panini.supporttickets.data.remote.dto.LoginResponseDto
import com.panini.supporttickets.data.remote.dto.TicketResponseDto
import com.panini.supporttickets.data.remote.dto.UpdateTicketPriorityRequestDto
import com.panini.supporttickets.data.remote.dto.UpdateTicketStatusRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @GET("tickets")
    suspend fun getTickets(): List<TicketResponseDto>

    @POST("tickets")
    suspend fun createTicket(@Body request: CreateTicketRequestDto): TicketResponseDto

    @GET("tickets/{ticketId}")
    suspend fun getTicket(@Path("ticketId") ticketId: String): TicketResponseDto

    @PATCH("tickets/{ticketId}/status")
    suspend fun updateStatus(
        @Path("ticketId") ticketId: String,
        @Body request: UpdateTicketStatusRequestDto
    ): TicketResponseDto

    @PATCH("tickets/{ticketId}/priority")
    suspend fun updatePriority(
        @Path("ticketId") ticketId: String,
        @Body request: UpdateTicketPriorityRequestDto
    ): TicketResponseDto
}
