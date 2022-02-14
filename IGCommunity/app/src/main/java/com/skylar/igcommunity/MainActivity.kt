package com.skylar.igcommunity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.skylar.igcommunity.webViewActivity.Companion.mainSteamID
import com.skylar.igcommunity.webViewActivity.Companion.userId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.json.JSONObject
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val apiKey = "11887DE1A07B61A5E2E3B710D3AA87FE"
    private val url = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=$apiKey&steamids=$userId"

    companion object {
        var personaname : String = ""
        var avatarImage : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //function calls
        downloadTask()
        setupActionBar()
        onSave()

        //other syntax
        nav_view.setNavigationItemSelectedListener(this)

        Handler().postDelayed({
            setUserName()
            val executor = Executors.newSingleThreadExecutor()


            val handler = Handler(Looper.getMainLooper())

            var image: Bitmap? = null

            executor.execute {

                val imageURL = avatarImage

                try {
                    val `in` = java.net.URL(imageURL).openStream()
                    image = BitmapFactory.decodeStream(`in`)

                    handler.post {
                        nav_user_image.setImageBitmap(image)
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, 3000)
    }

    private fun downloadTask() {
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET,url,
            Response.Listener { response ->

                val responseString = response.toString()
                var jResponseObject = JSONObject(responseString)
                var jPlayerObject = jResponseObject.getJSONObject("response")
                var jPlayerArray = jPlayerObject.getJSONArray("players")

                for ( i in 0..jPlayerArray.length()-1) {
                    var jChildObject = jPlayerArray.getJSONObject(i)
                    personaname = jChildObject.getString("personaname")
                    avatarImage = jChildObject.getString("avatarfull")
                    Log.e("Profile Name",personaname)
                    Log.e("Avatar Image",avatarImage)

                }

            },Response.ErrorListener {
                Log.d("Error", it.toString())

            })
        queue.add(request)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_main_activity)
        toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolbar_main_activity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    private fun toggleDrawer(){
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_sign_out ->{
                onSignOutPref()
                val intent = Intent(this,IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUserName(){
        tv_username.text = personaname
    }

    private fun onSave() {
        val insertedText = userId
        val steamIDPref = mainSteamID

        val sharedPreferences = getSharedPreferences("sharedPrefsLogin", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply(){
            putString("STEAM_ID_64", insertedText)
            putString("STEAM_ID", steamIDPref)
        }.apply()
    }

    private fun onSignOutPref() {
        val sharedPreferences = getSharedPreferences("sharedPrefsLogin", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply(){
            putString("STEAM_ID_64", null)
            putString("STEAM_ID", null)
        }.apply()
    }
}