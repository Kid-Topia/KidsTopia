package com.limheejin.kidstopia.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.ChannelItemBinding
import com.limheejin.kidstopia.model.database.ChannelData

class ChannelAdapter(private val onItemClick: (ChannelData) -> Unit) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    private var itemsChannel: List<ChannelData> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItemsChannel(items: List<ChannelData>) {
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

        fun bind(item: ChannelData) {
            with(binding) {
                titleChannel.text = item.snippet.title
                Glide.with(itemView.context)
                    .load(item.snippet.thumbnails?.high?.url)
                    .into(imgChannel)
            }
        }

    }
}