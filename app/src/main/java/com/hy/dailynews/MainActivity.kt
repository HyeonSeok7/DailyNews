package com.hy.dailynews

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hy.dailynews.databinding.ActivityMainBinding
import com.hy.dailynews.utils.KeepStateNavigator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        setActionBar()
        initNavigation()
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setMessage(getString(R.string.toast_exit_app))
            setPositiveButton(getString(R.string.button_positive)) { _: DialogInterface, _: Int -> finish() }
            setNegativeButton(getString(R.string.button_negative)) { _: DialogInterface, _: Int -> }.show()
        }
    }

    private fun setActionBar() {
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_search)
        binding.toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Toolbar Click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    // 네트워크 체크
    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private fun initNavigation() {
        if (isOnline()) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.layout_frame) as NavHostFragment
            val navController = navHostFragment.navController

            val navigator =
                KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.layout_frame)
            navController.navigatorProvider.addNavigator(navigator)
            navController.setGraph(R.navigation.nav_graph)
            binding.layoutBottomNavigation.setupWithNavController(navController)
        } else {
            /** '다시 시도' 버튼 예정*/
            Toast.makeText(this, R.string.content_no_network_connection, Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}