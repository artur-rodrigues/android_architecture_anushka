package com.example.navdemo1upgradedlesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navdemo1upgradedlesson.databinding.FragmentHomeBinding
import com.example.navdemo1upgradedlesson.utils.toast

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            button.setOnClickListener {
                if(editText.text.isEmpty()) {
                    activity?.toast("Não pode cara!!!")
                } else {
                    val action = HomeFragmentDirections.toSecondFragment(editText.text.toString())
                    findNavController().navigate(action)
                }
            }
        }
    }
}