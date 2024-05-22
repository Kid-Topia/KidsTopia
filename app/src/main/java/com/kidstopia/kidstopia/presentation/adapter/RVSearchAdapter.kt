package com.kidstopia.kidstopia.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kidstopia.kidstopia.databinding.RvSearchfragmentItemBinding
import com.kidstopia.kidstopia.model.SearchItems

class RVSearchAdapter(
    private val onItemClick: (SearchItems) -> Unit,
    private val onLongClick: (Int) -> Boolean
) : RecyclerView.Adapter<RVSearchAdapter.MyViewHolder>() {

    private var items: List<SearchItems> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<SearchItems>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RvSearchfragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyViewHolder(val binding: RvSearchfragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(items[position])
                }
            }
            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onLongClick(position)
                } else {
                    false
                }
            }
        }

        fun bind(data: SearchItems) {
            with(binding) {
                searchitemTitle.text = data.snippet.title
                searchitemContext.text = data.snippet.description
                Glide.with(itemView.context)
                    .load(data.snippet.thumbnails.medium.url)
                    .into(searchitemThumbnail)
            }
        }
    }
}