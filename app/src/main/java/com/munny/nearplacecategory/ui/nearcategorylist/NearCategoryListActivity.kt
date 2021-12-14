package com.munny.nearplacecategory.ui.nearcategorylist

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import com.google.android.gms.location.*
import com.gun0912.tedpermission.coroutine.TedPermission
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityNearCategoryListBinding
import com.munny.nearplacecategory.extensions.startActivity
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.ui.articlelist.ArticleListActivity
import com.munny.nearplacecategory.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NearCategoryListActivity : BaseActivity<ActivityNearCategoryListBinding>(
    R.layout.activity_near_category_list
) {
    private val locationProvider by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }
    private val vm: NearCategoryListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                nearCategoryListViewModel = vm,
                nearCategoryClickEvent = {
                    vm.setupArticleImages(it)
                }
            )
        }

        vm.selectCategoryEvent.observeEvent(this) {
            startActivity<ArticleListActivity>(Bundle().apply {
                putParcelable(ArticleListActivity.EXTRA_CATEGORY_ITEM, it)
            })
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

                        vm.setLatLng(location.latitude, location.longitude)
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
    nearCategoryClickEvent: (CategoryItem) -> Unit
) {
    val loading by nearCategoryListViewModel.isLoading

    NearCategoryListScreen(
        categoryList = nearCategoryListViewModel.categoryItems,
        isLoading = loading,
        nearCategoryClickEvent = nearCategoryClickEvent
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    NearCategoryListScreen(
        categoryList = emptyList(),
        isLoading = true,
        nearCategoryClickEvent = { }
    )
}