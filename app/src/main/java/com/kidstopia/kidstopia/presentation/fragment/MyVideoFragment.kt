package com.kidstopia.kidstopia.presentation.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidstopia.kidstopia.R
import com.kidstopia.kidstopia.databinding.DialogUsernameBinding
import com.kidstopia.kidstopia.databinding.FragmentMyVideoBinding
import com.kidstopia.kidstopia.model.UserPreferences
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoEntity
import com.kidstopia.kidstopia.presentation.adapter.MyFavoriteVideoAdapter
import com.kidstopia.kidstopia.presentation.adapter.VisitedPageAdapter
import com.kidstopia.kidstopia.viewmodel.MyVideoViewModel
import com.kidstopia.kidstopia.viewmodel.MyVideoViewModelFactory
import kotlinx.coroutines.launch


class MyVideoFragment : Fragment() {
    private lateinit var binding: FragmentMyVideoBinding
    private lateinit var items: MutableList<MyFavoriteVideoEntity>
    private lateinit var visitedPageAdapter: VisitedPageAdapter
    private lateinit var myFavoriteVideoAdapter: MyFavoriteVideoAdapter
    private val myVideoViewModel by viewModels<MyVideoViewModel> {
        MyVideoViewModelFactory(requireContext())
    }

    private val userPreferences by lazy {
        UserPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getItems()
        initRv()
        setEditNameListener()
        observeUserName()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            getItems()
        }
    }

    private fun getItems() = lifecycleScope.launch {
        myVideoViewModel.getItems()
    }

    private fun initRv() {
        myVideoViewModel.items.observe(viewLifecycleOwner) { viewModelItems ->
            items = viewModelItems
            visitedPageAdapter = VisitedPageAdapter(items)
            myFavoriteVideoAdapter =
                MyFavoriteVideoAdapter(items.filter { it.classify == "isLiked" }.toMutableList())

            setupVisitedPageAdapterClickListener(visitedPageAdapter)
            setupMyFavoriteVideoAdapterClickListener(myFavoriteVideoAdapter)

            binding.apply {
                favoriteVideoRv.adapter = myFavoriteVideoAdapter
                visitedPageRv.adapter = visitedPageAdapter
                favoriteVideoRv.layoutManager = LinearLayoutManager(requireContext())
                visitedPageRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun setupVisitedPageAdapterClickListener(adapter: VisitedPageAdapter) {
        adapter.itemClick = object : VisitedPageAdapter.ItemClick {
            override fun itemClick(id: String) {
                setFragment(id)
            }
        }
    }

    private fun setupMyFavoriteVideoAdapterClickListener(adapter: MyFavoriteVideoAdapter) {
        adapter.itemClick = object : MyFavoriteVideoAdapter.ItemClick {
            override fun itemClick(id: String) {
                setFragment(id)
            }
        }
    }

    private fun setEditNameListener() {
        binding.icEditUsername.setOnClickListener {
            showEditNameDialog()
        }
        binding.myIdTv.setOnClickListener {
            showEditNameDialog()
        }
    }

    private fun showEditNameDialog() {
        val dialogBinding = DialogUsernameBinding.inflate(layoutInflater) // 바인딩으로 구현
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnUsernameConfirm.setOnClickListener {
            val newName = dialogBinding.etEditUsername.text.toString()
            saveUserName(newName)
            dialog.dismiss()
        }

        dialogBinding.btnUsernameCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun saveUserName(newName: String) {
        lifecycleScope.launch {
            userPreferences.updateUserName(newName)
        }
    }

    private fun observeUserName() {
        lifecycleScope.launch {
            userPreferences.userNameFlow.collect { userName ->
                binding.myIdTv.text = userName
            }
        }
    }

    private fun setFragment(id: String) {
        val videoDetailFragment = VideoDetailFragment()
        val bundle = Bundle()
        bundle.putString("VideoId", id)
        videoDetailFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_up, R.anim.none, R.anim.none, R.anim.slide_down)
            .replace(R.id.fl, videoDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}