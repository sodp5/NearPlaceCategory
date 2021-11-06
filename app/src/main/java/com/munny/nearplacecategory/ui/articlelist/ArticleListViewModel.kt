package com.munny.nearplacecategory.ui.articlelist

import androidx.lifecycle.*
import com.munny.nearplacecategory.extensions.map
import com.munny.nearplacecategory.model.Place
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class ArticleListViewModel @AssistedInject constructor(
    @Assisted private val orgCategoryName: String,
    @Assisted private val orgPlaceList: List<Place>
) : ViewModel() {
    private val _title = MutableLiveData(orgCategoryName)
    val title: LiveData<String>
        get() = _title

    private val _currCategory = MutableLiveData<String>()
    val currCategory: LiveData<String>
        get() = _currCategory

    private val _placeList = MutableLiveData(orgPlaceList)
    val placeList: LiveData<List<Place>>
        get() = _placeList

    val categoryList: LiveData<List<String>>
        get() = Transformations.map(placeList) {
            it.associateBy { place ->
                place.categories.getOrElse(depth) { "" }
            }.keys
                .filter { key -> key.isNotEmpty() }
                .toList()
        }

    private val currCategoryList = arrayListOf(orgCategoryName)

    private var depth = 1

    @Inject
    fun sortList() {
        _placeList.value = placeList.value?.sortedBy { it.distance }
    }

    fun selectCategory(categoryName: String) {
        currCategoryList.add(categoryName)

        var newPlaceList = orgPlaceList

        currCategoryList.forEach { category ->
            newPlaceList = newPlaceList.filter { place ->
                place.categories.contains(category)
            }
        }

        _placeList.value = newPlaceList
        _currCategory.value = currCategoryList.joinToString(">")

        depth++
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