package com.limheejin.kidstopia.presentation.fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.MostPopularItemBinding
import com.limheejin.kidstopia.model.PopularItems

class MostFragmentAdapter(private val context: Context) :
    RecyclerView.Adapter<MostFragmentAdapter.MostViewHolder>() {

    private val items: MutableList<PopularItems> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostViewHolder {
        val binding =
            MostPopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return MostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MostViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class MostViewHolder(private val binding: MostPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PopularItems) {
            binding.textInfo.text = item.snippet.title
            Log.d("test", "item" + item.snippet.title)

            Glide.with(context)
                .load(item.snippet.thumbnails)
                .into(binding.mostImg)
        }
    }
    fun updateData(newItems: List<PopularItems>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}