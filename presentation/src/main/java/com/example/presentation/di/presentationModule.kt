package com.example.presentation.di

import com.example.presentation.bookmarks.BookmarksViewModel
import com.example.presentation.details.DetailsViewModel
import com.example.presentation.home.viewmodel.HomeViewModel
import com.example.presentation.search.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        HomeViewModel(
            getTrending = get(),
            getNowPlaying = get(),
            bookmarkMovie = get()
        )
    }

    viewModel {
        SearchViewModel(
            searchMovies = get()
        )
    }

    viewModel {
        DetailsViewModel(
            getDetails = get(),
            bookmarkMovie = get()
        )
    }

    viewModel {
        BookmarksViewModel(
            getBookmarks = get()
        )
    }
}
