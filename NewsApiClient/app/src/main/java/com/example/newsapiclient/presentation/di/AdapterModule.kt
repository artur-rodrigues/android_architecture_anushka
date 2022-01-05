package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }
}