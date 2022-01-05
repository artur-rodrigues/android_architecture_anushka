package com.example.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.example.newsapiclient.utils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val context: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
) : AndroidViewModel(context) {

    private val newsHeadlinesMutable: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    val newsHeadlinesLiveData: LiveData<Resource<APIResponse>> = newsHeadlinesMutable

    private val searchedNewsMutable: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    val searchedNewsLiveData: LiveData<Resource<APIResponse>> = searchedNewsMutable

    fun getNewsHeadlines(country: String, page: Int) {
        execute(newsHeadlinesMutable) {
            getNewsHeadlinesUseCase.execute(country, page)
        }
    }

    fun searchNews(country: String, searchQuery: String, page: Int) {
        execute(searchedNewsMutable) {
            getSearchedNewsUseCase.execute(country, searchQuery, page)
        }
    }

    private fun <T> execute(mutableLiveData: MutableLiveData<Resource<T>>, executable: suspend () -> Resource<T>) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveData.postValue(Resource.Loading())
            try {
                if (context.isNetworkAvailable()) {
                    val apiResult = executable()
                    mutableLiveData.postValue(apiResult)
                } else {
                    mutableLiveData.postValue(Resource.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                mutableLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
}