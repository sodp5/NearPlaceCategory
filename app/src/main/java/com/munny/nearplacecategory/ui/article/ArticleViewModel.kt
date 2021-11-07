package com.munny.nearplacecategory.ui.article

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.munny.nearplacecategory.extensions.map
import com.munny.nearplacecategory.extensions.toDistance
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.model.ArticleImage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleViewModel @AssistedInject constructor(
    private val articleRepository: ArticleRepository,
    @Assisted place: Place
) : ViewModel() {
    private val _place = MutableLiveData(place)
    val place: LiveData<Place>
        get() = _place

    val articleImage: LiveData<ArticleImage>
        get() = _place.map {
            it.articleImage ?: ArticleImage.Empty
        }

    val articleName: LiveData<String>
        get() = _place.map {
            it.name
        }

    val categories: LiveData<String>
        get() = _place.map {
            it.categories.joinToString(", ")
        }

    val phoneNumber: LiveData<String>
        get() = _place.map {
            it.phone
        }

    val distance: LiveData<String>
        get() = _place.map {
            it.distance.toDistance()
        }

    private val _staticMapImage = MutableLiveData<Bitmap>()
    val staticMapImage: LiveData<Bitmap>
        get() = _staticMapImage

    init {
        getStaticMap()
    }

    private fun getStaticMap() {
        viewModelScope.launch {
            place.value?.let {
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