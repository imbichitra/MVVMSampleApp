package com.bichi.mvvmsampleapp.ui.home.quotes

import com.bichi.mvvmsampleapp.R
import com.bichi.mvvmsampleapp.data.db.entities.Quote
import com.bichi.mvvmsampleapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote:Quote
) :BindableItem<ItemQuoteBinding>(){
    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }

}