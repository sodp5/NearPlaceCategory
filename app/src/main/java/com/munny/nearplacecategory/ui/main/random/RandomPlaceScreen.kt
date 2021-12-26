package com.munny.nearplacecategory.ui.main.random

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.munny.nearplacecategory.extensions.ifTrue
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.article.Divider
import com.munny.nearplacecategory.ui.main.MainNavScreen
import com.munny.nearplacecategory.ui.shared.article.ArticleItem
import com.munny.nearplacecategory.ui.shared.article.HorizontalArticleItem
import com.munny.nearplacecategory.values.Colors.Lilac500
import com.munny.nearplacecategory.values.Colors.TextBlack
import com.munny.nearplacecategory.values.Colors.TextGray

@Composable
fun RandomPlaceScreen(
    histories: List<Place>,
    recentlyPlace: Place?,
    isLoading: Boolean,
    onPlaceClickEvent: (Place) -> Unit,
    onLikeClickEvent: (Place) -> Unit,
    selectRandomPlaceEvent: () -> Unit,
    onRefreshEvent: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = selectRandomPlaceEvent,
                backgroundColor = Lilac500,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = MainNavScreen.Random.icon,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        RandomPlaceContent(
            histories = histories,
            recentlyPlace = recentlyPlace,
            isLoading = isLoading,
            onPlaceClickEvent = onPlaceClickEvent,
            onLikeClickEvent = onLikeClickEvent,
            onRefreshEvent = onRefreshEvent,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun RandomPlaceContent(
    histories: List<Place>,
    recentlyPlace: Place?,
    isLoading: Boolean,
    onPlaceClickEvent: (Place) -> Unit,
    onLikeClickEvent: (Place) -> Unit,
    onRefreshEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeState,
        onRefresh = onRefreshEvent
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(12.dp))
            }

            recentlyPlace?.let {
                item {
                    ArticleItem(
                        place = it,
                        onClickAction = {
                            onPlaceClickEvent.invoke(it)
                        },
                        onLikeClickEvent = {

                        },
                    )
                }
            }

            item {
                RandomArticleHeader()
            }

            histories.isEmpty().ifTrue {
                item {
                    EmptyRandomHistory()
                }
            }

            items(
                items = histories,
                key = { it.id }
            ) { place ->
                HorizontalArticleItem(
                    place = place,
                    onLikeClickEvent = {
                        onLikeClickEvent.invoke(place)
                    }
                ) {
                    onPlaceClickEvent.invoke(place)
                }
                Divider()
            }
        }
    }
}

@Composable
fun RandomArticleHeader() {
    Text(
        text = "찾았던 장소",
        fontWeight = FontWeight.Bold,
        color = TextBlack,
        fontSize = 14.sp
    )
}

@Composable
fun EmptyRandomHistory() {
    Spacer(modifier = Modifier.size(12.dp))
    Text(
        text = "하단의 버튼을 눌러서 가게를 추가 해보세요!",
        fontSize = 12.sp,
        color = TextGray
    )
}

@Preview(showBackground = true)
@Composable
fun RandomPlaceScreenPreview() {
    RandomPlaceScreen(
        listOf(
            Place(0, "hello", "", emptyList(), "", 10, 0.0, 0.0, false),
            Place(1, "hello", "", emptyList(), "", 10, 0.0, 0.0, false)
        ),
        Place(1, "hello", "", emptyList(), "", 10, 0.0, 0.0, false),
        false,
        { },
        { },
        { }
    ) {

    }
}