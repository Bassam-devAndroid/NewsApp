package com.example.newsapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.ui.domain.usecases.app_entry.AppEntryUseCases
import com.example.newsapp.ui.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
):ViewModel() {

    var splashConditionScreen by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.NewsNavigation.route)
        private set

    init {
        appEntryUseCases.loadAppEntryUseCase().onEach {
            if (it) {
                startDestination = Route.NewsNavigation.route
            } else {
                startDestination = Route.AppStartNavigation.route
            }
            delay(300)
            splashConditionScreen = false
        }.launchIn(viewModelScope)
    }


}