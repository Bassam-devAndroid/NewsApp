package com.example.newsapp.presentation.bookmark

import com.example.newsapp.domain.models.Article

data class BookMarkState(
    val articles: List<Article> = emptyList()
)
