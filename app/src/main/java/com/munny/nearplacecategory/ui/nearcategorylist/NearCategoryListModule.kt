package com.munny.nearplacecategory.ui.nearcategorylist

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NearCategoryListModule {
    @Binds
    abstract fun bindNearCategoryRepository(
        nearCategoryListRepositoryImpl: NearCategoryListRepositoryImpl
    ): NearCategoryListRepository
}