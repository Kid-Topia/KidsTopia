package com.kidstopia.kidstopia.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kidstopia.kidstopia.databinding.RvVisitedVideoBinding
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoEntity

class VisitedPageAdapter(private val items: MutableList<MyFavoriteVideoEntity>) :
    RecyclerView.Adapter<VisitedPageAdapter.Holder>() {
    interface ItemClick {
        fun itemClick(id: String)
    }

    var itemClick: ItemClick? = null

    class Holder(binding: RvVisitedVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.visitedPageIv
        val title = binding.visitedPageTitleTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RvVisitedVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: VisitedPageAdapter.Holder, position: Int) {
        items.sortByDescending { it.date }
        holder.itemView.setOnClickListener {
            itemClick?.itemClick(items[position].id)
        }

        Glide.with(holder.itemView.context)
            .load(items[position].thumbnails)
            .into(holder.image)

        holder.title.text = items[position].title
    }

    override fun getItemCount(): Int {
        return items.size
    }
}