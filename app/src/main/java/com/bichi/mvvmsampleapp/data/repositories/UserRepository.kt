package com.bichi.mvvmsampleapp.data.repositories

import com.bichi.mvvmsampleapp.data.db.AppDataBase
import com.bichi.mvvmsampleapp.data.db.entities.User
import com.bichi.mvvmsampleapp.data.network.MyApi
import com.bichi.mvvmsampleapp.data.network.RetrofitClient
import com.bichi.mvvmsampleapp.data.network.SafeApiRequest
import com.bichi.mvvmsampleapp.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository(
    private val api:MyApi,
    private val db:AppDataBase
    //private val retrofitClient: RetrofitClient
) :SafeApiRequest(){

    suspend fun userLogin(email: String, password:String):AuthResponse{
        //return apiRequest { retrofitClient.getInstance().create(MyApi::class.java).userLogin(email,password) }
        return apiRequest { api.userLogin(email,password) }
    }

    suspend fun userSignup(
        name:String,
        email: String,
        password: String
    ):AuthResponse{
        return apiRequest { api.userSingup(name,email,password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}

