package com.example.presentation.search

sealed interface SearchIntent  {
    data class QueryChanged(val query: String) : SearchIntent
}
