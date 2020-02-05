package com.example.recyclerviewdemo1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo1.R
import com.example.recyclerviewdemo1.databinding.ListItemBinding
import com.example.recyclerviewdemo1.model.Fruit

typealias Click = (Fruit) -> Unit

class CustomAdapter(private val click: Click = {}): RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    private lateinit var binding: ListItemBinding

    var list: List<Fruit> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_item, parent,false)

        return CustomViewHolder(binding, click)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class CustomViewHolder(private val binding: ListItemBinding, private val click: Click = {}):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fruit: Fruit) {
            binding.fruit = fruit
            binding.root.setOnClickListener {
                click(fruit)
            }
        }
    }
}