package com.example.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.SearchMoviesUseCase
import com.example.presentation.search.SearchIntent
import com.example.presentation.search.SearchState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMovies: SearchMoviesUseCase
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        observeSearch()
    }

    fun dispatch(intent: SearchIntent) {
        when (intent) {
             is SearchIntent.QueryChanged -> queryFlow.value = intent.query
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            queryFlow
                .debounce(400)
                .distinctUntilChanged()
                .collectLatest { query ->
                    _state.update { it.copy(loading = true, query = query) }
                    val result = searchMovies(query)
                    _state.update {
                        it.copy(results = result, loading = false)
                    }
                }
        }
    }
}
