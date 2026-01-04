package com.example.presentation.home

sealed interface HomeEffect  {
    data class NavigateToDetails(val movieId: Int) : HomeEffect
    data class ShowError(val message: String) : HomeEffect
}
