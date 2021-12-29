package com.munny.nearplacecategory.ui.main.myinfo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MyInfoModule {
    @Binds
    abstract fun bindMyInfoRepository(
        myInfoRepositoryImpl: MyInfoRepositoryImpl
    ): MyInfoRepository
}