package com.example.domain.di

import com.example.domain.usecase.BookmarkMovieUseCase
import com.example.domain.usecase.GetBookmarksUseCase
import com.example.domain.usecase.GetMovieDetailsUseCase
import com.example.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.domain.usecase.GetTrendingMoviesUseCase
import com.example.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetTrendingMoviesUseCase(get()) }
    factory { GetNowPlayingMoviesUseCase(get()) }
    factory { GetBookmarksUseCase(get()) }
    factory { SearchMoviesUseCase(get()) }
    factory { GetMovieDetailsUseCase(get()) }
    factory { BookmarkMovieUseCase(get()) }
}