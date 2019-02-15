package com.raiachat.fragments


import android.os.Bundle
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_BACK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raiachat.R
import com.raiachat.util.WebChromeClt
import com.raiachat.util.WebViewClt
import kotlinx.android.synthetic.main.fragment_help.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HelpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this

        val view = inflater.inflate(com.raiachat.R.layout.fragment_help, container, false)
        handleBackPress(view)

        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (webView != null) {

            val webSettings = webView!!.settings
            webSettings.javaScriptEnabled = true

            webSettings.builtInZoomControls = true

            webView!!.webViewClient = WebViewClt(activity!!)
            webView!!.webChromeClient = WebChromeClt(activity!!)

            //           Case 1 .. Direct Url of the page...
            webView!!.loadUrl(getString(R.string.help_site_url))
        }
    }

    private fun handleBackPress(mainView: View) {
        mainView.isFocusableInTouchMode = true
        mainView.requestFocus()
        mainView.setOnKeyListener { v, keyCode, event ->
            if (!(keyCode != KEYCODE_BACK || event.action == ACTION_UP || !this.webView!!.canGoBack())) {
                webView!!.goBack()
                true
            }

            false
        }
    }


}
