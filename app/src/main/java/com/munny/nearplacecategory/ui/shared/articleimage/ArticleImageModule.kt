package com.munny.nearplacecategory.ui.shared.articleimage

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class ArticleImageModule {
    @Binds
    abstract fun bindArticleImageRepository(
        articleImageRepositoryImpl: ArticleImageRepositoryImpl
    ): ArticleImageRepository
}