package com.example.newsapp.domain.usecases.news


import com.example.newsapp.domain.repository.NewsRepository

class GetArticlesUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() = newsRepository.getAllArticles()
}