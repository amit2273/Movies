package com.example.presentation.details

sealed class DetailsIntent  {
    data class Load(val movieId: Int) : DetailsIntent()
    data class ToggleBookmark(val movieId: Int, val bookmarked: Boolean) : DetailsIntent()
    data object Share : DetailsIntent()
}
