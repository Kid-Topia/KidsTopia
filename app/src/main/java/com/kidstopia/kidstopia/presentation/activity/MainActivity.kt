package com.kidstopia.kidstopia.presentation.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kidstopia.kidstopia.R
import com.kidstopia.kidstopia.databinding.ActivityMainBinding
import com.kidstopia.kidstopia.databinding.DialogExitBinding
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoDAO
import com.kidstopia.kidstopia.model.database.MyFavoriteVideoDatabase
import com.kidstopia.kidstopia.presentation.fragment.HomeFragment
import com.kidstopia.kidstopia.presentation.fragment.MyVideoFragment
import com.kidstopia.kidstopia.presentation.fragment.SearchFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var dao: MyFavoriteVideoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 데이터베이스 모든정보 지울때 잠깐 주석풀어 사용
        // deleteAllDatabaseInfo()
        dao = MyFavoriteVideoDatabase.getDatabase(application).getDao()
        binding.nav.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.fl, HomeFragment()).commit()
    }

    private fun deleteAllDatabaseInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            dao = MyFavoriteVideoDatabase.getDatabase(application).getDao()
            dao.deleteVisitedVideo()
            dao.deleteLikedVideo()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment.isVisible) {
                fragmentTransaction.hide(fragment)
            }
        }

        when (item.itemId) {
            R.id.mnu_home -> {
                val homeFragment =
                    supportFragmentManager.findFragmentByTag("HOME") ?: HomeFragment().apply {
                        fragmentTransaction.add(R.id.fl, this, "HOME")
                    }
                fragmentTransaction.show(homeFragment)
            }

            R.id.mnu_search -> {
                val searchFragment =
                    supportFragmentManager.findFragmentByTag("SEARCH") ?: SearchFragment().apply {
                        fragmentTransaction.add(R.id.fl, this, "SEARCH")
                    }
                fragmentTransaction.show(searchFragment)
            }

            R.id.mnu_user -> {
                val myVideoFragment = supportFragmentManager.findFragmentByTag("MY_VIDEO")
                    ?: MyVideoFragment().apply {
                        fragmentTransaction.add(R.id.fl, this, "MY_VIDEO")
                    }
                fragmentTransaction.show(myVideoFragment)
            }
        }
        fragmentTransaction.commitAllowingStateLoss()
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            val dialogBinding = DialogExitBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .setCancelable(false)
                .create()

            // 다이얼로그의 Radius를 적용하기 위해 기존 다이얼로그의 builder 배경을 transparent로 설정
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // 다이얼로그 내부의 버튼 초기화 및 클릭 이벤트 설정
            dialogBinding.btnConfirm.setOnClickListener {
                dialog.dismiss()
                super.onBackPressed()  // 확인 버튼을 누르면 앱 종료
            }

            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()  // 취소 버튼을 누르면 다이얼로그 닫기
            }

            dialog.show()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}


