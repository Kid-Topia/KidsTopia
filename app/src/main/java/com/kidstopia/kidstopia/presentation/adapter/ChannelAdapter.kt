package com.kidstopia.kidstopia.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kidstopia.kidstopia.databinding.ChannelItemBinding
import com.kidstopia.kidstopia.model.ChannelItems

class ChannelAdapter(private val onItemClick: (ChannelItems) -> Unit) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    private var itemsChannel: List<ChannelItems> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsChannel(items: List<ChannelItems>) {
        this.itemsChannel = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ChannelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val items = itemsChannel[position]
        holder.bind(items)
    }

    override fun getItemCount(): Int {
        return itemsChannel.size
    }

    inner class ChannelViewHolder(val binding: ChannelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(itemsChannel[position])
                }
            }
        }

        fun bind(item: ChannelItems) {
            with(binding) {
                titleChannel.text = item.snippet.title
                Glide.with(itemView.context)
                    .load(item.snippet.thumbnails?.high?.url)
                    .into(imgChannel)
            }
        }

    }
}