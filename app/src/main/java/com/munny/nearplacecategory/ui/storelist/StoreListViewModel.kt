package com.munny.nearplacecategory.ui.storelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.munny.nearplacecategory.model.Place
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

class StoreListViewModel @AssistedInject constructor(
    @Assisted title: String,
    @Assisted placeList: List<Place>
) : ViewModel() {
    private val _title = MutableLiveData(title)
    val title: LiveData<String>
        get() = _title

    private val _placeList = MutableLiveData(placeList)
    val placeList: LiveData<List<Place>>
        get() = _placeList

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(title: String, placeList: List<Place>): StoreListViewModel
    }

    companion object {
        fun getFactory(
            factory: AssistedFactory,
            title: String,
            placeList: List<Place>
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return factory.create(title, placeList) as T
                }
            }
        }
    }
}