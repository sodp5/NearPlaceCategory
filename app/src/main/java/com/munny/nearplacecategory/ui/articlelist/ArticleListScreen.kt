package com.munny.nearplacecategory.ui.articlelist

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
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
import com.munny.nearplacecategory.ui.shared.article.ArticleItem
import com.munny.nearplacecategory.values.Colors

@Composable
fun ArticleListScreen(
    title: String,
    placeList: List<Place>,
    backPressedEvent: () -> Unit,
    itemClickEvent: (Place) -> Unit,
    categories: List<String>,
    categoryClickEvent: (String) -> Unit,
    onLikeClickEvent: (Place) -> Unit
) {
    Scaffold(
        topBar = {
            ArticleScreenToolbar(
                title = title,
                backPressedEvent = backPressedEvent
            )
        }
    ) { innerPadding ->
        ArticleListContents(
            modifier = Modifier.padding(innerPadding),
            placeList = placeList,
            itemClickEvent = itemClickEvent,
            categories = categories,
            categoryClickEvent = categoryClickEvent,
            onLikeClickEvent = onLikeClickEvent
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
fun ArticleListContents(
    modifier: Modifier = Modifier,
    placeList: List<Place>,
    itemClickEvent: (Place) -> Unit,
    categories: List<String>,
    categoryClickEvent: (String) -> Unit,
    onLikeClickEvent: (Place) -> Unit
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
            ArticleItem(
                place = article,
                onClickAction = {
                    itemClickEvent.invoke(article)
                },
                onLikeClickEvent = {
                    onLikeClickEvent.invoke(article)
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
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