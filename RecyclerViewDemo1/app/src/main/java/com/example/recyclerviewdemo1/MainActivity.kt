package com.example.recyclerviewdemo1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewdemo1.adapter.CustomAdapter
import com.example.recyclerviewdemo1.databinding.ActivityMainBinding
import com.example.recyclerviewdemo1.model.Fruit
import com.example.recyclerviewdemo1.util.toast

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val adapter = CustomAdapter {
        toast(it.supplier)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpBinding()
    }

    private fun setUpBinding() {
        binding.recyclerView.apply {
            setBackgroundColor(Color.YELLOW)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        adapter.list = getFruitList("Manga", "Maça", "Laranja", "Banana")
    }

    private fun getFruitList(vararg names: String): List<Fruit> {
        val list = arrayListOf<Fruit>()

        for (i in names.indices) {
            list.add(Fruit(names[i], "Fornecedor ${i + 1}"))
        }

        return list
    }
}
