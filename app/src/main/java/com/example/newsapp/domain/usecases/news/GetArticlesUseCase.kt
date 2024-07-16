package com.example.newsapp.domain.usecases.news

import com.example.newsapp.data.local.NewsDao

class GetArticlesUseCase(
    private val newsDao: NewsDao
) {
    operator fun invoke() = newsDao.getArticles()
}