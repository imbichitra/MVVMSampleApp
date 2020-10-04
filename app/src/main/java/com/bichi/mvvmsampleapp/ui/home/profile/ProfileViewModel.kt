package com.bichi.mvvmsampleapp.ui.home.profile

import androidx.lifecycle.ViewModel
import com.bichi.mvvmsampleapp.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()
}