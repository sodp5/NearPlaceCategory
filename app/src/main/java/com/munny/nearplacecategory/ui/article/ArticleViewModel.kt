package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.model.ArticleImage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import timber.log.Timber

class ArticleViewModel @AssistedInject constructor(
    private val articleRepository: ArticleRepository,
    @Assisted private val place: Place
) : ViewModel() {
    private val _image = MutableLiveData<ArticleImage>()
    val image: LiveData<ArticleImage>
        get() = _image

    private val _staticMapImage = MutableLiveData<Bitmap>()
    val staticMapImage: LiveData<Bitmap>
        get() = _staticMapImage

    init {
        _image.value = place.articleImage
    }

    fun getStaticMap(screenWidth: Int, screenHeight: Int) {
        viewModelScope.launch {
            val result = articleRepository.getStaticMap(
                place.latitude,
                place.longitude,
                screenWidth,
                screenHeight
            )

            _staticMapImage.value = result
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