package org.sample.sampleTest.ui.space

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.sample.sampleTest.R
import org.sample.sampleTest.data.PanoDetail

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
        webView?.webViewClient = WebViewClient()
        webView?.loadUrl(embedUrl)
    }
}