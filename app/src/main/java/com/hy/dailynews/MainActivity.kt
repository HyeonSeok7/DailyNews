package com.hy.dailynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hy.dailynews.feature.main.home.HomeFragment
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView(savedInstanceState)

    }

    override fun onBackPressed() {
        alert (message = getString(R.string.toast_exit_app)){
            yesButton { finish() }
            noButton { it.dismiss() }
        }.show()
    }

    private fun initView(savedInstanceState: Bundle?) {
        val fm: FragmentManager = supportFragmentManager
        if (savedInstanceState == null) {
            fragment = HomeFragment.newInstance()
            val transaction = fm.beginTransaction()
            transaction.apply {
                replace(R.id.layout_frame,  fragment as HomeFragment)
                commit()
            }
        } else {
            fragment = fm.findFragmentById(R.id.layout_frame)
        }
    }


    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}