package com.munny.nearplacecategory.ui.article

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityArticleBinding
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.utils.GlideUtil
import com.munny.nearplacecategory.values.Colors
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

    private val vmFactory by lazy {
        val place = intent?.extras?.getParcelable<Place>(EXTRA_PLACE)
            ?: throw Exception("need Place params!!")

        ArticleViewModel.getFactory(assistedFactory, place)
    }

    @Inject
    lateinit var assistedFactory: ArticleViewModel.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArticleScreen()
        }
    }

    @Composable
    fun ArticleScreen() {
        Column {
            PlaceImage()
            Contents()
        }
    }

    @Composable
    fun PlaceImage(
        vm: ArticleViewModel = viewModel(factory = vmFactory)
    ) {
        val articleImage by vm.articleImage.observeAsState()
        val placeImage = remember {
            mutableStateOf<Bitmap?>(null)
        }

        GlideUtil.loadBitmap(
            this,
            articleImage?.url,
            {
                placeImage.value = it
            }
        )

        articleImage?.url.isNullOrEmpty().let {
            if (it) {
                return@let
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3 / 2f)
            ) {
                placeImage.value?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )
                }
            }
        }

    }

    @Composable
    fun Contents() {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            PlaceInfo()
            Spacer(modifier = Modifier.size(20.dp))
            Divider()
            Spacer(modifier = Modifier.size(16.dp))
            PlaceMap()
        }
    }

    @Composable
    fun PlaceInfo(
        vm: ArticleViewModel = viewModel(factory = vmFactory)
    ) {
        val placeName by vm.articleName.observeAsState("")
        val categories by vm.categories.observeAsState("")
        val phoneNumber by vm.phoneNumber.observeAsState("")
        val distance by vm.distance.observeAsState("")

        Text(
            text = placeName,
            color = Colors.TextBlack,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = categories,
            color = Colors.TextGray,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = phoneNumber,
                color = Colors.TextGray,
                fontSize = 12.sp
            )
            Text(
                text = distance,
                color = Colors.TextGray,
                fontSize = 12.sp
            )
        }
    }

    @Composable
    fun Divider() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(color = Colors.Gray)
        )
    }

    @Composable
    fun PlaceMap(
        vm: ArticleViewModel = viewModel(factory = vmFactory)
    ) {
        val emptyBitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888)
        val map by vm.staticMapImage.observeAsState(emptyBitmap)

        Image(
            bitmap = map.asImageBitmap(),
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 2f)
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        ArticleScreen()
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