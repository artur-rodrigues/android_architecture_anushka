package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.domain.repository.NewsRepository
import com.example.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesNewsHeadLinesUseCase(repository: NewsRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetSearchedNewsUseCase(repository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(repository)
    }
}