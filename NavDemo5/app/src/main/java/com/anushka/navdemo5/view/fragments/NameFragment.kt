package com.anushka.navdemo5.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anushka.navdemo5.R
import com.anushka.navdemo5.databinding.FragmentNameBinding
import com.anushka.navdemo5.utils.toast

class NameFragment : Fragment() {

    private lateinit var binding: FragmentNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_name, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            nextButton.setOnClickListener {
                if (nameEditText.text.isEmpty()) {
                    activity?.toast(R.string.msg_campos_obrigatorios)
                } else {
                    it.findNavController().navigate(NameFragmentDirections
                        .nameToEmailFragment(nameEditText.text.toString()))
                }
            }
        }
    }
}
