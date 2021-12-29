package com.munny.nearplacecategory.di

import android.content.Context
import com.munny.nearplacecategory.utils.PreferenceKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideNpcPreference(
        @ApplicationContext context: Context
    ) = context.getSharedPreferences(PreferenceKey.PREF_NAME, Context.MODE_PRIVATE)
}