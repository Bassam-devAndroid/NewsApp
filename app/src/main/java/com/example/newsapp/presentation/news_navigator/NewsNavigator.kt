package com.example.newsapp.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.models.Article
import com.example.newsapp.presentation.bookmark.BookMarkScreen
import com.example.newsapp.presentation.bookmark.BookMarkViewModel
import com.example.newsapp.presentation.details.DetailsEvent
import com.example.newsapp.presentation.details.DetailsScreen
import com.example.newsapp.presentation.details.DetailsViewModel
import com.example.newsapp.ui.presentation.home.HomeScreen
import com.example.newsapp.ui.presentation.home.HomeViewModel
import com.example.newsapp.ui.presentation.navigation.Route
import com.example.newsapp.ui.presentation.search.SearchScreen
import com.example.newsapp.ui.presentation.search.SearchState
import com.example.newsapp.ui.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(
                icon = R.drawable.ic_home,
                text = "Home"
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_search,
                text = "Search"
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_bookmark,
                text = "Bookmark"
            )
        )
    }

    val navController = rememberNavController()

    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when (backstackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookMarkScreen.route -> 2
        else -> 0
    }

    val isBottomBarVisible = remember(key1 = backstackState){
        backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.BookMarkScreen.route ||
                backstackState?.destination?.route == Route.HomeScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsButtonNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(navController, Route.HomeScreen.route)
                            1 -> navigateToTab(navController, Route.SearchScreen.route)
                            2 -> navigateToTab(navController, Route.BookMarkScreen.route)
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        )
        {
            composable(Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(navController, Route.SearchScreen.route)
                    },
                    navigateToDetails = {
                        navigateToDetails(navController, it)
                    }

                )
            }
            composable(Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state: SearchState = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(navController, it)
                    })
            }
            composable(Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if(viewModel.sideEffect != null){
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let {
                        DetailsScreen(
                            article = it,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() })
                    }
            }
            composable(Route.BookMarkScreen.route) {
                val viewModel: BookMarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookMarkScreen(state = state, navigate = {
                    navigateToDetails(navController, it)
                })
            }
        }


    }
}



fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}


fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailsScreen.route)
}