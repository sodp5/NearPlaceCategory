package com.munny.nearplacecategory.ui.article

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ArticleModule {
    @Binds
    abstract fun bindStoreDetailRepository(
        storeDetailRepositoryImpl: ArticleRepositoryImpl
    ) : ArticleRepository
}