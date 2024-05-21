package com.limheejin.kidstopia.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.CategoryItemBinding
import com.limheejin.kidstopia.model.PopularItems

class CategoryRVAdapter(private val onItemClick: (PopularItems) -> Unit) :
    RecyclerView.Adapter<CategoryRVAdapter.CategoryViewHolder>(){

    private var categoryitems: List<PopularItems> = mutableListOf()

    fun setCategoryItems(items: List<PopularItems>){
        this.categoryitems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding =
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val currentItem = categoryitems[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = categoryitems.size


    inner class CategoryViewHolder(val binding: CategoryItemBinding) :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position =adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    onItemClick(categoryitems[position])
                }
            }
        }

        fun bind(item: PopularItems){
            with(binding){
                categoryTextInfo.text = item.snippet.title
                Glide.with(itemView.context)
                    .load(item.snippet.thumbnails.medium.url)
                    .into(categoryImg)
            }
        }
    }
}