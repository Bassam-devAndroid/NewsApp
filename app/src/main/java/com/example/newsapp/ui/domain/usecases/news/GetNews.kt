package com.example.newsapp.ui.domain.usecases.news

import androidx.paging.PagingData
import com.example.newsapp.ui.domain.models.Article
import com.example.newsapp.ui.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>):Flow<PagingData<Article>>{
        return newsRepository.getNews(sources = sources)
    }
}