package com.limheejin.kidstopia.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.limheejin.kidstopia.databinding.RvFavoriteVideoBinding
import com.limheejin.kidstopia.databinding.RvVisitedVideoBinding
import com.limheejin.kidstopia.model.database.MyFavoriteVideoEntity

class VisitedPageAdapter(private val items: MutableList<MyFavoriteVideoEntity>): RecyclerView.Adapter<VisitedPageAdapter.Holder>() {
    class Holder(private val binding: RvVisitedVideoBinding): RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RvVisitedVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: VisitedPageAdapter.Holder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}