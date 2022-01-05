package com.example.newsapiclient.domain.usecase

import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.domain.repository.NewsRepository

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(article: Article) = newsRepository.getSavedNews()
}