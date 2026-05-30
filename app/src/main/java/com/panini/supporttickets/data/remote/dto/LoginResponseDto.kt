package com.panini.supporttickets.data.remote.dto

data class LoginResponseDto(
    val accessToken: String,
    val userName: String,
    val role: String
)
