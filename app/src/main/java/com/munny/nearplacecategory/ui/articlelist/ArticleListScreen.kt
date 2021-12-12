package com.munny.nearplacecategory.ui.articlelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.extensions.toDistance
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.values.Colors

@Composable
fun ArticleListScreen(
    title: String,
    placeList: List<Place>,
    backPressedEvent: () -> Unit,
    itemClickEvent: (Place) -> Unit,
    categories: List<String>,
    categoryClickEvent: (String) -> Unit
) {
    Scaffold(
        topBar = {
            ArticleScreenToolbar(
                title = title,
                backPressedEvent = backPressedEvent
            )
        }
    ) { innerPadding ->
        Contents(
            modifier = Modifier.padding(innerPadding),
            placeList = placeList,
            itemClickEvent = itemClickEvent,
            categories = categories,
            categoryClickEvent = categoryClickEvent
        )
    }
}

@Composable
fun ArticleScreenToolbar(
    title: String,
    backPressedEvent: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.text_black)
                )
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_backpress_black_24dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable { backPressedEvent.invoke() }
                )
            },
            backgroundColor = Color.White
        )
        Shadow()
    }
}

@Composable
fun Shadow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                brush = Brush.verticalGradient(
                    0f to Color(0x99AAAAAA),
                    1f to Color(0x22AAAAAA)
                )
            )
    )
}

@Composable
fun Contents(
    modifier: Modifier = Modifier,
    placeList: List<Place>,
    itemClickEvent: (Place) -> Unit,
    categories: List<String>,
    categoryClickEvent: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        flingBehavior = ScrollableDefaults.flingBehavior()
    ) {
        item { Spacer(modifier = Modifier.size(12.dp)) }
        item {
            CategoryList(
                categories = categories,
                categoryClickEvent = categoryClickEvent
            )
        }
        item { Spacer(modifier = Modifier.size(16.dp)) }
        items(placeList) { article ->
            ArticlePreview(article) {
                itemClickEvent.invoke(article)
            }
        }
    }
}

@Composable
fun CategoryList(
    categories: List<String>,
    categoryClickEvent: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(categories) { category ->
            Category(item = category) {
                categoryClickEvent(category)
            }
        }
    }
}

@Composable
fun Category(
    item: String,
    onClickAction: () -> Unit
) {
    Text(
        text = item,
        fontSize = 12.sp,
        color = Colors.Lilac700,
        modifier = Modifier
            .border(1.dp, Colors.Lilac700, RoundedCornerShape(16.dp))
            .padding(
                horizontal = 10.dp, vertical = 4.dp
            )
            .clickable {
                onClickAction.invoke()
            }
    )
}

@Composable
fun ArticlePreview(place: Place, onClickAction: () -> Unit) {
    val placeImage = rememberImagePainter(
        place.articleImage?.url
    ) {
        placeholder(R.drawable.ic_restaurant_placeholder)
        error(R.drawable.ic_restaurant_placeholder)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .clickable(
                    onClick = onClickAction,
                    indication = rememberRipple(bounded = true),
                    interactionSource = MutableInteractionSource()
                )
        ) {
            Image(
                painter = placeImage,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .aspectRatio(3 / 2f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = place.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.text_black)
            )
            Text(
                text = place.distance.toDistance(),
                fontSize = 12.sp,
                color = colorResource(R.color.text_gray)
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
}