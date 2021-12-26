package com.munny.nearplacecategory.ui.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.values.Colors.Lilac800
import com.munny.nearplacecategory.values.Colors.Lilac900
import timber.log.Timber

@Composable
fun MainScreen(
    items: List<NavItem>
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MainNavScreen.fromRoute(
        backStackEntry?.destination?.route
    )

    Scaffold(
        topBar = {
            MainTopBar(currentScreen.label)
        },
        bottomBar = {
            MainBottomBar(items, currentScreen.label) { route ->
                if (currentScreen.name == route) {
                    return@MainBottomBar
                }

                navController.navigate(route) {
                    popUpTo(MainNavScreen.Near.name) {
                        if (route == MainNavScreen.Near.name) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            items = items
        )
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    items: List<NavItem>
) {
    NavHost(
        navController = navController,
        startDestination = MainNavScreen.Near.name,
        modifier = modifier
    ) {
        items.forEach { screen ->
            composable(screen.navScreen.name) {
                screen.content.invoke()
            }
        }
    }
}

@Composable
fun MainTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.text_black)
            )
        },
        backgroundColor = Color.White
    )
}

@Composable
fun MainBottomBar(
    items: List<NavItem>,
    selectedLabel: String,
    tabSelected: (route: String) -> Unit
) {
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        items.forEach {
            NavigationItem(
                icon = it.navScreen.icon,
                label = it.navScreen.label,
                selected =  it.navScreen.label == selectedLabel,
                tabSelected = {
                    tabSelected.invoke(it.navScreen.name)
                    it.onTabSelected.invoke()
                }
            )
        }
    }
}

@Composable
fun RowScope.NavigationItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    tabSelected: () -> Unit
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        selected = selected,
        label = {
            Text(text = label)
        },
        onClick = tabSelected,
        selectedContentColor = Lilac900,
        unselectedContentColor = Color.DarkGray
    )
}