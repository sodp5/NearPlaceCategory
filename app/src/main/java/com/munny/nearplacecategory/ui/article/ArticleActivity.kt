package com.munny.nearplacecategory.ui.article

import android.os.Bundle
import androidx.activity.viewModels
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityArticleBinding
import com.munny.nearplacecategory.model.Place
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleActivity : BaseActivity<ActivityArticleBinding>(
    R.layout.activity_article
) {
    private val vm: ArticleViewModel by viewModels {
        val place = intent?.extras?.getParcelable<Place>(EXTRA_PLACE)
            ?: throw Exception("need Place params!!")

        ArticleViewModel.getFactory(assistedFactory, place)
    }

    @Inject
    lateinit var assistedFactory: ArticleViewModel.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = vm

        vm.getStaticMap(500, 500)
    }

    companion object {
        const val EXTRA_PLACE = "extra_place"
    }
}