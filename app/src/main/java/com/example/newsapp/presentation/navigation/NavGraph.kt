package com.example.newsapp.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.newsapp.presentation.news_navigator.NewsNavigator
import com.example.newsapp.ui.presentation.onboarding.OnBoardingScreen
import com.example.newsapp.ui.presentation.onboarding.OnBoardingViewModel


@Composable
fun NavGraph(
    startDestination: String
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination){
        navigation(
            startDestination = Route.OnBoardingScreen.route,
            route = Route.AppStartNavigation.route,
        ){
            composable(
                route = Route.OnBoardingScreen.route
            ){
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen (event = viewModel::onEvent)
            }
        }

        navigation(
            startDestination = Route.NewsNavigationScreen.route,
            route = Route.NewsNavigation.route,
        ){
            composable(
                route = Route.NewsNavigationScreen.route
            ){
                NewsNavigator()
            }
        }
    }
}


