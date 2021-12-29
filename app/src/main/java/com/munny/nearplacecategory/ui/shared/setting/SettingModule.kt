package com.munny.nearplacecategory.ui.shared.setting

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SettingModule {
    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository
}