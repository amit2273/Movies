package com.example.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Movie
import com.example.domain.usecase.GetBookmarksUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BookmarksViewModel(
    getBookmarks: GetBookmarksUseCase
) : ViewModel() {

    val state = getBookmarks()
        .map { movies ->
            BookmarksState(movies)
        }
        .stateIn(

            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            BookmarksState(emptyList())
        )
}

data class BookmarksState(
    val movies: List<Movie>
)
