package com.example.presentation.home

import com.example.domain.Movie


data class HomeState(
    val trending: List<Movie> = emptyList(),
    val nowPlaying: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
