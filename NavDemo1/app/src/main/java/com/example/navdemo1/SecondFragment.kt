package com.example.navdemo1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.navdemo1.databinding.FragmentSecondBinding
import com.example.navdemo1.ui.main.MainFragmentDirections

class SecondFragment : Fragment() {

    lateinit var binding: FragmentSecondBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second,
            container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = SecondFragmentArgs.fromBundle(arguments!!)

        binding.textView.text = args.userName

        binding.toFirst.setOnClickListener {
            Navigation.findNavController(it).navigate(SecondFragmentDirections.toMainFragment())
        }

        binding.toThird.setOnClickListener {
            Navigation.findNavController(it).navigate(SecondFragmentDirections.toThirdFragment())
        }
    }
}
