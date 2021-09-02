package com.munny.nearplacecategory.ui.nearcategorylist

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.gun0912.tedpermission.coroutine.TedPermission
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityNearCategoryListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class NearCategoryListActivity : BaseActivity<ActivityNearCategoryListBinding>(
    R.layout.activity_near_category_list
) {
    private val locationProvider by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }
    private val vm: NearCategoryListViewModel by viewModels()

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