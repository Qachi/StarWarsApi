package com.example.starwarsapi_paging3_roomdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapi_paging3_roomdb.R
import com.example.starwarsapi_paging3_roomdb.model.People
import kotlinx.android.synthetic.main.row_layout.view.*

class StarWarAdapter(
    private val clickListener: OnPeopleClickListener
) : PagingDataAdapter<People, StarWarAdapter.MyViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position) ?: return
        holder.initialize(currentItem)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun initialize(people: People) {
            itemView.name_text.text = people.name
            itemView.birth_txt.text = people.birth_year
            itemView.film_txt.text = people.films.joinToString("\n")
            itemView.eye_txt.text = people.eye_color
            itemView.gender_txt.text = people.gender

            itemView.setOnClickListener {
                clickListener.itemClicked(people, absoluteAdapterPosition)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(
                oldItem: People,
                newItem: People
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: People,
                newItem: People
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    interface OnPeopleClickListener {
        fun itemClicked(people: People, position: Int)
    }
}