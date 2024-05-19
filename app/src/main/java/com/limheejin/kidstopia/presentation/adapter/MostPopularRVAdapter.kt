
package com.limheejin.kidstopia.presentation.fragment.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.limheejin.kidstopia.databinding.MostPopularItemBinding
import com.limheejin.kidstopia.model.PopularItems


// 1. 네이밍: Fragment Adapter 가 아닌 RVAdapter임.
// 또한 MostViewHolder와 같은 느낌으로 명명되어 있는데 MostLiked(가장 많은 좋아요) 등 다른 곳에도 Most가 쓰일 곳이 많아서 정확히 수정해야 함
class MostPopularRVAdapter(private val onItemClick: (PopularItems) -> Unit ) : // 클릭 시 전환 편하도록 설정
    RecyclerView.Adapter<MostPopularRVAdapter.MostPopularViewHolder>() {

    private var items: List<PopularItems> = mutableListOf()

    fun setItems(items: List<PopularItems>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        val binding =
            MostPopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return MostPopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = items.size

    inner class MostPopularViewHolder(val binding: MostPopularItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    onItemClick(items[position])
                }
            }
        }

        fun bind(item: PopularItems) {
            with(binding){
                textInfo.text = item.snippet.title // XML 상의 아이디 명을 모두 바꿀 필요가 있음. 겹칠만한 아이디가 너무 많음
                Glide.with(itemView.context)
                    .load(item.snippet.thumbnails.medium.url)
                    .into(mostImg)

            }
        }
    }
}