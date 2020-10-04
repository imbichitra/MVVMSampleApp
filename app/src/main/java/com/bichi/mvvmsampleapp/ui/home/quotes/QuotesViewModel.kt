package com.bichi.mvvmsampleapp.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.bichi.mvvmsampleapp.data.repositories.QuotesRepository
import com.bichi.mvvmsampleapp.util.lazyDefferd

class QuotesViewModel(
    quotesRepository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDefferd {
        quotesRepository.getQuotes()
    }
}