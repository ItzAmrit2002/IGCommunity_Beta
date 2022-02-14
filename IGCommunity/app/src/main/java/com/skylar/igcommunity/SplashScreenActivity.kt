package com.skylar.igcommunity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.skylar.igcommunity.webViewActivity.Companion.mainSteamID
import com.skylar.igcommunity.webViewActivity.Companion.userId

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        loadData()
        //savedPreferences

                window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        if(userId != null)
        {
            Handler().postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }, 2500)
        }else{
            Handler().postDelayed({
                startActivity(Intent(this,IntroActivity::class.java))
                finish()
            }, 2500)
        }
    }
    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefsLogin", Context.MODE_PRIVATE)
        val savedUserId = sharedPreferences.getString("STEAM_ID_64", null)
        val savedSteamId = sharedPreferences.getString("STEAM_ID", null)

        if (savedUserId != null) {
            userId = savedUserId
        }

        if (savedSteamId != null) {
            mainSteamID = savedSteamId
        }
    }
}