package com.example.newsapp.ui.presentation.search

sealed class SearchEvent {

    data class updateSearchQuery(val searchQuery: String) : SearchEvent()

    object SearchNews : SearchEvent()
}