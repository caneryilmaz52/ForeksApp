package com.caneryilmazapps.foreksapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.caneryilmazapps.foreksapp.R
import com.github.ybq.android.spinkit.SpinKitView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var loadingView: SpinKitView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        loadingView = ac_ma_spin_kit
        ac_ma_bottomNavigationView.setupWithNavController(ac_ma_nav_host_fragment.findNavController())
    }

    fun showLoadingView() {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        loadingView.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        loadingView.visibility = View.GONE
    }
}