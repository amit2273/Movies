package com.example.domain.usecase

import com.example.domain.MovieRepository

class BookmarkMovieUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, bookmarked: Boolean) =
        repository.bookmarkMovie(movieId, bookmarked)
}
