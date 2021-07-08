package com.hy.dailynews

//import com.hy.dailynews.feature.main.home.HomeFragment
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hy.dailynews.databinding.ActivityMainBinding
import com.hy.dailynews.utils.KeepStateNavigator
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        initView(savedInstanceState)
        initBinding()
        setActionBar()
        initNavigation()
    }

    override fun onBackPressed() {
        alert(message = getString(R.string.toast_exit_app)) {
            yesButton { finish() }
            noButton { it.dismiss() }
        }.show()
    }

    private fun setActionBar() {
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_search)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
    }

    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private fun initNavigation() {
        if (isOnline()) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.layout_frame) as NavHostFragment
            val navController = navHostFragment.navController
//            val appBarConfiguration = AppBarConfiguration(
//                topLevelDestinationIds = setOf(),
//                fallbackOnNavigateUpListener = ::onSupportNavigateUp
//            )

            val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.layout_frame)
            navController.navigatorProvider.addNavigator(navigator)
            navController.setGraph(R.navigation.nav_graph)
            binding.layoutBottomNavigation.setupWithNavController(navController)
//            binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        } else {
            /** '다시 시도' 버튼 예정*/
            toast(R.string.content_no_network_connection)
        }

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}