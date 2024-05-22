package com.kidstopia.kidstopia.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kidstopia.kidstopia.databinding.CategoryItemBinding
import com.kidstopia.kidstopia.model.SearchItems

class CategoryAdapter(private val onItemClick: (SearchItems) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryitems: MutableList<SearchItems> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryItems(items: MutableList<SearchItems>) {
        this.categoryitems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryitems[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return categoryitems.size
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(categoryitems[position])
                }
            }
        }

        fun bind(item: SearchItems) {
            with(binding) {
                categoryTextInfo.text = item.snippet.title
                Glide.with(itemView.context)
                    .load(item.snippet.thumbnails.medium.url)
                    .into(categoryImg)
            }
        }
    }
}