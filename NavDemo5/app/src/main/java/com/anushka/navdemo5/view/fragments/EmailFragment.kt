package com.anushka.navdemo5.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.anushka.navdemo5.R
import com.anushka.navdemo5.databinding.FragmentEmailBinding
import com.anushka.navdemo5.utils.toast

class EmailFragment : Fragment() {

    private lateinit var binding: FragmentEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_email, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userName = EmailFragmentArgs.fromBundle(arguments!!).userName

        binding.apply {
            submitButton.setOnClickListener {
                if(emailEditText.text.isEmpty()) {
                    activity?.toast(R.string.msg_campos_obrigatorios)
                } else {
                    it.findNavController().navigate(
                        EmailFragmentDirections
                            .emailToWelcomeFragment(userName,
                                emailEditText.text.toString())
                    )
                }
            }
        }
    }
}
