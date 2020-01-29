package com.anushka.navdemo5.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.anushka.navdemo5.R
import com.anushka.navdemo5.databinding.FragmentTermsBinding

/**
 * A simple [Fragment] subclass.
 */
class TermsFragment : Fragment() {

    private lateinit var binding: FragmentTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_terms, container, false)
        return binding.root
    }
}