package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.api.NewsApiService
import com.example.newsapiclient.data.repository.datasourse.NewsRemoteDataSource
import com.example.newsapiclient.data.repository.datasourseimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsApiService)
    }
}