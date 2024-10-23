package com.example.dicodingeventapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.databinding.ItemSearchLayoutBinding

class SearchAdapter(private val onClick: (EventEntity) -> Unit) :
    ListAdapter<EventEntity, SearchAdapter.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view =
            ItemSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventViewHolder(
        private var binding: ItemSearchLayoutBinding,
        val onClick: (EventEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventItem: EventEntity) {
            Glide.with(binding.root).load(eventItem.mediaCover).into(binding.imgMediaCover)
            binding.tvName.text = eventItem.name

            itemView.setOnClickListener {
                onClick(eventItem)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}