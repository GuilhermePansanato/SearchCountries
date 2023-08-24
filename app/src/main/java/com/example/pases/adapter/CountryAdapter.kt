package com.example.pases.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pases.Country
import com.example.pases.R

class CountryAdapter() :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private val countries = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_adapter_paises, parent, false)
        return ViewHolder(view)
    }

    private val filteredList: MutableList<Country> = countries.toMutableList()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val country = filteredList[position]
        holder.name.text = (country.translations?.por?.common)


        holder.capital.text = (country.capital?.firstOrNull())


        holder.population.text = (country.population?.toString())

        Glide.with(holder.flag.context)
            .load(country.flags?.png)
            .fitCenter()
            .error(R.drawable.ic_launcher_background)
            .into(holder.flag)

    }

    override fun getItemCount(): Int = filteredList.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val capital: TextView
        val population: TextView
        val flag: ImageView

        init {
            name = view.findViewById(R.id.tv_pais)
            capital = view.findViewById(R.id.tv_capital)
            population = view.findViewById(R.id.tv_populacao)
            flag = view.findViewById(R.id.iv_flag)
        }

    }

    fun filter(query: String) {

        filteredList.clear()
        if (query.isNullOrEmpty()) {
            filteredList.addAll(countries)
        } else {
            countries.forEach {
                if (it.translations?.por?.common?.contains(query, ignoreCase = true)== true) {
                    filteredList.add(it)
                }
            }
        }
        notifyDataSetChanged()
    }
    fun updateList (countries : List<Country>){
        this.filteredList.clear()
        this.filteredList.addAll(countries)
        this.countries.clear()
        this.countries.addAll(countries)
        notifyDataSetChanged()
    }
}



