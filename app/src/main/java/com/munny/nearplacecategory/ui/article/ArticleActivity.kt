package com.munny.nearplacecategory.ui.article

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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

        binding.ivMap.run {
            post {
                vm.getStaticMap(width, height)
            }
        }
    }

    companion object {
        private const val EXTRA_PLACE = "extra_place"

        fun getIntent(context: Context, place: Place): Intent {
            return Intent(context, ArticleActivity::class.java).apply {
                putExtra(EXTRA_PLACE, place)
            }
        }
    }
}