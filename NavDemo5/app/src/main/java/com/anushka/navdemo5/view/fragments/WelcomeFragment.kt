package com.anushka.navdemo5.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.anushka.navdemo5.R
import com.anushka.navdemo5.databinding.FragmentWelcomeBinding
import com.anushka.navdemo5.model.User

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_welcome, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val (userName, userEmail) = WelcomeFragmentArgs.fromBundle(arguments!!)

        binding.apply {
            user = User(userName, userEmail)

            viewTermsButton.setOnClickListener {
                it.findNavController().navigate(WelcomeFragmentDirections.welcomeToTermsFragment())
            }
        }
    }
}
