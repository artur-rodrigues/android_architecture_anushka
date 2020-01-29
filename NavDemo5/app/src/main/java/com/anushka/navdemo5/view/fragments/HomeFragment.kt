package com.anushka.navdemo5.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anushka.navdemo5.R
import com.anushka.navdemo5.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)
        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            termsButton.setOnClickListener {
                it.findNavController().navigate(HomeFragmentDirections.homeToTermsFragment())
            }

            signUpButton.setOnClickListener {
                it.findNavController().navigate(HomeFragmentDirections.homeToNameFragment())
            }
        }
    }
}