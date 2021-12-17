package clothing.semzpallet

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var myWebView : WebView
    lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myWebView  = findViewById(R.id.webView)
        //var url  = "https://store.boostedtechs.com"
        loadPages("https://semzpallet.clothing/")
//        bottom_navigation?.setOnItemSelectedListener {item ->
//            when(item.itemId) {
//                R.id.home -> run {
//                    loadPages("https://semzpallet.clothing/")
//                }
//
//                R.id.about_us -> run {
//                    loadPages("https://semzpallet.clothing/about-semz/")
//                }
//
//                R.id.book_shop -> run {
//                    loadPages("https://semzpallet.clothing/shop/")
//                }
//
//                R.id.sermons -> run {
//                    loadPages("https://semzpallet.clothing/checkout/")
//                }
//                R.id.more -> {
//                    val wrapper: Context =  ContextThemeWrapper(this, R.style.CustomPopUpStyle)
//                   val popupMenu = PopupMenu(wrapper, bottom_navigation, Gravity.RIGHT)
//                    popupMenu.setOnMenuItemClickListener { menuItem ->
//                        when(menuItem.itemId) {
////                            R.id.events -> run {
////                                loadPages("https://dunstankagwiisa.org/events")
////                            }
////                            R.id.donate -> run {
////                                loadPages("https://dunstankagwiisa.org/donate")
////                            }
//                            R.id.cart -> run {
//                                loadPages("https://semzpallet.clothing/cart/")
//                            }
//                            R.id.about_app -> run {
//                                makeToastMessage("App Version: 1 (1.1.5). SEMZ CLOTHING PALLET BY BOOSTED TECHNOLOGIES LIMITED")
//                            }
//                        }
//                        return@setOnMenuItemClickListener true
//                    }
//                    popupMenu.inflate(R.menu.top_navigation_menu)
//                    try {
//                        val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
//                        fieldMPopup.isAccessible = true
//                        val mPopup = fieldMPopup.get(popupMenu)
//                        mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(mPopup, true)
//                        Log.e("Icons set", "Icons set")
//                    } catch(e : Exception) {
//                        Log.e("Main Activity", "Popup Icon", e)
//                    } finally {
//                        popupMenu.show()
//                    }
//                }
//            }
//            return@setOnItemSelectedListener true
//        }

    }


    fun loadPages(url: String) {
        val isOnline : NetworkConnectivityCheck = NetworkConnectivityCheck()
        if (isOnline.isOnline(this))
            myWebView.loadUrl(url)
        else
            makeToastMessage("You are offline")

        myWebView.settings.javaScriptEnabled = true
        //myWebView.settings.javaScriptCanOpenWindowsAutomatically = true
        //myWebView.settings.setSupportMultipleWindows(true);
        myWebView.webViewClient = MyWebViewClient()
        myWebView.settings.userAgentString = "Boosted/semzclothingpallet"
        progressBar = findViewById(R.id.progressBar3)
        progressBar.visibility = View.VISIBLE
    }
    private fun makeToastMessage(text: String?) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (myWebView.canGoBack()) {
                        myWebView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            //makeToastMessage("started webview")
            if (Uri.parse(url).host.toString() == "semzpallet.clothing" || Uri.parse(url).host.equals("www.semzpallet.clothing")) {
                // This is my web site, so do not override; let my WebView load the page
                return false
            }
            //Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            var u = Uri.parse(url)
            val newUrlString :String? = null
            u = if (newUrlString.isNullOrEmpty()) u else Uri.parse(newUrlString)
            Intent(Intent.ACTION_VIEW, u).apply{
                startActivity(this)
                //makeToastMessage(u.toString())
            }
            return true
        }

        private val javascript = "android = 1; console.log(android)"

//        override fun onLoadResource(view: WebView?, url: String?) {
//            super.onLoadResource(view, url)
//            myWebView.evaluateJavascript(javascript){}
//        }
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
           // myWebView.evaluateJavascript(javascript){}
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            //makeToastMessage("Page finished")
            super.onPageFinished(view, url)
//            myWebView.loadUrl("javascript:(function() { " +
//                    "android_app_remove_header(); })()")
            //myWebView.evaluateJavascript(javascript){}
            progressBar.visibility = View.GONE
        }
    }
}