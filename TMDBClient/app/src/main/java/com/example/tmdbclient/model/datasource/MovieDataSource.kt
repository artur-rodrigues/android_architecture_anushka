package com.example.tmdbclient.model.datasource

import androidx.paging.PageKeyedDataSource
import com.example.tmdbclient.BuildConfig
import com.example.tmdbclient.model.entity.Movie
import com.example.tmdbclient.model.entity.MovieDBResponse
import com.example.tmdbclient.model.service.MovieDataService
import kotlinx.coroutines.*
import retrofit2.Response

typealias Error = (Throwable) -> Unit
typealias Result<T> = (T) -> Unit

@Suppress("UNCHECKED_CAST", "ThrowableNotThrown")
class MovieDataSource(private val service: MovieDataService):
    PageKeyedDataSource<Long, Movie>() {

    override fun loadInitial(params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Movie>) {
        execute<MutableList<Movie>>(
            1,
            {callback.onResult(it, null, 2)},
            {callback.onError(it)})
    }

    override fun loadAfter(params: LoadParams<Long>,
                           callback: LoadCallback<Long, Movie>) {
        execute<MutableList<Movie>>(
            params.key,
            {callback.onResult(it, params.key + 1)},
            {callback.onError(it)})
    }

    override fun loadBefore(params: LoadParams<Long>,
                            callback: LoadCallback<Long, Movie>) {
        execute<MutableList<Movie>>(
            params.key,
            {callback.onResult(it, params.key - 1)},
            {callback.onError(it)})
    }

    private fun <T> execute(page: Long, result: Result<T>, error: Error) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            error(throwable)
        }

        CoroutineScope(Dispatchers.Main).launch(handler) {
            var response: Response<MovieDBResponse>? = null

            withContext(Dispatchers.IO) {
                response = service.getPagedPopularMovie(BuildConfig.API_KEY, page)
            }

            response?.let {
                if(it.isSuccessful) {
                    it.body()?.results.let {movies ->
                        result(movies as T)
                    }
                } else {
                    error(Exception("Service returned code ${it.code()}"))
                }

            }
        }
    }
}