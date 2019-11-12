package com.sesang06.todaywhateat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sesang06.todaywhateat.R
import com.sesang06.todaywhateat.viewholder.RestaurantViewHolder


class RestaurantAdapter : RecyclerView.Adapter<RestaurantViewHolder>() {

    private var items: MutableList<String> = mutableListOf()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        items[position].let { title ->
            holder.bind(title)
        }
    }

    fun clearItems() {
        this.items.clear()
    }

    fun setItems(items: List<String>) {
        this.items = items.toMutableList()
    }

}