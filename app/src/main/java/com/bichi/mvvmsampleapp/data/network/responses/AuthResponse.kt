package com.bichi.mvvmsampleapp.data.network.responses

import com.bichi.mvvmsampleapp.data.db.entities.User

data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)