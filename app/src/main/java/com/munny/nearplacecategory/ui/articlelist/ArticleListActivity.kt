package com.munny.nearplacecategory.ui.articlelist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory._base.BaseActivity
import com.munny.nearplacecategory.databinding.ActivityArticleListBinding
import com.munny.nearplacecategory.extensions.toDistance
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.model.Place
import com.munny.nearplacecategory.ui.article.ArticleActivity
import com.munny.nearplacecategory.utils.GlideUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListActivity : BaseActivity<ActivityArticleListBinding>(
    R.layout.activity_article_list
) {
    private val vmFactory by lazy {
        val categoryItem = intent.extras?.getParcelable(EXTRA_CATEGORY_ITEM)
            ?: CategoryItem("", emptyList())

        ArticleListViewModel.getFactory(factory, categoryItem.categoryName, categoryItem.placeList)
    }

    @Inject
    lateinit var factory: ArticleListViewModel.AssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArticleListScreen()
        }
    }

    @Composable
    fun ArticleListScreen() {
        Surface {
            Column {
                Toolbar()
                Shadow()
                Contents()
            }
        }
    }

    @Composable
    fun Toolbar(
        vm: ArticleListViewModel = viewModel(factory = vmFactory)
    ) {
        val title by vm.title.observeAsState(initial = "")

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(56.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_backpress_black_24dp),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.text_black)
            )
        }
    }

    @Composable
    fun Shadow() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    brush = Brush.verticalGradient(
                        0f to Color(0x99AAAAAA),
                        1f to Color(0x22AAAAAA)
                    )
                )
        )
    }

    @Composable
    fun Contents(
        vm: ArticleListViewModel = viewModel(factory = vmFactory)
    ) {
        val placeList by vm.placeList.observeAsState(initial = emptyList())

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            flingBehavior = ScrollableDefaults.flingBehavior()
        ) {
            item { Spacer(modifier = Modifier.size(16.dp)) }
            item { CategoryList() }
            items(placeList) { article ->
                ArticlePreview(article) {
                    val intent = ArticleActivity.getIntent(
                        this@ArticleListActivity,
                        article
                    )

                    startActivity(intent)
                }
            }
        }
    }

    @Composable
    fun CategoryList() {
        val list = remember { mutableStateListOf("한식", "중식", "일식") }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list) { categoryItem ->
                Category(item = categoryItem)
            }
        }
    }

    @Composable
    fun Category(item: String) {
        Text(
            text = item,
            fontSize = 14.sp,
            modifier = Modifier
                .border(0.5.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }

    @Composable
    fun ArticlePreview(place: Place, onClickAction: () -> Unit) {
        val placeImage = remember {
            mutableStateOf<Bitmap>(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_restaurant_placeholder
                )
            )
        }

        place.articleImage?.url?.let { url ->
            GlideUtil.loadBitmap(
                this,
                url,
                { bitmap ->
                    placeImage.value = bitmap ?: return@loadBitmap
                }
            )
        }

        Column(
            modifier = Modifier
                .clickable(onClick = onClickAction)
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Image(
                bitmap = placeImage.value.asImageBitmap(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .aspectRatio(3 / 2f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = place.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.text_black)
            )
            Text(
                text = place.distance.toDistance(),
                fontSize = 12.sp,
                color = colorResource(R.color.text_gray)
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        ArticleListScreen()
    }

    companion object {
        const val EXTRA_CATEGORY_ITEM = "extra_category_item"
    }
}