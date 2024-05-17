package com.limheejin.kidstopia.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.RvFavoriteVideoBinding
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity

class MyFavoriteVideoAdapter(private val items: MutableList<MyFavoriteVideoEntity>): RecyclerView.Adapter<MyFavoriteVideoAdapter.Holder>() {
    class Holder(private val binding: RvFavoriteVideoBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.favoriteVideoIv
        val channelId = binding.favoriteVideoChannelIdTv
        val title = binding.favoriteVideoTitleTv
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RvFavoriteVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyFavoriteVideoAdapter.Holder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position].thumbnails)
            .into(holder.image)

        holder.channelId.text = items[position].ChannelId
        holder.title.text = items[position].title
    }
}
