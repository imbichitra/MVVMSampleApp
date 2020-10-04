package com.bichi.mvvmsampleapp

import android.app.Application
import android.app.Presentation
import com.bichi.mvvmsampleapp.data.db.AppDataBase
import com.bichi.mvvmsampleapp.data.network.MyApi
import com.bichi.mvvmsampleapp.data.network.NetworkConnectionInterceptor
import com.bichi.mvvmsampleapp.data.network.RetrofitClient
import com.bichi.mvvmsampleapp.data.preferences.PreferenceProvider
import com.bichi.mvvmsampleapp.data.repositories.QuotesRepository
import com.bichi.mvvmsampleapp.data.repositories.UserRepository
import com.bichi.mvvmsampleapp.ui.auth.AuthViewModelFactory
import com.bichi.mvvmsampleapp.ui.home.profile.ProfileViewModelFactory
import com.bichi.mvvmsampleapp.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(),KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        //bind() from provider { RetrofitClient(instance()) }
        bind() from singleton { AppDataBase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(),instance()) }
        bind() from provider { QuotesRepository(instance(),instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }
    }

}