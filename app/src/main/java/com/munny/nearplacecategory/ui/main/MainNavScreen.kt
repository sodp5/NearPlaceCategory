package com.munny.nearplacecategory.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainNavScreen(
    val icon: ImageVector,
    val label: String
) {
    Near(
        icon = Icons.Filled.Search,
        label = "내주변"
    ),
    Random(
        icon = Icons.Filled.Refresh,
        label = "랜덤찾기"
    ),
    Favorite(
        icon = Icons.Filled.Favorite,
        label = "찜목록"
    ),
    MyInfo(
        icon = Icons.Filled.Person,
        label = "내정보"
    );

    companion object {
        fun fromRoute(route: String?): MainNavScreen {
            return when (route) {
                Near.name -> Near
                Random.name -> Random
                Favorite.name -> Favorite
                MyInfo.name -> MyInfo
                null -> Near

                else -> throw Exception("not exist screen")
            }
        }
    }
}