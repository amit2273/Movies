package com.example.domain.usecase

import com.example.domain.Movie
import com.example.domain.MovieRepository

class GetMovieDetailsUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie =
        repository.movieDetails(movieId)
}
