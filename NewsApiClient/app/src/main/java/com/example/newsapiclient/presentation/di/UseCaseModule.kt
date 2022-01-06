package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.domain.repository.NewsRepository
import com.example.newsapiclient.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    @Singleton
    fun providesSaveNewsUseCase(repository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetSavedNewsUseCase(repository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDeleteSavedNewsUseCase(repository: NewsRepository): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(repository)
    }
}