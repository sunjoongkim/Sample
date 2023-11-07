package org.sample.sampleTest.ui.space

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sample.sampleTest.R

// Embed 선택시 하단에서 올라오는 뷰
class BottomEmbedView : BottomSheetDialogFragment() {

    private var webView: WebView? = null

    var embedUrl: String = ""
    var embedName: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_embed_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.webView)
    }

    override fun onResume() {
        super.onResume()

        webView?.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // 페이지 로딩이 시작될 때 호출
                Log.e("@@@@@", "====> onPageStarted")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 페이지 로딩이 끝났을 때 호출
                Log.e("@@@@@", "====> onPageFinished : $url")
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                // 페이지 로딩 중 에러가 발생했을 때 호출
                Log.e("@@@@@", "====> onReceivedError : $error")
            }
        }
        val webSettings: WebSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true // 웹뷰가 wide viewport를 사용하도록 함
        webSettings.loadWithOverviewMode = true // 웹뷰가 컨텐츠를 화면에 맞게 조정하도록 함

        webView?.loadUrl(embedUrl)
    }
}