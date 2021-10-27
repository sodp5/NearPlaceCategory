package com.munny.nearplacecategory.ui.storedetail

import androidx.lifecycle.*
import com.munny.nearplacecategory.api.NaverSearchApi
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.model.StoreImage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class StoreDetailViewModel @AssistedInject constructor(
    private val storeDetailRepository: StoreDetailRepository,
    @Assisted private val place: Place
) : ViewModel() {
    private val _image = MutableLiveData<StoreImage>()
    val image: LiveData<StoreImage>
        get() = _image

    init {
        searchImage(place.name)
    }

    private fun searchImage(query: String) {
        viewModelScope.launch {
            val result = storeDetailRepository.getImage(query)

            _image.value = result
        }
    }

    fun getStaticMap(screenWidth: Int, screenHeight: Int) {
        viewModelScope.launch {
//            val result = storeDetailRepository.getStaticMap(
//                place.latitude,
//                place.longitude,
//                screenWidth,
//                screenHeight
//            )
//
//            Timber.d(result.toString())
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(place: Place): StoreDetailViewModel
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