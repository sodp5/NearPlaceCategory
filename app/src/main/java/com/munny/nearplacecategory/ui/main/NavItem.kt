package com.munny.nearplacecategory.ui.main

import androidx.compose.runtime.Composable

data class NavItem(
    val navScreen: MainNavScreen,
    val onTabSelected: () -> Unit = { },
    val content: @Composable () -> Unit
)
