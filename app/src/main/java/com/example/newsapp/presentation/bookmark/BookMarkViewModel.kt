package com.example.newsapp.presentation.bookmark


import androidx.lifecycle.ViewModel
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel(){

    private val _state = MutableStateFlow(BookMarkState())
    val state: StateFlow<BookMarkState> = _state

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUseCases.getArticlesUseCase().onEach {
            _state.value = _state.value.copy(articles = it)
        }
    }
}