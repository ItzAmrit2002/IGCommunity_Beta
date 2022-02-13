package com.skylar.igcommunity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

import java.util.*

class webViewActivity : AppCompatActivity() {

    val REALM_PARAM: String = "IGCommunity"

    companion object {
        var mainSteamID : String = ""
        var userId : String? = ""
    }

    private val url: String = "https://steamcommunity.com/openid/login?" +
            "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
            "openid.mode=checkid_setup&" +
            "openid.ns=http://specs.openid.net/auth/2.0&" +
            "openid.realm=https://" + REALM_PARAM + "&" +
            "openid.return_to=https://" + REALM_PARAM + "/signin/"
    private var isAlreadyCreated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(false)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("onPagerFinished", "PageFinished Called")
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("onPagerStarted", "PageStarted Called")
                setTitle(url)
                val Url: Uri = Uri.parse(url)
                if (Url.authority.equals(REALM_PARAM.lowercase(Locale.getDefault()))) {
                    val userAccountUrl: Uri = Uri.parse(Url.getQueryParameter("openid.identity"))
                    userId = userAccountUrl.lastPathSegment
                    Log.d("steam userid", "userId $userId")
                    val newUserId : String = userId.toString()
                    val steamID64 : Long = newUserId.toLong()
                    var authServer : Int = 0
                    if(((steamID64 - 76561197960265728)%2).equals(1)){
                       authServer = 1
                    }
                    else{
                        authServer = 0
                    }
                    val authId : Long = ((steamID64 - 76561197960265728 - authServer) / 2)
                    mainSteamID = "STEAM_1:$authServer:$authId"
                    Log.d("steamID main", mainSteamID)

                    webView.stopLoading()
                    finish()

                    val intent = Intent(this@webViewActivity, MainActivity::class.java)
                    startActivity(intent)

                    IntroActivity.fa.finish()
                }
            }
            /*override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                Log.d("receivedError Func Called", "wassuppp")
            }*/
        }
        webView.loadUrl(url)
    }

}