package com.example.domain

data class Movie(
    val id: Int,
    val title: String,
    val poster: String?,
    val overview: String,
    val isBookmarked: Boolean
)

