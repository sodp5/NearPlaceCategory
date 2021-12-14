package com.munny.nearplacecategory.ui.nearcategorylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.munny.nearplacecategory.R
import com.munny.nearplacecategory.model.CategoryItem
import com.munny.nearplacecategory.ui.article.Divider
import com.munny.nearplacecategory.values.Colors

@Composable
fun NearCategoryListScreen(
    categoryList: List<CategoryItem>,
    isLoading: Boolean,
    nearCategoryClickEvent: (CategoryItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CategoryList(
            categoryList = categoryList,
            nearCategoryClickEvent = nearCategoryClickEvent
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CategoryList(
    categoryList: List<CategoryItem>,
    nearCategoryClickEvent: (CategoryItem) -> Unit
) {
    LazyColumn {
        items(categoryList) { category ->
            CategoryListItem(
                categoryName = category.categoryName,
                categoryCount = category.placeList.size
            ) {
                nearCategoryClickEvent.invoke(category)
            }
        }
    }
}

@Composable
fun CategoryListItem(
    categoryName: String,
    categoryCount: Int,
    onClickEvent: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .height(100.dp)
            .clickable { onClickEvent.invoke() }
            .padding(horizontal = 16.dp)
    ) {
        val (categoryRef, countRef, dividerRef) = createRefs()

        createVerticalChain(
            categoryRef,
            countRef,
            chainStyle = ChainStyle.Packed
        )

        Text(
            text = categoryName,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier
                .constrainAs(categoryRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(countRef.top)
                }
        )
        Text(
            text = stringResource(R.string.item_near_category_list_count, categoryCount),
            fontSize = 14.sp,
            color = Colors.TextGray,
            modifier = Modifier
                .constrainAs(countRef) {
                    top.linkTo(categoryRef.bottom, margin = 4.dp)
                    bottom.linkTo(parent.bottom)
                }
        )
        Divider(
            modifier = Modifier
                .constrainAs(dividerRef) {
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}