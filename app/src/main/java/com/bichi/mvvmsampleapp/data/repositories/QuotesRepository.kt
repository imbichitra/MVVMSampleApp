package com.bichi.mvvmsampleapp.data.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bichi.mvvmsampleapp.data.db.AppDataBase
import com.bichi.mvvmsampleapp.data.db.entities.Quote
import com.bichi.mvvmsampleapp.data.network.MyApi
import com.bichi.mvvmsampleapp.data.network.SafeApiRequest
import com.bichi.mvvmsampleapp.data.preferences.PreferenceProvider
import com.bichi.mvvmsampleapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

private const val MINIMUM_INTERVAL = 6

class QuotesRepository(
    private val api:MyApi,
    private val db:AppDataBase,
    private val prefs:PreferenceProvider
) :SafeApiRequest(){

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes():LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes(){
        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(lastSavedAt)){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isFetchNeeded(lastSavedAt: String):Boolean{
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm:ss") //or use getDateInstance()
        val nowDate = formatter.format(date)

        val d1 = formatter.parse(lastSavedAt)
        val d2 = formatter.parse(nowDate)
        val hour = (d2?.time!! - d1?.time!!)/(60 * 60 * 1000) % 24
        return hour> MINIMUM_INTERVAL
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveQuotes(quotes: List<Quote>?) {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm:ss") //or use getDateInstance()
        val formatedDate = formatter.format(date)
        Coroutines.io {
            prefs.saveLastSaveAt(formatedDate)
            quotes?.let { db.getQuoteDao().saveAllQuotes(it) }
        }
    }
}