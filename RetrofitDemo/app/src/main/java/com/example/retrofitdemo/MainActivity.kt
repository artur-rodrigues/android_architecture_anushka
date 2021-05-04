package com.example.retrofitdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.retrofitdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val retService: AlbumService =
        RetrofitInstance.getInstance().create(AlbumService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        uploadAlbum()
//        getRequestWithPathParameter()
//        getRequestWithQueryParameter()
    }

    private fun uploadAlbum() {
        val album = AlbumsItem(0, "My title", 101)

        val postResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }

        observe(postResponse) {
            binding.titles.text = "Album title: ${it.title}"
        }
    }

    private fun getRequestWithPathParameter() {
        val responseLiveDataItem: LiveData<Response<AlbumsItem>> = liveData {
            val response = retService.getAlbum(3)
            emit(response)
        }

        observe(responseLiveDataItem) { albumsItem ->
            toast(albumsItem.title)
        }
    }

    private fun getRequestWithQueryParameter() {
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getSortedAlbums(3)
            emit(response)
        }

        observe(responseLiveData) {
            var titles = ""
            it.forEach { item ->
                titles += "Album title: ${item.title} \n"
            }
            binding.titles.text = titles
        }
    }

    private fun <T> observe(liveData: LiveData<Response<T>>, execute: (T) -> Unit) {
        liveData.observe(this) {
            it.body()?.let { executable ->
                execute(executable)
            }
        }
    }

    private fun AppCompatActivity.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}