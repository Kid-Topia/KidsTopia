package com.limheejin.kidstopia.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.FragmentVideoDetailBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class VideoDetailFragment : Fragment() {
//    private var param1: Example? = null
    private var param2: String? = null

    private val binding by lazy {
        FragmentVideoDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getParcelable(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initView() { // 임시로 지정해뒀습니다
        binding.tvChannelName.text = "뿅"
        binding.tvTitle.text = "쨘"
        binding.tvDescription.text = "얍"
        binding.ivThumbnail.setImageResource(R.drawable.ic_kidstopia_img)
    }

    private fun initListener() {
        binding.btnPlay.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_play, Toast.LENGTH_SHORT).show()
        }
        binding.btnLikeImg.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_like, Toast.LENGTH_SHORT).show()
        }
        binding.btnShareImg.setOnClickListener {
            Toast.makeText(context, R.string.toast_detailfragment_share, Toast.LENGTH_SHORT).show()
        }
    }

    companion object { // 데이터클래스 만들면 연동할 예정입니다
//        @JvmStatic
//        fun newInstance(param1: Example, param2: String) =
//            VideoDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}