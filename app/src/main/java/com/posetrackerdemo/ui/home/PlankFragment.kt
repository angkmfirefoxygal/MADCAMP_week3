package com.posetrackerdemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.posetrackerdemo.R
import android.Manifest
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import org.json.JSONException
import org.json.JSONObject

class PlankFragment : Fragment() {

    private var API_KEY: String = "75d3c7f5-0533-429c-a137-2ae8a47dd10c"
    private var POSETRACKER_API_URL: String = "https://app.posetracker.com/pose_tracker/tracking"
    private var exercise: String = "face_plank"
    private var difficulty: String = "easy"
    private var skeleton: Boolean = true
    private var width = 350
    private var height = 350

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plank, container, false)

        val webView: WebView = view.findViewById(R.id.webView)
        val textApiResults: TextView = view.findViewById(R.id.textApiResults)
        val buttonRefresh: Button = view.findViewById(R.id.btnRefreshWebview)

        val url = "${POSETRACKER_API_URL}?token=${API_KEY}&exercise=${exercise}&difficulty=${difficulty}&type=counter&skeleton=${skeleton}&width=${width}&height=${height}"

        val webviewClient = object : WebViewClient() {
            override fun onPageFinished(webview: WebView, url: String) {
                webview.loadUrl(
                    "javascript:(function() {" +
                            "   window.webViewCallback  = function(info) {" +
                            "       Android.androidBridgeInterface(JSON.stringify(info ? info : 'No data')); " +
                            "   }" +
                            "})()"
                )
                super.onPageFinished(webview, url)
            }
        }

        webView.webViewClient = webviewClient
        webView.addJavascriptInterface(WebviewBridgeInterface(requireContext(), textApiResults), "Android")

        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.webChromeClient = MyWebChromeClient(requireActivity())

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(requireContext(), "Video Permission OK", Toast.LENGTH_LONG).show()
                    textApiResults.text = "Waiting for API data..."
                } else {
                    Toast.makeText(requireContext(), "Video Permission NOT granted", Toast.LENGTH_LONG).show()
                    textApiResults.text = "[ERROR] No video permission granted"
                }
                webView.loadUrl(url)
            }
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        buttonRefresh.setOnClickListener {
            textApiResults.text = "Reloading page..."
            webView.reload()
            textApiResults.text = "Reload done! Waiting for API data..."
        }

        return view
    }

    class WebviewBridgeInterface(private val mContext: Context, private val textView: TextView) {
        @JavascriptInterface
        fun androidBridgeInterface(strFromWebview: String) {
            try {
                val jsonData = JSONObject(strFromWebview)
                if (jsonData.getString("type") == "counter") {
                    val currentCount = jsonData.getInt("current_count")
                    textView.text = "Exercise Count: $currentCount"
                } else {
                    textView.text = "Data: $strFromWebview"
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                textView.text = "Invalid data format received"
            }
        }
    }

    class MyWebChromeClient(private val mContext: Context) : WebChromeClient() {
        override fun onPermissionRequest(request: PermissionRequest?) {
            if (request != null) {
                for (resource in request.resources) {
                    if (resource == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                        request.grant(arrayOf(resource))
                    }
                }
            }
        }
    }
}
