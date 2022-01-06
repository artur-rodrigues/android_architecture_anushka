package com.example.newsapiclient.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsapiclient.R
import com.example.newsapiclient.data.model.Article

fun Fragment.navigateToInfoFragment(article: Article) {
    val bundle = Bundle().apply {
        putSerializable("selected_article", article)
    }

    findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
}