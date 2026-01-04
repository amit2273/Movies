package com.example.presentation.home

sealed interface HomeIntent  {
    object Load : HomeIntent
    data class ToggleBookmark(val movieId: Int, val bookmarked: Boolean) : HomeIntent
    data class MovieClicked(val movieId: Int) : HomeIntent
}
