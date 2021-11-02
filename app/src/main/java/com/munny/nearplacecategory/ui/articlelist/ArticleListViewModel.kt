package com.munny.nearplacecategory.ui.articlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.munny.nearplacecategory.model.Place
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class ArticleListViewModel @AssistedInject constructor(
    @Assisted title: String,
    @Assisted placeList: List<Place>
) : ViewModel() {
    private val _title = MutableLiveData(title)
    val title: LiveData<String>
        get() = _title

    private val _placeList = MutableLiveData(placeList)
    val placeList: LiveData<List<Place>>
        get() = _placeList

    @Inject
    fun sortList() {
        _placeList.value = placeList.value?.sortedBy { it.distance }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(title: String, placeList: List<Place>): ArticleListViewModel
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