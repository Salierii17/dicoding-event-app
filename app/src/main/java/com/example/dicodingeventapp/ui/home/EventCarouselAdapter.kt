package com.example.dicodingeventapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.CarouselItemEventBinding

class EventCarouselAdapter(
    private val onClick: (ListEventsItem) -> Unit
) : ListAdapter<ListEventsItem, EventCarouselAdapter.CarouselViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view =
            CarouselItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(view, onClick)
    }


    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val eventItem = getItem(position)
        holder.bind(eventItem)

        if (position < 5) {
            holder.itemView.visibility = View.VISIBLE
        } else {
            holder.itemView.visibility = View.GONE
        }
    }

    class CarouselViewHolder(
        private var binding: CarouselItemEventBinding,
        val onClick: (ListEventsItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventItem: ListEventsItem) {
            Glide.with(binding.root).load(eventItem.imageLogo).into(binding.imgMediaCover)
            binding.tvName.text = eventItem.name

            itemView.setOnClickListener {
                onClick(eventItem)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}