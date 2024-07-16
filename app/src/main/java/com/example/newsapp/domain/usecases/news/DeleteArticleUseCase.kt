package com.example.newsapp.domain.usecases.news

import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.domain.models.Article

class DeleteArticleUseCase(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article) =
        newsDao.delete(article)
}