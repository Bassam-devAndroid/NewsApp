package com.example.newsapp.domain.usecases.news

import com.example.newsapp.domain.repository.NewsRepository

class SearchNewsUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>) =
        newsRepository.searchNews(searchQuery, sources)
}