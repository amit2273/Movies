package com.example.presentation.details

import com.example.domain.Movie

data class DetailsState(
    val movie: Movie? = null,
    val loading: Boolean = false
)
