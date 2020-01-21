package com.example.countriesapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesapp.R
import com.example.countriesapp.databinding.RowCountryBinding
import com.example.countriesapp.model.entity.Country

typealias Click = (Country) -> Unit

class CountryAdapter(val click: Click = {}):
    RecyclerView.Adapter<CountryAdapter.CountryHolder>() {

    var countries = arrayListOf<Country>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val binding:RowCountryBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.row_country,
                parent, false)
        return CountryHolder(binding)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val country = countries[position]
        holder.binding.tvCountryName.text = country.title
        holder.binding.root.setOnClickListener {
            click(country)
        }
    }

    class CountryHolder(val binding: RowCountryBinding):
        RecyclerView.ViewHolder(binding.root)
}