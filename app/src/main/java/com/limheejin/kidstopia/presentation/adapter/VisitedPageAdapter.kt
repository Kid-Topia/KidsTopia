package com.limheejin.kidstopia.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.RvFavoriteVideoBinding
import com.limheejin.kidstopia.databinding.RvVisitedVideoBinding
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity

class VisitedPageAdapter(private val items: MutableList<MyFavoriteVideoEntity>): RecyclerView.Adapter<VisitedPageAdapter.Holder>() {
    class Holder(private val binding: RvVisitedVideoBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.visitedPageIv
        val channelId = binding.visitedPageChannelidTv
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RvVisitedVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: VisitedPageAdapter.Holder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position].thumbnails)
            .into(holder.image)

        holder.channelId.text = items[position].ChannelId
    }

    override fun getItemCount(): Int {
        return items.size
    }
}