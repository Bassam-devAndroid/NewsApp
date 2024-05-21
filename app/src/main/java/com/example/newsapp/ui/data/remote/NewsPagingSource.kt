package com.example.newsapp.ui.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.ui.domain.models.Article

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val sources: String,
): PagingSource<Int, Article>() {

    private var totalArticleCount = 0
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.getNews(sources = sources, page = page)
            totalArticleCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title }
            LoadResult.Page(
                data = newsResponse.articles,
                prevKey = null,
                nextKey = if (totalArticleCount == newsResponse.totalResults) null else page + 1,
            )
        }catch (e: Exception){
            e.stackTrace
            LoadResult.Error(
                throwable = e
            )
        }
    }
}