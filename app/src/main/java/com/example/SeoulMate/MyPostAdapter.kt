package com.example.SeoulMate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.mate_list.view.*
import kotlinx.android.synthetic.main.post_list.view.*
import java.util.ArrayList

class MyPostAdapter(val context: Context, val itemCheck: (mateDTO) -> Unit)
    : RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {

    private var items = ArrayList<mateDTO>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val itemView: View = inflater.inflate(R.layout.post_list, viewGroup, false)
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

            itemView.title_list2.text = item.title
            itemView.where_list2.text = item.spot
            itemView.when_list2.text = item.date
            itemView.budget_list2.text = item.budget
            itemView.setOnClickListener() { itemCheck(item) }
        }

    }

}
