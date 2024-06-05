package com.example.newsapp.ui.data.remote.dto

import com.example.newsapp.ui.domain.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)