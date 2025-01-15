package com.posetrackerdemo


import android.content.Context
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONException
import org.json.JSONObject


/** Define the Javascript bridge interface to the Webview */
class WebviewBridgeInterface2(private val mContext: Context, private val textView: TextView) {

    /** Android bridge function to be called from JS using "Android.androidBridgeInterface(jsonStr)" */
    @JavascriptInterface
    fun androidBridgeInterface(strFromWebview: String) {
        try {
            // Parse JSON string from WebView
            val jsonData = JSONObject(strFromWebview)

            // Check if type is 'counter'
            if (jsonData.getString("type") == "counter") {
                val currentCount = jsonData.getInt("current_count")
                // Update the TextView with the current exercise count
                textView.text = "Exercise Count: $currentCount"
            } else {
                // Handle other types if necessary (e.g., posture)
                textView.text = "Data: $strFromWebview"
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            textView.text = "Invalid data format received"
        }

    }
}


/** Define a WebChromeClient to handle VIDEO_CAPTURE permission from the webview */
class MyWebChromeClient2(private val mContext: AppCompatActivity) : WebChromeClient() {
    override fun onPermissionRequest(request: PermissionRequest?) {
        if(request != null) {
            for( resource in request.resources) {
                if(resource == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                    request.grant(arrayOf(resource))
                }
            }
        }
    }
}

/** Handle main activity :
 * - Display Webview,
 * - Set JS bridge,
 * - Set chrome permission intercepter from Webview
 * - Ask for Camera permission before navigating to API
 * - */
class PlankTracker : AppCompatActivity(){

    // properties
    // API request your token provided on our dashboard on posetracker.com (It's free <3)
    var API_KEY: String = "745374e1-db25-4a90-81dc-c0aadb9c41d4"
    // Below is the main url to reach our API
    var POSETRACKER_API_URL : String = "https://app.posetracker.com/pose_tracker/tracking"
    // Our API request the exercise you want to track and count
    var exercise : String = "face_plank"
    // Our API request the difficulty of the exercise (by default it's set to normal)
    var difficulty : String = "easy"
    // You can request API to display user skeleton or not (by default it's set to true)
    var skeleton : Boolean = true
    // retrieve webview dimensions
    var width = 350
    // webView.resources.displayMetrics.
    var height = 350

    // init main view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.squat_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // create a webclient to add the bridge function to the webview.
        val webviewClient = object : WebViewClient() {
            override fun onPageFinished(webview: WebView, url: String) {
                webview.loadUrl(
                    "javascript:(function() {" +
                            "   window.webViewCallback  = function(info) {" +
                            "       Android.androidBridgeInterface(JSON.stringify(info ? info : 'No data')); " +
                            "   }" +
                            "})()"
                );
                super.onPageFinished(webview, url)
            }
        }

        // retrieve webview Widget
        val webView: WebView = findViewById(R.id.webView)
        // Retrieve text view used to display api results
        val textApiResults: TextView = findViewById(R.id.textApiResults)
        // Retrieve button Refresh
        var buttonRefresh: Button = findViewById(R.id.btnRefreshWebview)

        // set callback to refresh page on button click
        buttonRefresh.setOnClickListener { view ->
            textApiResults.setText("Reloading page...")
            webView.reload();
            textApiResults.setText("Reload done ! Waiting for API data...")
        }

        // init webClient and JS interface needed to retrieve data sent by the API
        webView.webViewClient = webviewClient
        webView.addJavascriptInterface(WebviewBridgeInterface2(this, textApiResults), "Android")

        // activate Javascript options in webview
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.setWebChromeClient(MyWebChromeClient2(this))

        // build API full URL
        //var url = "${POSETRACKER_API_URL}?token=${API_KEY}&exercise=${exercise}&difficulty=${difficulty}&skeleton=${skeleton}&width=${width}&height=${height}"
        val url = "${POSETRACKER_API_URL}?token=${API_KEY}&exercise=${exercise}&difficulty=${difficulty}&type=counter&skeleton=${skeleton}&width=${width}&height=${height}"

        // init and request camera permission on start
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission has been granted : Start camera preview Activity.
                    Toast.makeText(this, "Video Permission OK", Toast.LENGTH_LONG).show()
                    textApiResults.setText("Waiting for API data...")
                } else {
                    // Permission request was denied : display message
                    Toast.makeText(this, "Video Permission NOT granted", Toast.LENGTH_LONG).show()
                    textApiResults.setText("[ERROR] No video permission granted")
                }
                // load url no matter what
                webView.loadUrl(url)
            }
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
}