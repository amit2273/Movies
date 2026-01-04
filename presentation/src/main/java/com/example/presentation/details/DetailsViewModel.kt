package com.example.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.BookmarkMovieUseCase
import com.example.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getDetails: GetMovieDetailsUseCase,
    private val bookmarkMovie: BookmarkMovieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DetailsEffect>()
    val effect = _effect.asSharedFlow()

    fun dispatch(intent: DetailsIntent)  = viewModelScope.launch {
        when (intent) {
            is DetailsIntent.Load -> loadMovie(intent.movieId)
            is DetailsIntent.ToggleBookmark ->
                bookmarkMovie(intent.movieId, intent.bookmarked)
            DetailsIntent.Share ->
                _state.value.movie?.let {
                    emitEffect(DetailsEffect.ShareMovie(it))
                }
        }
    }


    private fun loadMovie(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val movie = getDetails(id)
            _state.update {
                it.copy(movie = movie, loading = false)
            }
        }
    }

    private fun emitEffect(effect: DetailsEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}
