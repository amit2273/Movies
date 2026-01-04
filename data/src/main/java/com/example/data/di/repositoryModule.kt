package com.example.data.di

import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.MovieRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<MovieRepository> {
        MovieRepositoryImpl(
            api = get(),
            dao = get(),
            apiKey = get(named("TMDB_API_KEY"))
        )
    }
}
