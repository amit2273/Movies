package com.example.domain.usecase

import com.example.domain.Movie
import com.example.domain.MovieRepository

class SearchMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String): List<Movie> =
        repository.searchMovies(query)
}
