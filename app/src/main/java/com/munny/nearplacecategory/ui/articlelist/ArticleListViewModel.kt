package com.munny.nearplacecategory.ui.articlelist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.*
import com.munny.nearplacecategory.extensions.ifFalse
import com.munny.nearplacecategory.extensions.ifTrue
import com.munny.nearplacecategory.extensions.map
import com.munny.nearplacecategory.model.Place
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class ArticleListViewModel @AssistedInject constructor(
    @Assisted private val orgCategoryName: String,
    @Assisted private val orgPlaceList: List<Place>
) : ViewModel() {
    private val _title = mutableStateOf(orgCategoryName)
    val title: State<String>
        get() = _title

    private val _currCategory = mutableStateOf("")
    val currCategory: State<String>
        get() = _currCategory

    private val _placeList = mutableStateListOf<Place>().apply {
        addAll(orgPlaceList)
    }
    val placeList: SnapshotStateList<Place>
        get() = _placeList

    val categoryList: SnapshotStateList<String>
        get() = placeList.associateBy { place ->
                place.categories.getOrElse(depth) { "" }
            }.keys
                .filter { key -> key.isNotEmpty() }
                .toMutableStateList()

    private val currCategoryList = arrayListOf(orgCategoryName)

    private var depth = 1

    @Inject
    fun sortList() {
        _placeList.sortedBy {
            it.distance
        }
    }

    fun selectCategory(categoryName: String) {
        currCategoryList.add(categoryName)

        refreshPlaceList()

        depth++
    }

    fun removeLastCategory(): Boolean {
        return (currCategoryList.size > 1).ifTrue {
            currCategoryList.removeLast()

            refreshPlaceList()

            depth--
        }
    }

    private fun refreshPlaceList() {
        var newPlaceList = orgPlaceList

        currCategoryList.forEach { category ->
            newPlaceList = newPlaceList.filter { place ->
                place.categories.contains(category)
            }
        }

        _placeList.run {
            clear()
            addAll(newPlaceList)
        }
        _currCategory.value = currCategoryList.joinToString(">")
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