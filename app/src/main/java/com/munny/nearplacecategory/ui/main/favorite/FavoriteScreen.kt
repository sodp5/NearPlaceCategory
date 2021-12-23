package com.munny.nearplacecategory.ui.main.favorite

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.main.random.HorizontalArticleItem

@Composable
fun FavoriteScreen(
    places: List<Place>,
    onItemClickEvent: (Place) -> Unit,
    onLikeClickEvent: (Place) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        items(places) { place ->
            HorizontalArticleItem(
                place = place,
                onClickEvent = {
                    onItemClickEvent.invoke(place)
                },
                onLikeClickEvent = {
                    onLikeClickEvent.invoke(place)
                }
            )
        }
    }
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