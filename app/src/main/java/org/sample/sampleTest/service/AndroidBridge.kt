package org.sample.sampleTest.service

import android.util.Log
import android.webkit.JavascriptInterface

class AndroidBridge {


    // 웹뷰에서 사진 클릭
    @JavascriptInterface
    fun viewPicture(panodetail_id: String) {

        Log.i("@@@@@", "viewPicture(panodetail_id : $panodetail_id)")
    }

    // 웹뷰에서 동영상 클릭
    @JavascriptInterface
    fun viewVideo(panodetail_id: String) {

        Log.i("@@@@@", "viewVideo(panodetail_id : $panodetail_id)")
    }

}