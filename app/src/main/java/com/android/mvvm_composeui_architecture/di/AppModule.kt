package com.android.mvvm_composeui_architecture.di

import com.android.mvvm_composeui_architecture.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideListRepository(): ListRepository {
        return ListRepository()
    }
}