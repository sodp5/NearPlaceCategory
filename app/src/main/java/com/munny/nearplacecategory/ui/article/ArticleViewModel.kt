package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.munny.nearplacecategory.extensions.toDistance
import com.munny.nearplacecategory.model.ArticleInfo
import com.munny.nearplacecategory.model.Place
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class ArticleViewModel @AssistedInject constructor(
    private val articleRepository: ArticleRepository,
    @Assisted private val place: Place
) : ViewModel() {
    private val _articleInfoState = mutableStateOf(ArticleInfo())
    val articleInfoState: State<ArticleInfo>
        get() = _articleInfoState

    private val _staticMapImage = mutableStateOf<Bitmap>(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    val staticMapImage: State<Bitmap>
        get() = _staticMapImage

    init {
        getStaticMap()
        initialize()
    }

    private fun initialize() {
        place.let {
            _articleInfoState.value = ArticleInfo(
                name = it.name,
                placeUrl = it.placeUrl ?: "",
                categories = it.categories.joinToString(", "),
                phoneNumber = it.phone,
                distance = it.distance.toDistance()
            )
        }
    }

    private fun getStaticMap() {
        viewModelScope.launch {
            place.let {
                val result = articleRepository.getStaticMap(
                    it.latitude,
                    it.longitude,
                    600,
                    400
                )

                _staticMapImage.value = result
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(place: Place): ArticleViewModel
    }

    companion object {
        fun getFactory(
            assistedFactory: AssistedFactory,
            place: Place
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return assistedFactory.create(place) as T
                }
            }
        }
    }
}