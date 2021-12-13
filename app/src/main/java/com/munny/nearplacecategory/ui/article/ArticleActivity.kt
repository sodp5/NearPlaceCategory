package com.munny.nearplacecategory.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityArticleBinding
import com.munny.nearplacecategory.model.ArticleImage
import com.munny.nearplacecategory.model.ArticleInfo
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
        setContent {
            Screen(vm)
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

@Composable
private fun Screen(articleViewModel: ArticleViewModel) {
    val articleImage by articleViewModel.articleImage.observeAsState(ArticleImage.Empty)
    val articleInfo by articleViewModel.articleInfoState.observeAsState(ArticleInfo())
    val placeMap by articleViewModel.staticMapImage.observeAsState()

    ArticleScreen(articleImage, articleInfo, placeMap)
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreen() {
    ArticleScreen(ArticleImage.Empty, ArticleInfo(), null)
}