package org.sample.sampleTest.ui.space.bridge

import android.util.Log
import android.webkit.JavascriptInterface
import androidx.fragment.app.FragmentManager
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.ui.BottomSheetView
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


    private fun showPlayer(panodetailId: String) {
        val bottomPlayerFragment = BottomPlayerView()
        bottomPlayerFragment.playList = playList
        bottomPlayerFragment.panodetailId = panodetailId
        bottomPlayerFragment.show(fragmentManager, bottomPlayerFragment.tag)
    }
}