package com.limheejin.kidstopia.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.MostPopularItemBinding
import com.limheejin.kidstopia.model.PopularItems

class MostPopularVideoAdapter(private val onItemClick: (PopularItems) -> Unit) : // 클릭 시 전환 편하도록 설정
    RecyclerView.Adapter<MostPopularVideoAdapter.MostPopularViewHolder>() {

    private var items: List<PopularItems> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<PopularItems>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        val binding =
            MostPopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MostPopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MostPopularViewHolder(val binding: MostPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(items[position])
                }
            }
        }

        fun bind(item: PopularItems) {
            with(binding) {
                textInfo.text = item.snippet.title // XML 상의 아이디 명을 모두 바꿀 필요가 있음. 겹칠만한 아이디가 너무 많음
                Glide.with(itemView.context)
                    .load(item.snippet.thumbnails.medium.url)
                    .into(mostImg)
            }
        }
    }
}