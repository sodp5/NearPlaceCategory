package com.munny.nearplacecategory.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import com.google.android.gms.location.*
import com.gun0912.tedpermission.coroutine.TedPermission
import com.munny.nearplacecategory.extensions.startActivity
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.article.ArticleActivity
import com.munny.nearplacecategory.ui.articlelist.ArticleListActivity
import com.munny.nearplacecategory.ui.main.random.RandomPlaceScreen
import com.munny.nearplacecategory.ui.main.random.RandomPlaceViewModel
import com.munny.nearplacecategory.ui.nearcategorylist.NearCategoryListScreen
import com.munny.nearplacecategory.ui.nearcategorylist.NearCategoryListViewModel
import com.munny.nearplacecategory.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val nearCategoryListViewModel: NearCategoryListViewModel by viewModels()
    private val randomPlaceViewModel: RandomPlaceViewModel by viewModels()

    private val locationProvider by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                nearCategoryListViewModel = nearCategoryListViewModel,
                nearCategoryClickEvent = {
                    nearCategoryListViewModel.setupArticleImages(it)
                },
                randomPlaceViewModel = randomPlaceViewModel,
                placeClickEvent = {
                    val intent = ArticleActivity.getIntent(this, it)

                    startActivity(intent)
                }
            )
        }

        nearCategoryListViewModel.selectCategoryEvent.observeEvent(this) {
            startActivity<ArticleListActivity>(Bundle().apply {
                putParcelable(ArticleListActivity.EXTRA_CATEGORY_ITEM, it)
            })
        }

        nearCategoryListViewModel.searchPoiEvent.observeEvent(this) {
            randomPlaceViewModel.setAllPlace(it)
        }
    }

    override fun onStart() {
        super.onStart()

        enableLocationListener()
    }

    private fun enableLocationListener() {
        lifecycle.coroutineScope.launch {
            val tedPermission = TedPermission.create()
                .setPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .check()

            @SuppressLint("MissingPermission")
            if (tedPermission.isGranted) {
                val locationRequest = LocationRequest.create()
                val locationCallBack = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        val location = result.lastLocation

                        nearCategoryListViewModel.setLatLng(location.latitude, location.longitude)
                    }

                    override fun onLocationAvailability(availability: LocationAvailability) = Unit
                }
                locationProvider.requestLocationUpdates(
                    locationRequest,
                    locationCallBack,
                    mainLooper
                )
            }
        }
    }
}

@Composable
private fun Screen(
    nearCategoryListViewModel: NearCategoryListViewModel,
    nearCategoryClickEvent: (CategoryItem) -> Unit,
    randomPlaceViewModel: RandomPlaceViewModel,
    placeClickEvent: (Place) -> Unit
) {
    val loading by nearCategoryListViewModel.isLoading

    val nearNavItem = NavItem(
        navScreen = MainNavScreen.Near
    ) {
        NearCategoryListScreen(
            categoryList = nearCategoryListViewModel.categoryItems,
            isLoading = loading,
            nearCategoryClickEvent = nearCategoryClickEvent
        )
    }

    val recentlyPlace by randomPlaceViewModel.recentlyPlace

    val randomNavItem = NavItem(
        navScreen = MainNavScreen.Random
    ) {
        RandomPlaceScreen(
            histories = randomPlaceViewModel.histories,
            recentlyPlace = recentlyPlace,
            onPlaceClickEvent = placeClickEvent,
            selectRandomPlaceEvent = randomPlaceViewModel::selectRandomPlace
        )
    }

    val favoriteNavItem = NavItem(
        navScreen = MainNavScreen.Favorite
    ) {

    }

    val infoNavItem = NavItem(
        navScreen = MainNavScreen.Info
    ) {

    }

    val screenList = listOf(
            nearNavItem,
            randomNavItem,
            favoriteNavItem,
            infoNavItem
        )

    MainScreen(screenList)
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    MainScreen(emptyList())
}