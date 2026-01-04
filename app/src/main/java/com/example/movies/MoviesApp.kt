package com.example.movies

import android.app.Application
import com.example.data.di.databaseModule
import com.example.data.di.networkModule
import com.example.data.di.repositoryModule
import com.example.domain.di.domainModule
import com.example.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoviesApp)
            modules(
                listOf(presentationModule,
                    databaseModule,
                    repositoryModule,
                    domainModule,
                    networkModule)
            )
        }
    }
}
