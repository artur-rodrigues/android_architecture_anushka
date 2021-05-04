package com.example.roomdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.R
import com.example.roomdemo.databinding.ListItemBinding
import com.example.roomdemo.db.Subscriber

typealias Click = (Subscriber) -> Unit

class MyRecyclerViewAdapter(
    private var subscribersList: List<Subscriber> = ArrayList(),
    private var itemClicked: Click = {}
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ListItemBinding>(inflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position], itemClicked)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    fun setSubscribers(subscribersList: List<Subscriber>) {
        this.subscribersList = subscribersList
        notifyDataSetChanged()
    }
}

class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber, itemClicked: Click) {
        binding.run {
            nameTextView.text = subscriber.name
            emailTextView.text = subscriber.email
            listItemLayout.setOnClickListener {
                itemClicked(subscriber)
            }
        }
    }
}