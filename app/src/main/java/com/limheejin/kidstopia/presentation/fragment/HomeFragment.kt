
package com.limheejin.kidstopia.presentation.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.limheejin.kidstopia.databinding.FragmentHomeBinding
import com.limheejin.kidstopia.presentation.fragment.Adapter.MostPopularRVAdapter
import com.limheejin.kidstopia.presentation.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterMostPopular: MostPopularRVAdapter
//    private lateinit var adapterCategoty: CategoryRVAdapter
//    private lateinit var adapterChannel: ChannelRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMostPopularRV()
        setupRecyclerView()
        fetchMostPopularVideos()

    }

    private fun fetchMostPopularVideos() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    NetworkClient.youtubeApiPopularVideo.getPopularVideoList(
                        key = NetworkClient.AUTH_KEY,
                        part = "snippet",
                        chart = "mostPopular",
                        maxResults = 5
                    )
                }
                adapterMostPopular.setItems(response.items)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "인기 동영상이 정상적으로 연결되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setupRecyclerView() {
        // 수평 스크롤
        val layoutManagerMostPopular = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )

//        val layoutManagerCategotry = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL, false
//        )
//        val layoutManagerChannel = LinearLayoutManager(
//            requireContext(),
//            LinearLayoutManager.HORIZONTAL, false
//        )

        //어댑터 연결

        binding.mostVidio.layoutManager = layoutManagerMostPopular
        binding.mostVidio.adapter = adapterMostPopular

//        binding.category.layoutManager = layoutManagerCategotry
////        binding.category.adapter = adapterCategoty
//
//        binding.channel.layoutManager = layoutManagerChannel
////        binding.channel.adapter = adapterChannel


    }

    private fun setupMostPopularRV() {
        adapterMostPopular = MostPopularRVAdapter(
            onItemClick = {

            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

