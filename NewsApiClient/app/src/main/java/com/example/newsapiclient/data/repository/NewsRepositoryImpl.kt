package com.example.newsapiclient.data.repository

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.repository.datasourse.NewsRemoteDataSource
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val dataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsHeadline(country: String, page: Int): Resource<APIResponse> {
        return Resource.convertResponseToResource(dataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<APIResponse> {
        return Resource.convertResponseToResource(dataSource.getSearchedTopHeadlines(country, searchQuery, page))
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}