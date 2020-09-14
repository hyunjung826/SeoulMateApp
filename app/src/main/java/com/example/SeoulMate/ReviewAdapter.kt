package com.example.SeoulMate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.review_list.view.*
import java.util.ArrayList

class ReviewAdapter (val context: Context, val reviewitemCheck: (ReviewDTO) -> Unit)
    : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    private var reviewitems = ArrayList<ReviewDTO>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(viewGroup.context)
        val reviewitemView: View = inflater.inflate(R.layout.review_list, viewGroup, false)
        return ViewHolder(reviewitemView, reviewitemCheck)
    }
    /*클릭 이벤트*/

    ///////////////////////////////////////////////////////////////////////////
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item: ReviewDTO = reviewitems[position]
        viewHolder.setItem(item)
    }

    override fun getItemCount(): Int {
        return reviewitems.count()
    }

    fun setItems(items: ArrayList<ReviewDTO>) {
        this.reviewitems = items
    }


    inner class ViewHolder(itemView: View, itemCheck: (ReviewDTO) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: ReviewDTO) {
            itemView.review_id2.text = item.id
            itemView.review_date2.text = item.date
            itemView.review_text2.text = item.review
            itemView.setOnClickListener() { reviewitemCheck(item) }
        }
    }

}

