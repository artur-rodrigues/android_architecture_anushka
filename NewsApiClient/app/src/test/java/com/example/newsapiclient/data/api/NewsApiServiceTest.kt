package com.example.newsapiclient.data.api

import com.example.newsapiclient.data.model.APIResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsApiServiceTest {

    private lateinit var service: NewsApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeaderLines_sentRequest_receivedExpected() {
        runBlocking {
            val response = getResponse()
            val request = server.takeRequest()
            assertThat(response).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=d83900a30c5644bbacab7c0505a32a57")
        }
    }

    @Test
    fun getTopHeaderLines_receivedExpected_correctPageSize() {
        runBlocking {
            val articles = getResponse()!!.articles
            assertThat(articles.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeaderLines_receivedExpected_correctContent() {
        runBlocking {
            val articles = getResponse()!!.articles
            val article = articles[0]
            assertThat(article.author).isEqualTo("Lucia Mutikani")
            assertThat(article.url).isEqualTo("https://www.reuters.com/world/us/us-weekly-jobless-claims-drop-below-400000-2021-06-03/")
            assertThat(article.publishedAt).isEqualTo("2021-06-03T14:14:00Z")
        }
    }

    private suspend fun getResponse(): APIResponse? {
        enqueueMockResponse("newsresponse.json")
        return service.getTopHeadlines("us", 1).body()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

}