package com.example.dicodingeventapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.databinding.CarouselItemEventBinding

class CarouselAdapter(private val onClick: (EventEntity) -> Unit) :
    ListAdapter<EventEntity, CarouselAdapter.CarouselViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder{
        val view = CarouselItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class CarouselViewHolder(
        private var binding: CarouselItemEventBinding,
        val onClick: (EventEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventItem: EventEntity) {
            binding.tvName.text = eventItem.name
            Glide.with(itemView.context)
                .load(eventItem.mediaCover)
                .into(binding.imgMediaCover)

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

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: EventEntity,
                newItem: EventEntity
            ): Boolean {
                return oldItem == newItem
            }

        }

    }
}