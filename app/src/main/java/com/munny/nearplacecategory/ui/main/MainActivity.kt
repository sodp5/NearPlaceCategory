package com.munny.nearplacecategory.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Moving
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
import com.munny.nearplacecategory.ui.main.favorite.FavoriteScreen
import com.munny.nearplacecategory.ui.main.favorite.FavoriteViewModel
import com.munny.nearplacecategory.ui.main.myinfo.MenuItem
import com.munny.nearplacecategory.ui.main.myinfo.MyInfoScreen
import com.munny.nearplacecategory.ui.main.myinfo.MyInfoViewModel
import com.munny.nearplacecategory.ui.main.random.RandomPlaceScreen
import com.munny.nearplacecategory.ui.main.random.RandomPlaceViewModel
import com.munny.nearplacecategory.ui.main.nearcategorylist.NearCategoryListScreen
import com.munny.nearplacecategory.ui.main.nearcategorylist.NearCategoryListViewModel
import com.munny.nearplacecategory.ui.setting.SettingActivity
import com.munny.nearplacecategory.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val nearCategoryListViewModel: NearCategoryListViewModel by viewModels()
    private val randomPlaceViewModel: RandomPlaceViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val myInfoViewModel: MyInfoViewModel by viewModels()

    private val locationProvider by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val menuItems: List<MenuItem> by lazy {
        listOf(
            MenuItem(
                itemName = "내 주변 반경 설정",
                itemIcon = Icons.Default.Moving,
                onItemClick = {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                }
            ),
            MenuItem(
                itemName = "앱 버전",
                itemIcon = Icons.Default.Info,
                onItemClick = {
                    Toast.makeText(this, "1.0.0", Toast.LENGTH_SHORT).show()
                }
            )
        )
    }

    @SuppressLint("MissingPermission")
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
                },
                favoriteViewModel = favoriteViewModel,
                myInfoViewModel = myInfoViewModel,
                menuItems = menuItems,
                refreshMyLocation = {
                    locationProvider.lastLocation.addOnSuccessListener {
                        myInfoViewModel.getMyLocation(
                            longitude = it.longitude,
                            latitude = it.latitude
                        )
                    }
                }
            )
        }

        observeViewModel()
    }

    override fun onStart() {
        super.onStart()

        enableLocationListener()
    }

    private fun observeViewModel() {
        nearCategoryListViewModel.selectCategoryEvent.observeEvent(this) {
            startActivity<ArticleListActivity>(Bundle().apply {
                putParcelable(ArticleListActivity.EXTRA_CATEGORY_ITEM, it)
            })
        }

        nearCategoryListViewModel.searchPoiEvent.observeEvent(this) {
            randomPlaceViewModel.setAllPlace(it)
        }

        randomPlaceViewModel.toastMessage.observeEvent(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
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
                    private var isFirstFind = true

                    override fun onLocationResult(result: LocationResult) {
                        if (!isFirstFind) {
                            return
                        }
                        isFirstFind = false

                        val location = result.lastLocation

                        nearCategoryListViewModel.setLatLng(location.latitude, location.longitude)
                        myInfoViewModel.getMyLocation(location.longitude, location.latitude)
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
    placeClickEvent: (Place) -> Unit,
    favoriteViewModel: FavoriteViewModel,
    myInfoViewModel: MyInfoViewModel,
    menuItems: List<MenuItem>,
    refreshMyLocation: () -> Unit
) {
    val nearNavItem = getNearCategoryListNavItem(
        nearCategoryListViewModel = nearCategoryListViewModel,
        nearCategoryClickEvent = nearCategoryClickEvent
    )

    val randomNavItem = getRandomPlaceNavItem(
        randomPlaceViewModel = randomPlaceViewModel,
        placeClickEvent = placeClickEvent
    )

    val favoriteNavItem = getFavoriteNavItem(
        favoriteViewModel = favoriteViewModel,
        placeClickEvent = placeClickEvent
    )

    val infoNavItem = getMyInfoNavItem(
        myInfoViewModel = myInfoViewModel,
        menuItems = menuItems,
        refreshMyLocation = refreshMyLocation
    )

    val screenList = listOf(
        nearNavItem,
        randomNavItem,
        favoriteNavItem,
        infoNavItem
    )

    MainScreen(screenList)
}

private fun getNearCategoryListNavItem(
    nearCategoryListViewModel: NearCategoryListViewModel,
    nearCategoryClickEvent: (CategoryItem) -> Unit,
) = NavItem(
    navScreen = MainNavScreen.Near
) {
    val isLoading by nearCategoryListViewModel.isLoading

    NearCategoryListScreen(
        categoryList = nearCategoryListViewModel.categoryItems,
        isLoading = isLoading,
        nearCategoryClickEvent = nearCategoryClickEvent
    )
}

private fun getRandomPlaceNavItem(
    randomPlaceViewModel: RandomPlaceViewModel,
    placeClickEvent: (Place) -> Unit
) = NavItem(
    navScreen = MainNavScreen.Random,
    onTabSelected = randomPlaceViewModel::setupFavoritePlaceIds
) {
    val recentlyPlace by randomPlaceViewModel.recentlyPlace
    val isLoading by randomPlaceViewModel.isLoading

    RandomPlaceScreen(
        histories = randomPlaceViewModel.histories,
        recentlyPlace = recentlyPlace,
        isLoading = isLoading,
        onPlaceClickEvent = placeClickEvent,
        onLikeClickEvent = randomPlaceViewModel::switchFavorite,
        selectRandomPlaceEvent = randomPlaceViewModel::selectRandomPlace,
        onRefreshEvent = randomPlaceViewModel::onRefresh
    )
}

private fun getFavoriteNavItem(
    favoriteViewModel: FavoriteViewModel,
    placeClickEvent: (Place) -> Unit
) = NavItem(
    navScreen = MainNavScreen.Favorite,
    onTabSelected = favoriteViewModel::getAllPlace
) {
    FavoriteScreen(
        places = favoriteViewModel.favoritePlaces,
        onItemClickEvent = placeClickEvent,
        onLikeClickEvent = favoriteViewModel::switchFavorite
    )
}

private fun getMyInfoNavItem(
    myInfoViewModel: MyInfoViewModel,
    menuItems: List<MenuItem>,
    refreshMyLocation: () -> Unit
) = NavItem(
    onTabSelected = {
        myInfoViewModel.setRefreshEnabled(true)
    },
    navScreen = MainNavScreen.MyInfo
) {
    val location by myInfoViewModel.myLocation
    val refreshEnabled by myInfoViewModel.refreshEnabled

    MyInfoScreen(
        location = location,
        menuItems = menuItems,
        locationRefreshEnabled = refreshEnabled,
        locationRefreshEvent = {
            refreshMyLocation.invoke()
            myInfoViewModel.setRefreshEnabled(false)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    MainScreen(emptyList())
}