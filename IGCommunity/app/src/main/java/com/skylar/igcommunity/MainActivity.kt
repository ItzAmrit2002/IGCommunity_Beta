package com.skylar.igcommunity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.skylar.igcommunity.webViewActivity.Companion.mainSteamID
import com.skylar.igcommunity.webViewActivity.Companion.userId
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    val apiKey = "11887DE1A07B61A5E2E3B710D3AA87FE"
    val steamId = userId
    val url = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=$apiKey&steamids=$steamId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadTask()

    }

    private fun downloadTask() {
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET,url,
            Response.Listener { response ->

                val responseString = response.toString()
                var jResponseObject = JSONObject(responseString)
                var jPlayerObject = jResponseObject.getJSONObject("response")
                var jPlayerArray = jPlayerObject.getJSONArray("players")
                Log.e("jObject",jPlayerArray.toString())
                for ( i in 0..jPlayerArray.length()-1) {
                    var jChildObject = jPlayerArray.getJSONObject(i)
                    var personaname = jChildObject.getString("personaname")
                    var avatarImage = jChildObject.getString("avatarfull")
                    Log.e("Profile Name",personaname)
                    Log.e("Avatar Image",avatarImage)

                }

            },Response.ErrorListener {
                Log.d("Error", it.toString())

            })
        queue.add(request)
    }
}