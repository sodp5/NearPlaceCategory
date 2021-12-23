package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.model.ArticleInfo
import com.munny.nearplacecategory.values.Colors

@Composable
fun ArticleScreen(
    articleInfoState: ArticleInfo,
    placeMap: Bitmap?
) {
    Column {
        PlaceImage(articleInfoState.placeUrl)
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            PlaceInfo(articleInfoState)
            Spacer(modifier = Modifier.size(20.dp))
            Divider()
            Spacer(modifier = Modifier.size(16.dp))
            PlaceMap(placeMap)
        }
    }
}

@Composable
fun PlaceImage(placeUrl: String) {
    val placeImage = rememberImagePainter(placeUrl) {
        placeholder(R.drawable.ic_restaurant_placeholder)
        error(R.drawable.ic_restaurant_placeholder)
    }

    placeUrl.isEmpty().let {
        if (it) {
            return@let
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 2f)
        ) {
            placeImage.let { image ->
                Image(
                    painter = image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun PlaceInfo(state: ArticleInfo) {
    Text(
        text = state.name,
        color = Colors.TextBlack,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = state.categories,
        color = Colors.TextGray,
        fontSize = 12.sp
    )
    Spacer(modifier = Modifier.size(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = state.phoneNumber,
            color = Colors.TextGray,
            fontSize = 12.sp
        )
        Text(
            text = state.distance,
            color = Colors.TextGray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun Divider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(color = Colors.Gray)
    )
}

@Composable
fun PlaceMap(map: Bitmap?) {
    map?.let {
        Image(
            bitmap = map.asImageBitmap(),
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlePreview() {
    ArticleScreen(
        ArticleInfo("청년다방", "한식", "010-1234-5670", "112km"),
        null
    )
}