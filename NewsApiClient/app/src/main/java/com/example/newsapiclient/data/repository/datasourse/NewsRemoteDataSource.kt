package com.example.newsapiclient.data.repository.datasourse

import com.example.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.Query

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>

    suspend fun getSearchedTopHeadlines(country: String, searchQuery: String, page: Int): Response<APIResponse>
}