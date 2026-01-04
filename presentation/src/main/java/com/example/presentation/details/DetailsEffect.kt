package com.example.presentation.details

import com.example.domain.Movie

sealed class DetailsEffect  {
    data class ShareMovie(val movie: Movie) : DetailsEffect()
}
