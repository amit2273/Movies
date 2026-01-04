package com.example.domain.usecase

import com.example.domain.Movie
import com.example.domain.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> =
        repository.trendingMovies()
}
