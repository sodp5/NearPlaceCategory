package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap
import com.munny.nearplacecategory.model.ArticleImage

interface ArticleRepository {
    suspend fun getImage(query: String) : ArticleImage
    suspend fun getStaticMap(latitude: Double, longitude: Double, width: Int, height: Int): Bitmap
}