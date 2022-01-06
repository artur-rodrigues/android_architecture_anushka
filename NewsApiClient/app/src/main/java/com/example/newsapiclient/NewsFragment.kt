package com.example.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.databinding.FragmentNewsBinding
import com.example.newsapiclient.presentation.adapter.NewsAdapter
import com.example.newsapiclient.presentation.viewmodel.NewsViewModel
import com.example.newsapiclient.utils.navigateToInfoFragment
import kotlinx.coroutines.*

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: NewsAdapter

    private var country = "us"
    private var page = 1

    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        adapter = (activity as MainActivity).newsAdapter
        adapter.setItemClickedListener {
            navigateToInfoFragment(it)
        }
        initRecyclerView()
        viewNewsList()
        setSearchView()
        viewSearchedNews()
    }

    private fun initRecyclerView() {
        binding.rvNews.apply {
            adapter = this@NewsFragment.adapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(onScrollChangeListener)
        }
    }

    private fun setSearchView() {
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchNews(country, it, page) }
                return false
            }

            var job: Job? = null

            // TODO Testar para ver se não chama mais de uma vez a searchNews
            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    job?.cancel()
                    job = null

                    if (it.isEmpty()) {
                        adapter.differ.submitList(arrayListOf())
                        viewModel.getNewsHeadlines(country, page)
                    } else {
                        job = MainScope().launch {
                            delay(800)
                            if (isActive) {
                                viewModel.searchNews(country, it, page)
                            }
                        }
                    }
                }
                return false
            }
        })

        binding.svNews.setOnCloseListener {
            adapter.differ.submitList(arrayListOf())
            viewModel.getNewsHeadlines(country, page)
            false
        }
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadlines(country, page)
        viewModel.newsHeadlinesLiveData.observe(viewLifecycleOwner) {
            showResult(it)
        }
    }

    private fun viewSearchedNews() {
        viewModel.searchedNewsLiveData.observe(viewLifecycleOwner) {
            showResult(it)
        }
    }

    private fun showResult(resource: Resource<APIResponse>?) {
        resource?.let {
            when (resource) {
                is Resource.Error -> onError(resource)
                is Resource.Loading -> showProgress(true)
                is Resource.Success -> onSuccess(resource)
            }
        }
    }

    private fun onSuccess(resource: Resource<APIResponse>) {
        resource.data?.let {
            adapter.differ.submitList(it.articles)

            pages = it.totalResults / 20

            if (it.totalResults % 20 != 0) {
                pages++
            }

            isLastPage = page == pages
        }

        showProgress(false)
    }

    private fun onError(resource: Resource<*>) {
        resource.message?.let { activity?.toast(it) }
        showProgress(false)
    }

    private fun showProgress(show: Boolean) {
        isLoading = show
        binding.progressBar.run {
            if (show) show() else gone()
        }
    }

    private val onScrollChangeListener = object : OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
            val size = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= size

            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if (shouldPaginate) {
                viewModel.getNewsHeadlines(country, ++page)
                isScrolling = false
            }
        }
    }
}