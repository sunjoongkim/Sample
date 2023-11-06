package org.sample.sampleTest.ui.space.bridge

import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.ui.space.BottomEmbedView
import org.sample.sampleTest.ui.space.BottomPlayerView

class SpaceBridge(private val playList: List<PanoDetail>, private val fragmentManager: FragmentManager) {


    // 웹뷰에서 사진 클릭
    @JavascriptInterface
    fun viewPicture(panodetail_id: String) {

        Log.i("@@@@@", "viewPicture(panodetail_id : $panodetail_id)")
        showPlayer(panodetail_id)
    }

    // 웹뷰에서 동영상 클릭
    @JavascriptInterface
    fun viewVideo(panodetail_id: String) {

        Log.i("@@@@@", "viewVideo(panodetail_id : $panodetail_id)")
        showPlayer(panodetail_id)
    }

    // 임베드 클릭-----------------------------------------------------------------------------
    @JavascriptInterface
    fun viewEmbed(embed_url: String, embed_name: String) {
        Log.i("@@@@@", "viewEmbed(embed_url : $embed_url, embed_name : $embed_name)")
        val bottomEmbedFragment = BottomEmbedView()
        bottomEmbedFragment.embedUrl = embed_url
        bottomEmbedFragment.embedName = embed_name
        bottomEmbedFragment.show(fragmentManager, bottomEmbedFragment.tag)
    }

    // 전시 클릭 ---------------------------------------------------------------------------------
    @JavascriptInterface
    fun viewShow(show_id: String) {
        Log.i("@@@@@", "viewShow(show_id : $show_id)")
    }

    // TTS 클릭 ---------------------------------------------------------------------------------
    @JavascriptInterface
    fun viewTts(tts_id: String) {
        Log.i("@@@@@", "viewTts(tts_id : $tts_id)")
        Toast.makeText(fragmentManager, )
    }


    private fun showPlayer(panodetailId: String) {
        val bottomPlayerFragment = BottomPlayerView()
        bottomPlayerFragment.playList = playList
        bottomPlayerFragment.panodetailId = panodetailId
        bottomPlayerFragment.show(fragmentManager, bottomPlayerFragment.tag)
    }


}