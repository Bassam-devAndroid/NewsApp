package com.example.newsapp.domain.usecases.news

data class NewsUseCases(
    val getNews: GetNews,
    val searchNews: SearchNewsUseCase,
    val getArticlesUseCase: GetArticlesUseCase,
    val upsertUseCase: UpsertUseCase,
    val deleteArticleUseCase: DeleteArticleUseCase,
)
