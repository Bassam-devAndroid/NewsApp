package com.example.newsapp.domain.usecases.news


import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.repository.NewsRepository

class UpsertUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) =
        newsRepository.upsertArticle(article)
}