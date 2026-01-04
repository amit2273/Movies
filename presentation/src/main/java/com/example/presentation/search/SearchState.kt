package com.example.presentation.search

import com.example.domain.Movie

data class SearchState(
    val query: String = "",
    val results: List<Movie> = emptyList(),
    val loading: Boolean = false
)
