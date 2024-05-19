
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
import com.limheejin.kidstopia.presentation.adapter.ChannelRVAdapter
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
    private lateinit var adapterChannel: ChannelRVAdapter


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
        setupChannelRV()
        setupRecyclerView()
        fetchMostPopularVideos()
        fetchChannel()

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

    private fun fetchChannel() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    NetworkClient.youtubeApiChannels.getChannels(
                        key = NetworkClient.AUTH_KEY,
                        part = "snippet,contentDetails",
                        id = "UCL6JmiMXKoXS6bpP1D3bk8g,UCPUeGC_AL-OnDQORKhRm6iA,UCscjd-azZ1AqHxxrO6YDIQg,UCscjd-azZ1AqHxxrO6YDIQg,UC-oIulX1JBJ0aKAB0GHnThA" ,
                        maxResults = 5
                    )
                }
                adapterChannel.setItemsChannel(response.items)
            } catch (e: Exception){
                Toast.makeText(requireContext(), "채널 연결이 실패했습니다. .", Toast.LENGTH_SHORT).show()

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
        val layoutManagerChannel = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )

        //어댑터 연결

        binding.homeMostVidio.layoutManager = layoutManagerMostPopular
        binding.homeMostVidio.adapter = adapterMostPopular

//        binding.hoemCategory.layoutManager = layoutManagerCategotry
//        binding.hoemCategory.adapter = adapterCategoty
//
        binding.homeChannel.layoutManager = layoutManagerChannel
        binding.homeChannel.adapter = adapterChannel


    }

    private fun setupMostPopularRV() {
        adapterMostPopular = MostPopularRVAdapter(
            onItemClick = {

            }
        )
    }

    private fun setupChannelRV(){
        adapterChannel = ChannelRVAdapter (
            onItemClick = {

            }
        )
    }

//    private fun categotyCommunicateNetwork() = lifecycleScope.launch {
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

