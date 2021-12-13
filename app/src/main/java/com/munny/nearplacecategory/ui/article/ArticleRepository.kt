package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap

interface ArticleRepository {
    suspend fun getStaticMap(latitude: Double, longitude: Double, width: Int, height: Int): Bitmap
}