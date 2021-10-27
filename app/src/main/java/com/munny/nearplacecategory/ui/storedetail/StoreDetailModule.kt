package com.munny.nearplacecategory.ui.storedetail

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class StoreDetailModule {
    @Binds
    abstract fun bindStoreDetailRepository(
        storeDetailRepositoryImpl: StoreDetailRepositoryImpl
    ) : StoreDetailRepository
}