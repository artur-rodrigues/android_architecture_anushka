package com.example.tmdbclient.presentation.commom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbclient.BuildConfig
import com.example.tmdbclient.R
import com.example.tmdbclient.databinding.ListItemBinding
import com.example.tmdbclient.loadImage

class GenericAdapter<T>(private val fillAdapterInfo: FillAdapterInfo<T>) :
    RecyclerView.Adapter<MyViewHolder<T>>() {

    private var listItems = ArrayList<T>()

    interface FillAdapterInfo<T> {
        fun getTitle(item: T): String
        fun getDescription(item: T): String
        fun getImageUrl(item: T): String
    }

    fun setList(itemList: ArrayList<T>) {
        this.listItems.clear()
        this.listItems.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false)
        return MyViewHolder<T>(binding, fillAdapterInfo)
    }

    override fun onBindViewHolder(holder: MyViewHolder<T>, position: Int) {
        holder.binding(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
}

class MyViewHolder<T>(
    private val binding: ListItemBinding,
    private val fillAdapterInfo: GenericAdapter.FillAdapterInfo<T>
) :
    RecyclerView.ViewHolder(binding.root) {

    fun binding(item: T) {
        binding.run {
            titleTextView.text = fillAdapterInfo.getTitle(item)
            descriptionTextView.text = fillAdapterInfo.getDescription(item)
            imageView.loadImage(BuildConfig.BASE_IMAGE_URL + fillAdapterInfo.getImageUrl(item))
        }
    }
}