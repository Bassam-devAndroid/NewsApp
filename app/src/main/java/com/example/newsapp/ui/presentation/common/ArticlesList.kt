package com.example.newsapp.ui.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.ui.domain.models.Article
import com.loc.newsapp.presentation.Dimens.MediumPadding1

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onArticleClick: (Article) -> Unit
) {

}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>
): Boolean{

    val loadState = articles.loadState
    val error = when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when{
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }
        else != null -> {

            false
        }
    }
}

@Composable
private fun ShimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)){
        repeat(10){
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}