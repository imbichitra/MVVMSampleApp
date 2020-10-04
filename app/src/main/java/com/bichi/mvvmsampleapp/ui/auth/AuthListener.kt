package com.bichi.mvvmsampleapp.ui.auth

import com.bichi.mvvmsampleapp.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: User)
    fun onFailure(message: String)
}