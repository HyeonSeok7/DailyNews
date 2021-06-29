package com.hy.dailynews

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hy.dailynews.databinding.ActivityMainBinding
import com.hy.dailynews.feature.main.home.HomeFragment
import com.hy.dailynews.feature.main.home.HomeFragment1
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    private var fragment: Fragment? = null
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initView(savedInstanceState)
    }

    override fun onBackPressed() {
        alert(message = getString(R.string.toast_exit_app)) {
            yesButton { finish() }
            noButton { it.dismiss() }
        }.show()
    }

    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private fun initView(savedInstanceState: Bundle?) {
        val fm: FragmentManager = supportFragmentManager
        if (isOnline()) {
//            binding.network = isOnline()
            if (savedInstanceState == null) {
                fragment = HomeFragment1.newInstance()
                val transaction = fm.beginTransaction()
                transaction.apply {
                    replace(R.id.layout_frame, fragment as HomeFragment1)
                    commit()
                }
            } else {
                fragment = fm.findFragmentById(R.id.layout_frame)
            }
        } else {
            /** '다시 시도' 버튼 예정*/
            toast(R.string.content_no_network_connection)
//            binding.network = isOnline()
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}