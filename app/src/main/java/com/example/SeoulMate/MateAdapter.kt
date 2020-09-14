package com.example.SeoulMate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.mate_list.view.*
import java.util.ArrayList

class MateAdapter(val context: Context, val itemCheck: (mateDTO) -> Unit)
    : RecyclerView.Adapter<MateAdapter.ViewHolder>() {

    private var items = ArrayList<mateDTO>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflater.inflate(R.layout.mate_list, viewGroup, false)
        return ViewHolder(itemView, itemCheck)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item: mateDTO = items[position]
        viewHolder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun setItems(items: ArrayList<mateDTO>) {
        this.items = items
    }

    inner class ViewHolder(itemView: View, itemCheck: (mateDTO) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: mateDTO) {

            itemView.title_list.text = item.title
            itemView.where_list.text = item.spot
            itemView.when_list.text = item.date
            itemView.budget_list.text = item.budget
            Glide.with(context).load(item.photo).into(itemView.photo_list)
            itemView.setOnClickListener() { itemCheck(item) }
        }

    }


}