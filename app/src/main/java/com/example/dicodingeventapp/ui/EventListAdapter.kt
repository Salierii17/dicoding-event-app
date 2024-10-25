package com.example.dicodingeventapp.ui

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.local.EventEntity
import com.example.dicodingeventapp.databinding.ItemEventBinding

class EventListAdapter(
    private val sizeOption: Int,
    private val onClick: (EventEntity) -> Unit
) : ListAdapter<EventEntity, EventListAdapter.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.sizeOption(sizeOption)
    }

    class EventViewHolder(
        private var binding: ItemEventBinding,
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

        fun sizeOption(sizeOption: Int) {
            when (sizeOption) {
                SIZE_SMALL -> {
                    binding.main.layoutParams.width = 500
                    binding.main.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    binding.tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    binding.tvName.maxLines = 1
                    binding.imgMediaCover.scaleType = ImageView.ScaleType.CENTER_CROP
                }

                SIZE_LARGE -> {
                    binding.main.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    binding.main.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
            }
        }
    }

    companion object {

        const val SIZE_SMALL = 1
        const val SIZE_LARGE = 2

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