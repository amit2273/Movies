package com.example.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.BookmarkMovieUseCase
import com.example.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.domain.usecase.GetTrendingMoviesUseCase
import com.example.presentation.home.HomeEffect
import com.example.presentation.home.HomeIntent
import com.example.presentation.home.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTrending: GetTrendingMoviesUseCase,
    private val getNowPlaying: GetNowPlayingMoviesUseCase,
    private val bookmarkMovie: BookmarkMovieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    fun dispatch(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Load -> loadMovies()
            is HomeIntent.ToggleBookmark -> toggleBookmark(intent)
            is HomeIntent.MovieClicked ->
                emitEffect(HomeEffect.NavigateToDetails(intent.movieId))
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            combine(
                getTrending(),
                getNowPlaying()
            ) { trending, nowPlaying ->
                trending to nowPlaying
            }.catch {
                emitEffect(HomeEffect.ShowError(it.message ?: "Unknown error"))
            }.collect { (trending, nowPlaying) ->
                _state.update {
                    it.copy(
                        trending = trending,
                        nowPlaying = nowPlaying,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun toggleBookmark(intent: HomeIntent.ToggleBookmark) {
        viewModelScope.launch {
            bookmarkMovie(intent.movieId, intent.bookmarked)
        }
    }

    private fun emitEffect(effect: HomeEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}
