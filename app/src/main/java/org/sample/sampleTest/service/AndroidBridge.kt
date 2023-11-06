package org.sample.sampleTest.service

import android.util.Log
import android.webkit.JavascriptInterface

class AndroidBridge {

//    // 웹뷰에서 사진 클릭
//    @JavascriptInterface
//    fun viewPicture(panodetail_id: String) {
//        Log.i("@@@@@", "viewPicture(panodetail_id : $panodetail_id)")
//    }
//
//    // 웹뷰에서 동영상 클릭
//    @JavascriptInterface
//    fun viewVideo(panodetail_id: String) {
//        Log.i("@@@@@", "viewVideo(panodetail_id : $panodetail_id)")
//    }

    // 임베드 클릭-----------------------------------------------------------------------------
    @JavascriptInterface
    fun viewEmbed(embed_url: String, embed_name: String) {
        Log.i("@@@@@", "viewEmbed(embed_url : $embed_url, embed_name : $embed_name)")
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
    }

}