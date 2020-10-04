package com.bichi.mvvmsampleapp.ui.home.quotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bichi.mvvmsampleapp.R
import com.bichi.mvvmsampleapp.data.db.entities.Quote
import com.bichi.mvvmsampleapp.util.Coroutines
import com.bichi.mvvmsampleapp.util.hide
import com.bichi.mvvmsampleapp.util.show
import com.bichi.mvvmsampleapp.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_quotes.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(),KodeinAware {

    override val kodein by kodein()
    private val factory:QuotesViewModelFactory by instance()
    private lateinit var viewMode :QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewMode = ViewModelProvider(this,factory).get(QuotesViewModel::class.java)
        bindUI()
    }

    private fun bindUI() =Coroutines.main {
        progress_bar.show()
        viewMode.quotes.await().observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Quote>.toQuoteItem():List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }
}