package com.munny.nearplacecategory.ui.shared.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.extensions.toDistance
import com.munny.nearplacecategory.model.Place

@Composable
fun ArticleItem(
    place: Place,
    onClickAction: () -> Unit,
    onLikeClickEvent: () -> Unit
) {
    val placeImage = rememberImagePainter(place.placeUrl) {
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = place.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.text_black)
                )
                FavoriteIcon(
                    isLiked = place.isLiked,
                    onLikeClickEvent = onLikeClickEvent,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Text(
                text = place.distance.toDistance(),
                fontSize = 12.sp,
                color = colorResource(R.color.text_gray)
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun HorizontalArticleItem(
    place: Place,
    modifier: Modifier = Modifier,
    onLikeClickEvent: () -> Unit,
    onClickEvent: () -> Unit,
) {
    val painter = rememberImagePainter(data = place.placeUrl) {
        error(R.drawable.ic_restaurant_placeholder)
        placeholder(R.drawable.ic_restaurant_placeholder)
    }

    ConstraintLayout(
        modifier = modifier
            .clickable(onClick = onClickEvent)
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        val (image, contents, like) = createRefs()

        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .height(80.dp)
                .aspectRatio(1f, true)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        Column(
            modifier = Modifier
                .padding(
                    top = 2.dp,
                    start = 12.dp
                )
                .constrainAs(contents) {
                    top.linkTo(image.top)
                    start.linkTo(image.end)
                }
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

        FavoriteIcon(
            isLiked = place.isLiked,
            onLikeClickEvent = onLikeClickEvent,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(like) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
fun FavoriteIcon(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
    onLikeClickEvent: () -> Unit
) {
    val vector: Any
    val tint: Color

    if (isLiked) {
        vector = Icons.Sharp.Favorite
        tint = Color.Red
    } else {
        vector = Icons.Sharp.FavoriteBorder
        tint = Color.DarkGray
    }

    Icon(
        imageVector = vector,
        contentDescription = null,
        tint = tint,
        modifier = modifier
            .clickable(
                onClick = onLikeClickEvent,
                interactionSource = MutableInteractionSource(),
                indication = null
            )
    )
}