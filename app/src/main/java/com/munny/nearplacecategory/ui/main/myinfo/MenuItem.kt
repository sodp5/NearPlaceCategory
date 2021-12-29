package com.munny.nearplacecategory.ui.main.myinfo

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val itemName: String,
    val itemIcon: ImageVector,
    val onItemClick: () -> Unit
)
