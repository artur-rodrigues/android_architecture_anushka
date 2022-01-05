package com.example.newsapiclient.data.repository.datasourseimpl

import com.example.newsapiclient.data.api.NewsApiService
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.repository.datasourse.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService,
) : NewsRemoteDataSource {
    
    override suspend fun getTopHeadlines(country: String, page: Int) = newsApiService.getTopHeadlines(country, page)

    override suspend fun getSearchedTopHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ) = newsApiService.getSearchedTopHeadlines(country, searchQuery, page)
}