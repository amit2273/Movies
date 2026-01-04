package com.example.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun trendingMovies(): Flow<List<Movie>>

    fun nowPlayingMovies(): Flow<List<Movie>>

    fun bookmarkedMovies(): Flow<List<Movie>>

    suspend fun searchMovies(query: String): List<Movie>

    suspend fun movieDetails(movieId: Int): Movie

    suspend fun bookmarkMovie(movieId: Int, bookmarked: Boolean)
}

