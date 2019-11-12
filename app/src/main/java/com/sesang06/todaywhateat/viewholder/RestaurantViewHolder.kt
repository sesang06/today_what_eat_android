package com.sesang06.todaywhateat.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView by lazy { itemView.text_view_title }

    fun bind(title: String) {
        textView.text = title
    }
}