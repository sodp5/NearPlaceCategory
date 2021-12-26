package com.munny.nearplacecategory.ui.articlelist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.munny.nearplacecategory.extensions.ifFalse
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.article.ArticleActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListActivity : AppCompatActivity() {
    private val vm: ArticleListViewModel by viewModels {
        val categoryItem = intent.extras?.getParcelable(EXTRA_CATEGORY_ITEM)
            ?: CategoryItem("", emptyList())

        ArticleListViewModel.getFactory(factory, categoryItem.categoryName, categoryItem.placeList)
    }

    @Inject
    lateinit var factory: ArticleListViewModel.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen(
                articleListViewModel = vm,
                backPressedEvent = { finish() }
            ) {
                val intent = ArticleActivity.getIntent(this, it)

                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        vm.removeLastCategory().ifFalse {
            super.onBackPressed()
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ITEM = "extra_category_item"
    }
}

@Composable
private fun Screen(
    articleListViewModel: ArticleListViewModel,
    backPressedEvent: () -> Unit,
    itemClickEvent: (Place) -> Unit
) {
    val title by articleListViewModel.title

    ArticleListScreen(
        title = title,
        placeList = articleListViewModel.placeList,
        backPressedEvent = backPressedEvent,
        itemClickEvent = itemClickEvent,
        categories = articleListViewModel.categoryList,
        categoryClickEvent = articleListViewModel::selectCategory,
        onLikeClickEvent = articleListViewModel::switchFavorite
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    ArticleListScreen(
        title = "한식",
        placeList = emptyList(),
        backPressedEvent = { },
        itemClickEvent = { },
        categories = emptyList(),
        categoryClickEvent = { },
        onLikeClickEvent = { }
    )
}