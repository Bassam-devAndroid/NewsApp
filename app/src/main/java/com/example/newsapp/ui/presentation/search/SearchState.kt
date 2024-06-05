package com.example.newsapp.ui.presentation.search

import androidx.paging.PagingData
import com.example.newsapp.ui.domain.models.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null,
)
