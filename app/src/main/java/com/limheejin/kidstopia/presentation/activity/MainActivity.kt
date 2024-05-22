package com.limheejin.kidstopia.presentation.activity


import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.limheejin.kidstopia.R
import com.limheejin.kidstopia.databinding.ActivityMainBinding
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDAO
import com.limheejin.kidstopia.model.database.MyFavoriteVideoDatabase
import com.limheejin.kidstopia.presentation.fragment.HomeFragment
import com.limheejin.kidstopia.presentation.fragment.MyVideoFragment
import com.limheejin.kidstopia.presentation.fragment.SearchFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var dao: MyFavoriteVideoDAO
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // 데이터베이스 모든정보 지울때 잠깐 주석풀어 사용
//        deleteAllDatabaseInfo()

        dao = MyFavoriteVideoDatabase.getDatabase(application).getDao()
        binding.nav.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.fl, HomeFragment()).commit()
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

    private fun deleteAllDatabaseInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            dao = MyFavoriteVideoDatabase.getDatabase(application).getDao()
            dao.deleteVisitedVideo()
            dao.deleteLikedVideo()
        }
    }

}

