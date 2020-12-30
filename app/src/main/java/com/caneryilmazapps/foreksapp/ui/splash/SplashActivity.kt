package com.caneryilmazapps.foreksapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.caneryilmazapps.foreksapp.R
import com.caneryilmazapps.foreksapp.ui.main.MainActivity
import com.caneryilmazapps.foreksapp.utils.Utils
import com.github.ybq.android.spinkit.SpinKitView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var loadingView: SpinKitView
    private lateinit var loadingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        loadingView = ac_sp_spin_kit
        loadingTextView = ac_sp_loading_text_view

        checkNetworkConnection()
    }

    private fun checkNetworkConnection() {
        val status = Utils.hasInternetConnection(this)

        if (status) {
            loadingTextView.visibility = View.GONE
            loadingView.visibility = View.GONE
            navigateMainActivity()
        } else {
            loadingView.visibility = View.GONE
            loadingTextView.visibility = View.VISIBLE

            loadingTextView.text = "İnternet bağlantınızı kontrol edin."
        }
    }

    private fun navigateMainActivity() {
        this.lifecycleScope.launch {
            delay(500)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}