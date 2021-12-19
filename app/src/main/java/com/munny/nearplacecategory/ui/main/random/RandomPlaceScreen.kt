package com.munny.nearplacecategory.ui.main.random

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.extensions.ifTrue
import com.munny.nearplacecategory.extensions.toDistance
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.article.Divider
import com.munny.nearplacecategory.ui.articlelist.ArticleItem
import com.munny.nearplacecategory.ui.main.MainNavScreen
import com.munny.nearplacecategory.values.Colors.Lilac500
import com.munny.nearplacecategory.values.Colors.Lilac700
import com.munny.nearplacecategory.values.Colors.TextBlack

@Composable
fun RandomPlaceScreen(
    histories: List<Place>,
    recentlyPlace: Place?,
    onPlaceClickEvent: (Place) -> Unit,
    selectRandomPlaceEvent: () -> Unit
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            recentlyPlace?.let {
                item {
                    ArticleItem(place = it) {
                        onPlaceClickEvent.invoke(it)
                    }
                }
            }
            histories.isNotEmpty().ifTrue {
                item {
                    Spacer(modifier = Modifier.size(12.dp))
                    RandomArticleHeader()
                }
            }

            items(
                items = histories,
                key = { it.id }
            ) { place ->
                RandomArticleHistory(place = place) {
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
fun RandomArticleHistory(place: Place, onClickEvent: () -> Unit) {
    val painter = rememberImagePainter(data = place.articleImage?.url) {
        error(R.drawable.ic_restaurant_placeholder)
        placeholder(R.drawable.ic_restaurant_placeholder)
    }

    Row(
        modifier = Modifier
            .clickable(onClick = onClickEvent)
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .height(80.dp)
                .aspectRatio(1f, true)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(
            Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = place.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.text_black)
            )
            Text(
                text = place.categories.joinToString(", "),
                fontSize = 11.sp,
                color = colorResource(R.color.text_gray)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = place.distance.toDistance(),
                fontSize = 11.sp,
                color = colorResource(R.color.text_gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RandomPlaceScreenPreview() {
    RandomPlaceScreen(
        listOf(
            Place(0, "hello", null, emptyList(), "", 10, 0.0, 0.0),
            Place(1, "hello", null, emptyList(), "", 10, 0.0, 0.0)
        ),
        Place(1, "hello", null, emptyList(), "", 10, 0.0, 0.0),
        {}
    ) {

    }
}