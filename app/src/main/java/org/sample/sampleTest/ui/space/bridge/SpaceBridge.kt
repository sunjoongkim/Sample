package org.sample.sampleTest.ui.space.bridge

import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import org.sample.sampleTest.data.PanoDetail
import org.sample.sampleTest.data.SpaceContent
import org.sample.sampleTest.data.SpaceTTS
import org.sample.sampleTest.define.Define
import org.sample.sampleTest.service.ApiService
import org.sample.sampleTest.service.RetrofitBuilder
import org.sample.sampleTest.ui.space.BottomEmbedView
import org.sample.sampleTest.ui.space.BottomPlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        RetrofitBuilder.apiService.getTTS(Define.SELVERS_ACCESS_TOKEN, tts_id)
            .enqueue(object : Callback<SpaceTTS> {

                override fun onResponse(
                    call: Call<SpaceTTS>,
                    response: Response<SpaceTTS>
                ) {

                    Log.i("@@@@@", response.body().toString())

                    var spaceTTS = response.body() as SpaceTTS

                    // YoutubeList 추가 후 pictureList 추가
                    panoDetailList.addAll(spaceContent.data.youtubeList)
                    panoDetailList.addAll(spaceContent.data.pictureList)

                    // 웹뷰 브릿지
                    webView.addJavascriptInterface(SpaceBridge(panoDetailList, fragmentManager) , "spaceHandler")


                }

                override fun onFailure(call: Call<SpaceTTS>, t: Throwable) {
                    Log.i("@@@@@", "onFailure : $t")

                }

            })
        //Toast.makeText(fragmentManager, )
    }


    private fun showPlayer(panodetailId: String) {
        val bottomPlayerFragment = BottomPlayerView()
        bottomPlayerFragment.playList = playList
        bottomPlayerFragment.panodetailId = panodetailId
        bottomPlayerFragment.show(fragmentManager, bottomPlayerFragment.tag)
    }


}