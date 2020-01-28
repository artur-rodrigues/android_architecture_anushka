package com.anushka.bindingdemo2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.anushka.bindingdemo2.databinding.ActivityMainBinding
import com.anushka.bindingdemo2.utils.gone
import com.anushka.bindingdemo2.utils.visible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.controlButton.setOnClickListener {
            startOrStopProgressBar()
        }
    }

    private fun teste(): String {
        return getString(R.string.start).also {
            print(it)
        }
    }

    private fun startOrStopProgressBar() {
        binding.controlButton.apply B@ {
            binding.progressBar.apply P@ {
                if (! isVisible) {
                    visible()
                    setText(R.string.stop)
//                    this@B.setText(R.string.stop)
                } else {
                    gone()
                    setText(R.string.start)
//                    this@B.setText(R.string.start)
                }
            }
        }
    }
}

