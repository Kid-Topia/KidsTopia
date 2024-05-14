package com.limheejin.kidstopia.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.RvSearchfragmentItemBinding
import com.limheejin.kidstopia.presentation.fragment.Item

class RVSearchAdapter(
    private val onClick: (Int) -> Unit,
    private val onLongClick: (Int) -> Boolean
) : RecyclerView.Adapter<RVSearchAdapter.MyViewHolder>(){

    var items: List<Item> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvSearchfragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = items.size

    inner class MyViewHolder(val binding: RvSearchfragmentItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            with(itemView){
                setOnClickListener { onClick(adapterPosition) }
                setOnLongClickListener { onLongClick(adapterPosition) }
            }
        }

        // API 받아와서 아이템 연결 후 나중에 수정
        fun bind(data: Item){
            with(binding){
                searchitemThumbnail.setImageResource(R.drawable.preview_search_item)
                searchitemTitle.text = "임시"
                searchitemContext.text = "임시"
            }
        }

    }

}