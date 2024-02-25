package com.example.starwarsapi_paging3_roomdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapi_paging3_roomdb.databinding.RowLayoutBinding
import com.example.starwarsapi_paging3_roomdb.model.PeopleResponseEntity

class StarWarAdapter(
    private val clickListener: OnPeopleClickListener
) : PagingDataAdapter<PeopleResponseEntity, StarWarAdapter.MyViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position) ?: return
        holder.initialize(currentItem)
    }

    inner class MyViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun initialize(people: PeopleResponseEntity) {
            binding.apply {
                nameText.text = people.name
                birthTxt.text = people.birth_year
                filmTxt.text = people.films.joinToString("\n")
                eyeTxt.text = people.eye_color
                genderTxt.text = people.gender
                itemView.setOnClickListener {
                    clickListener.itemClicked(people, absoluteAdapterPosition)
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PeopleResponseEntity>() {
            override fun areItemsTheSame(
                oldItem: PeopleResponseEntity,
                newItem: PeopleResponseEntity
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PeopleResponseEntity,
                newItem: PeopleResponseEntity
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    interface OnPeopleClickListener {
        fun itemClicked(people: PeopleResponseEntity, position: Int)
    }
}