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
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
        return inflater.inflate(R.layout.bottom_embed_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.webView)

    }

    override fun onResume() {
        super.onResume()

        // 하단 뷰 크기 고정
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT

        webView?.webViewClient = WebViewClient()
        val webSettings: WebSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true // 웹뷰가 wide viewport를 사용하도록 함
        webSettings.loadWithOverviewMode = true // 웹뷰가 컨텐츠를 화면에 맞게 조정하도록 함

        webView?.loadUrl(embedUrl)
    }
}