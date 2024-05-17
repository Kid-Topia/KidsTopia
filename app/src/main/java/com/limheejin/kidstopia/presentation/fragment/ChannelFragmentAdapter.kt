package com.limheejin.kidstopia.presentation.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.ChannelItemBinding
import com.limheejin.kidstopia.model.PopularItems

class ChannelFragmentAdapter(private val context: Context) :
    RecyclerView.Adapter<ChannelFragmentAdapter.ChannelViewHolder>() {

    var itemsChannel = ArrayList<PopularItems>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = ChannelItemBinding.
        inflate(LayoutInflater.from(parent.context),
            parent,false)
        return ChannelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsChannel.size
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val items = itemsChannel[position]
        holder.Channel(items)
    }

    inner class ChannelViewHolder(private val binding: ChannelItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun Channel(item: PopularItems){
                val img_channel : ImageView = binding.imgChannel
                val title_channel : TextView = binding.titleChannel

                Glide.with(context)
                    .load(item.snippet.thumbnails)
                    .into(img_channel)

                title_channel.text = item.snippet.title
            }

    }
    fun updateData(newItems: List<PopularItems>) {
        itemsChannel.clear()
        itemsChannel.addAll(newItems)
        notifyDataSetChanged()
    }

}