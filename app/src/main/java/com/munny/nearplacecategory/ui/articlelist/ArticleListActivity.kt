package com.munny.nearplacecategory.ui.articlelist

import android.os.Bundle
import androidx.activity.viewModels
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityArticleListBinding
import com.munny.nearplacecategory.extensions.startActivity
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.ui.article.ArticleActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListActivity : BaseActivity<ActivityArticleListBinding>(R.layout.activity_article_list) {
    private val vm: ArticleListViewModel by viewModels {
        val categoryItem = intent.extras?.getParcelable<CategoryItem>(EXTRA_CATEGORY_ITEM)
            ?: throw Exception("need CategoryItem params!!")

        ArticleListViewModel.getFactory(factory, categoryItem.categoryName, categoryItem.placeList)
    }

    @Inject
    lateinit var factory: ArticleListViewModel.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm

        binding.rvStoreList.adapter = ArticleListAdapter { place ->
            startActivity<ArticleActivity>(Bundle().apply {
                putParcelable(ArticleActivity.EXTRA_PLACE, place)
            })
        }
    }

    companion object {
        const val EXTRA_CATEGORY_ITEM = "extra_category_item"
    }
}