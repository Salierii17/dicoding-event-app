package com.example.dicodingeventapp.ui.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.ItemEventBinding

class EventAdapter(private val onClick: (ListEventsItem) -> Unit) :
    ListAdapter<ListEventsItem, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventViewHolder(
        private var binding: ItemEventBinding,
        val onClick: (ListEventsItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            Glide.with(binding.root).load(event.mediaCover).into(binding.imgEvent)
            binding.tvTitle.text = event.name

            itemView.setOnClickListener {
                onClick(event)
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