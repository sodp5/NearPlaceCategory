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
import com.munny.nearplacecategory.ui.shared.favorite.FavoriteRepository
import com.munny.nearplacecategory.usecase.GetAllPlaceIdUseCase
import com.munny.nearplacecategory.usecase.SwitchFavoriteUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class ArticleViewModel @AssistedInject constructor(
    @Assisted private val place: Place,
    private val articleRepository: ArticleRepository,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase,
    private val getAllPlaceIdUseCase: GetAllPlaceIdUseCase
) : ViewModel() {
    private val _articleInfoState = mutableStateOf(ArticleInfo())
    val articleInfoState: State<ArticleInfo>
        get() = _articleInfoState

    private val _staticMapImage = mutableStateOf<Bitmap>(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    val staticMapImage: State<Bitmap>
        get() = _staticMapImage

    private var currentPlace = place

    init {
        getStaticMap()
        initialize()
    }

    private fun initialize() {
        currentPlace.let {
            _articleInfoState.value = toArticleInfo(it)
        }
    }

    private fun toArticleInfo(place: Place) = ArticleInfo(
        name = place.name,
        placeUrl = place.placeUrl ?: "",
        categories = place.categories.joinToString(", "),
        phoneNumber = place.phone,
        distance = place.distance.toDistance(),
        isLiked = place.isLiked
    )

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

    fun switchFavorite() {
        viewModelScope.launch {
            currentPlace = switchFavoriteUseCase.switchFavorite(currentPlace)
            _articleInfoState.value = toArticleInfo(currentPlace)
        }
    }


    fun checkFavorite() {
        viewModelScope.launch {
            val ids = getAllPlaceIdUseCase.getAllPlaceId()

            currentPlace = currentPlace.run {
                if (ids.contains(id)) {
                    copy(isLiked = true)
                } else {
                    copy(isLiked = false)
                }
            }

            _articleInfoState.value = toArticleInfo(currentPlace)
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