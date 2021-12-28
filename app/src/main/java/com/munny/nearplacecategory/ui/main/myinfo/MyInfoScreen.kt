package com.munny.nearplacecategory.ui.main.myinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.munny.nearplacecategory.values.Colors.TextBlack

@Composable
fun MyInfoScreen(
    location: String,
    menuItems: List<MenuItem>,
    locationRefreshEnabled: Boolean,
    locationRefreshEvent: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        MyLocation(
            location = location,
            locationRefreshEnabled = locationRefreshEnabled,
            locationRefreshEvent = locationRefreshEvent,
        )

        menuItems.forEach {
            InfoMenu(menuItem = it)
        }

    }
}

@Composable
fun InfoItemLayout(
    modifier: Modifier = Modifier,
    verticalInnerSpace: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .padding(vertical = verticalInnerSpace)
    ) {
        content()
    }
}

@Composable
fun MyLocation(
    location: String,
    locationRefreshEnabled: Boolean,
    locationRefreshEvent: () -> Unit
) {
    InfoItemLayout(
        verticalInnerSpace = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "내 위치",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextBlack
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = location,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

                if (locationRefreshEnabled) {
                    Icon(
                        imageVector = Icons.Default.Autorenew,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable(onClick = locationRefreshEvent)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoMenu(
    menuItem: MenuItem
) {
    InfoItemLayout(
        modifier = Modifier
            .clickable(
                onClick = menuItem.onItemClick,
                interactionSource = MutableInteractionSource(),
                indication = null
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = menuItem.itemIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = menuItem.itemName,
                    fontSize = 16.sp,
                    color = TextBlack
                )
            }

            Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyInfoScreenPreview() {
    MyInfoScreen(
        "서울시 강남구 테헤란로 14길",
        listOf(
            MenuItem(
                itemName = "내 주변 거리 설정",
                itemIcon = Icons.Default.Moving,
                onItemClick = { }
            ),
            MenuItem(
                itemName = "앱 버전",
                itemIcon = Icons.Default.Info,
                onItemClick = { }
            )
        ),
        locationRefreshEnabled = true,
        locationRefreshEvent = { }
    )
}