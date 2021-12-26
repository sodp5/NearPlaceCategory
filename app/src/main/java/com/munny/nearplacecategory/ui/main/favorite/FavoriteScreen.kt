package com.munny.nearplacecategory.ui.main.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.munny.nearplacecategory.extensions.ifTrue
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.shared.article.HorizontalArticleItem
import com.munny.nearplacecategory.values.Colors

@Composable
fun FavoriteScreen(
    places: List<Place>,
    onItemClickEvent: (Place) -> Unit,
    onLikeClickEvent: (Place) -> Unit
) {
    if (places.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            EmptyFavoritePlace(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }

    LazyColumn {
        items(places) { place ->
            HorizontalArticleItem(
                place = place,
                onClickEvent = {
                    onItemClickEvent.invoke(place)
                },
                onLikeClickEvent = {
                    onLikeClickEvent.invoke(place)
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun EmptyFavoritePlace(modifier: Modifier = Modifier) {
    Text(
        text = "맘에드는 장소에\n하트를 눌러서 모아 보세요!",
        fontSize = 16.sp,
        color = Colors.TextGray,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    FavoriteScreen(
        places = listOf(
            Place(0, "hello", null, emptyList(), "", 10, 0.0, 0.0, false),
            Place(1, "hello", null, emptyList(), "", 10, 0.0, 0.0, false)
        ),
        onItemClickEvent = {},
        onLikeClickEvent = {}
    )
}