package com.bichi.mvvmsampleapp.data.network.responses

import com.bichi.mvvmsampleapp.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)